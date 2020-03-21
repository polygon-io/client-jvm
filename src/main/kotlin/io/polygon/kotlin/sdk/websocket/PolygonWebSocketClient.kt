package io.polygon.kotlin.sdk.websocket

import io.ktor.client.HttpClient
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.host
import io.ktor.http.URLProtocol
import io.ktor.http.cio.websocket.*
import io.polygon.kotlin.sdk.DefaultJvmHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.*

enum class WebSocketCluster(internal vararg val pathComponents: String) {
    Stocks("stocks"),
    Forex("forex"),
    Crypto("crypto")
}

/**
 * A client for the Polygon.io RESTful API
 *
 * @param apiKey the API key to use with all API requests
 * @param httpClientProvider (Optional) A provider for the ktor [HttpClient] to use; defaults to [DefaultJvmHttpClientProvider]
 * @param polygonWebSocketDomain (Optional) The domain to connect to for all websockets; defaults to Polygon's websocket domain "socket.polyhon.io". Useful for overriding in a testing environment
 */
class PolygonWebSocketClient
@JvmOverloads
constructor(
    private val apiKey: String,
    val cluster: WebSocketCluster,
    private val listener: PolygonWebSocketListener,
    private val bufferSize: Int = Channel.UNLIMITED,
    private val httpClientProvider: HttpClientProvider = DefaultJvmHttpClientProvider(),
    private val polygonWebSocketDomain: String = "socket.polygon.io",
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val serializer by lazy {
        Json(JsonConfiguration.Stable.copy(strictMode = false))
    }

    private var activeConnection: WebSocketConnection? = null

    /**
     * Set this to true if you want to parse incoming messages yourself
     */
    var sendRaw: Boolean = false

    suspend fun connect() {
        if (activeConnection != null) {
            return
        }

        val client = httpClientProvider.buildClient()
        val session = client.webSocketSession {
            host = polygonWebSocketDomain

            url.protocol = URLProtocol.WSS
            url.port = URLProtocol.WSS.defaultPort
            url.path(*cluster.pathComponents)
        }

        activeConnection = WebSocketConnection(client, session)

        try {
            session.incoming
                .consumeAsFlow()
                .buffer(bufferSize)
                .onEach(this::processFrame)
                .onCompletion { listener.onDisconnect(this@PolygonWebSocketClient) }
                .launchIn(coroutineScope)
        } catch (ex: Exception) {
            listener.onError(this, ex)
        }

        // Authenticate and wait for result to be processed before calling listener.onAuthenticated
        session.send("""{"action": "auth", "params": "$apiKey"}""")
    }

    /**
     * Subscribe to one or more data streams. Be sure to subscribe to streams available in the
     * current cluster
     */
    suspend fun subscribe(vararg subscriptions: PolygonWebSocketSubscription) {
        if (subscriptions.isEmpty()) return

        activeConnection
            ?.webSocketSession
            ?.send("""{"action": "subscribe", "params":"${subscriptions.joinToString()}"}""")
    }

    /**
     * Unsubscribe to one or more data streams
     */
    suspend fun unsubscribe(vararg subscriptions: PolygonWebSocketSubscription) {
        if (subscriptions.isEmpty()) return

        activeConnection
            ?.webSocketSession
            ?.send("""{"action": "unsubscribe", "params":"${subscriptions.joinToString()}"}""")
    }

    /**
     * Disconnect this client from the websocket server
     */
    suspend fun disconnect() {
        activeConnection?.disconnect()
        activeConnection = null

        listener.onDisconnect(this)
    }

    private suspend fun processFrame(frame: Frame) {
        try {
            if (activeConnection?.isAuthenticated == false) {
                if (parseAuthenticationFrame(frame)) {
                    return
                }
            }

            if (sendRaw) {
                listener.onReceive(this, PolygonWebSocketMessage.RawMessage(frame.readBytes()))
            } else {

            }
        } catch (ex: Exception) {
            listener.onError(this, ex)
        }
    }

    @Throws(SerializationException::class, JsonException::class)
    private fun parseAuthenticationFrame(frame: Frame): Boolean {
        val response = serializer.parseJson(String(frame.readBytes()))

        if (response is JsonArray) {
            return response.any { parseStatusMessageForAuthenticationResult(it) }
        }

        if (response is JsonObject) {
            return parseStatusMessageForAuthenticationResult(response)
        }

        return false
    }

    @Throws(SerializationException::class, JsonException::class)
    private fun parseStatusMessageForAuthenticationResult(message: JsonElement): Boolean {
        if (message.isStatusMessage()) {
            val status = serializer.fromJson(PolygonWebSocketMessage.StatusMessage.serializer(), message)
            if (status.message == "authenticated") {
                activeConnection?.isAuthenticated = true
                listener.onAuthenticated(this)
                return true
            }
        }

        return false
    }

    suspend fun socketTest() {
        val client = httpClientProvider.buildClient()

        val session = client.webSocketSession {
            url.protocol = URLProtocol.WSS
            url.port = URLProtocol.WSS.defaultPort
            host = polygonWebSocketDomain
            url.path("crypto")
        }

        session.incoming.consumeAsFlow()
            .onEach { println("From flow: " + (it as Frame.Text).readText()) }
            .onCompletion { println("complete") }
            .launchIn(coroutineScope)

        session.send("""{"action": "auth", "params": "$apiKey"}""")
        session.send("""{"action": "subscribe", "params": "XT.BTC-USD"}""")

        delay(1000)

        session.send("""{"action": "unsubscribe", "params": "XT.BTC-USD"}""")

        println("closing")
        session.close()
        client.close()
        println("Closed")
    }

    /**
     * If the first byte in the message is an open square bracket, this frame is housing an array
     */
    private fun JsonElement.isStatusMessage() =
        this is JsonObject && jsonObject.getPrimitive("ev").contentOrNull == "status"
}

private class WebSocketConnection(
    val httpClient: HttpClient,
    val webSocketSession: WebSocketSession,
    var isAuthenticated: Boolean = false
) {

    suspend fun disconnect() {
        webSocketSession.close()
        httpClient.close()
    }
}

@Serializable
private data class BaseWebSocketMessage(val ev: String)