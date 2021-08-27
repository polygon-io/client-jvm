package io.polygon.kotlin.sdk.websocket

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.host
import io.ktor.http.URLProtocol
import io.ktor.http.cio.websocket.*
import io.polygon.kotlin.sdk.DefaultJvmHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import io.polygon.kotlin.sdk.ext.PolygonCompletionCallback
import io.polygon.kotlin.sdk.ext.coroutineToCompletionCallback
import io.polygon.kotlin.sdk.websocket.PolygonWebSocketMessage.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.*

private const val EVENT_TYPE_MESSAGE_KEY = "ev"

/**
 * See https://polygon.io/sockets for details
 */
enum class PolygonWebSocketCluster(internal vararg val pathComponents: String) {
    Stocks("stocks"),
    Forex("forex"),
    Crypto("crypto"),
    Options("options")
}

/**
 * A client for the Polygon.io WebSocket API
 *
 * https://polygon.io/sockets
 *
 * @param apiKey the API key to use with all API requests
 * @param cluster the [PolygonWebSocketCluster] to connect to
 * @param listener the [PolygonWebSocketListener] to send events to
 * @param bufferSize the size of the back buffer to use when websocket events start coming in faster than they can be processed. To drop all but the latest event, use [Channel.CONFLATED]
 * @param httpClientProvider (Optional) A provider for the ktor [HttpClient] to use; defaults to [DefaultJvmHttpClientProvider]
 * @param polygonWebSocketDomain (Optional) The domain to connect to for all websockets; defaults to Polygon's websocket domain "socket.polyhon.io". Useful for overriding in a testing environment
 * @param coroutineScope The coroutine scope to launch the web socket processing in. For info on coroutines, see here: https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html
 */
class PolygonWebSocketClient
@JvmOverloads
constructor(
    private val apiKey: String,
    val cluster: PolygonWebSocketCluster,
    private val listener: PolygonWebSocketListener,
    private val bufferSize: Int = Channel.UNLIMITED,
    private val httpClientProvider: HttpClientProvider = DefaultJvmHttpClientProvider(),
    private val polygonWebSocketDomain: String = "socket.polygon.io"
) {

    private val serializer by lazy {
        Json(JsonConfiguration.Stable.copy(strictMode = false))
    }

    private var activeConnection: WebSocketConnection? = null

    /**
     * Set this to true if you want to parse incoming messages yourself
     */
    var sendRaw: Boolean = false

    /**
     * The coroutine scope to launch the web socket processing in.
     * This should be set before connecting to the websocket
     * For info on coroutines, see here: https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html
     */
    var coroutineScope: CoroutineScope = GlobalScope


    /**
     * Connect and authenticate to the given [PolygonWebSocketCluster].
     *
     * Calling from java? see [connectBlocking] and [connectAsync]
     */
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

    /** Blocking version of [connect] */
    fun connectBlocking() = runBlocking { connect() }

    /** Async/callback version of [connect] */
    fun connectAsync(callback: PolygonCompletionCallback?) =
        coroutineToCompletionCallback(callback, coroutineScope) { connect() }

    /**
     * Subscribe to one or more data streams. Be sure to subscribe to streams available in the
     * current cluster.
     *
     * Calling from Java? See [subscribeBlocking] and [subscribeAsync]
     */
    suspend fun subscribe(subscriptions: List<PolygonWebSocketSubscription>) {
        if (subscriptions.isEmpty()) return

        activeConnection
            ?.webSocketSession
            ?.send("""{"action": "subscribe", "params":"${subscriptions.joinToString(separator = ",")}"}""")
    }

    /** Blocking version of [subscribe]  */
    fun subscribeBlocking(subscriptions: List<PolygonWebSocketSubscription>) =
        runBlocking { subscribe(subscriptions) }

    /** Async/callback version of [subscribe] */
    fun subscribeAsync(subscriptions: List<PolygonWebSocketSubscription>, callback: PolygonCompletionCallback?) =
        coroutineToCompletionCallback(callback, coroutineScope)  { subscribe(subscriptions) }

    /**
     * Unsubscribe to one or more data streams
     *
     * Calling from Java? See [unsubscribeBlocking] and [unsubscribeAsync]
     */
    suspend fun unsubscribe(subscriptions: List<PolygonWebSocketSubscription>) {
        if (subscriptions.isEmpty()) return

        activeConnection
            ?.webSocketSession
            ?.send("""{"action": "unsubscribe", "params":"${subscriptions.joinToString(separator = ",")}"}""")
    }

    /** Blocking version of [unsubscribe] */
    fun unsubscribeBlocking(subscriptions: List<PolygonWebSocketSubscription>) =
        runBlocking { unsubscribe(subscriptions) }

    /** Callback/async version of [unsubscribe] */
    fun unsubscribeAsync(subsciptions: List<PolygonWebSocketSubscription>, callback: PolygonCompletionCallback?) =
        coroutineToCompletionCallback(callback, coroutineScope) { unsubscribe(subsciptions) }

    /**
     * Disconnect this client from the websocket server.
     *
     * Calling from Java? See [disconnectBlocking] and [disconnectAsync]
     */
    suspend fun disconnect() {
        activeConnection?.disconnect()
        activeConnection = null

        listener.onDisconnect(this)
    }

    /** Blocking version of [disconnect] */
    fun disconnectBlocking() = runBlocking { disconnect() }

    /** Async/callback version of [disconnect] */
    fun disconnectAsync(callback: PolygonCompletionCallback?) =
        coroutineToCompletionCallback(callback, coroutineScope) { disconnect() }

    private suspend fun processFrame(frame: Frame) {
        try {
            if (activeConnection?.isAuthenticated == false) {
                if (parseAuthenticationFrame(frame)) {
                    return
                }
            }

            if (sendRaw) {
                listener.onReceive(this, RawMessage(frame.readBytes()))
            } else {
                val json = serializer.parseJson(String(frame.readBytes()))
                processFrameJson(json).forEach { listener.onReceive(this, it) }
            }
        } catch (ex: Exception) {
            listener.onReceive(this, RawMessage(frame.readBytes()))
            listener.onError(this, ex)
        }
    }

    @Throws(SerializationException::class, JsonException::class)
    private fun processFrameJson(
        frame: JsonElement,
        collector: MutableList<PolygonWebSocketMessage> = mutableListOf()
    ): List<PolygonWebSocketMessage> {

        if (frame is JsonArray) {
            frame.jsonArray.forEach { processFrameJson(it, collector) }
        }

        if (frame is JsonObject) {
            val message = when (frame.jsonObject.getPrimitive(EVENT_TYPE_MESSAGE_KEY).contentOrNull) {
                "status" -> serializer.fromJson(StatusMessage.serializer(), frame)
                "T" -> serializer.fromJson(StocksMessage.Trade.serializer(), frame)
                "Q" -> serializer.fromJson(StocksMessage.Quote.serializer(), frame)
                "A", "AM" -> serializer.fromJson(StocksMessage.Aggregate.serializer(), frame)
                "C" -> serializer.fromJson(ForexMessage.Quote.serializer(), frame)
                "CA" -> serializer.fromJson(ForexMessage.Aggregate.serializer(), frame)
                "XQ" -> serializer.fromJson(CryptoMessage.Quote.serializer(), frame)
                "XT" -> serializer.fromJson(CryptoMessage.Trade.serializer(), frame)
                "XA" -> serializer.fromJson(CryptoMessage.Aggregate.serializer(), frame)
                "XS" -> serializer.fromJson(CryptoMessage.ConsolidatedQuote.serializer(), frame)
                "XL2" -> serializer.fromJson(CryptoMessage.Level2Tick.serializer(), frame)
                else -> RawMessage(frame.toString().toByteArray())
            }
            collector.add(message)
        }

        return collector
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
            val status = serializer.fromJson(StatusMessage.serializer(), message)
            if (status.message == "authenticated") {
                activeConnection?.isAuthenticated = true
                listener.onAuthenticated(this)
                return true
            }
        }

        return false
    }

    /**
     * If the first byte in the message is an open square bracket, this frame is housing an array
     */
    private fun JsonElement.isStatusMessage() =
        this is JsonObject && jsonObject.getPrimitive(EVENT_TYPE_MESSAGE_KEY).contentOrNull == "status"
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