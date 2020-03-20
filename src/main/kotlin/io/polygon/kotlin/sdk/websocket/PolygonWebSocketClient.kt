package io.polygon.kotlin.sdk.websocket

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.host
import io.ktor.http.URLProtocol
import io.ktor.http.cio.websocket.*
import io.polygon.kotlin.sdk.DefaultJvmHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

enum class WebSocketCluster(internal val path: String) {
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
    private val cluster: WebSocketCluster,
    private val httpClientProvider: HttpClientProvider = DefaultJvmHttpClientProvider(),
    private val polygonWebSocketDomain: String = "socket.polygon.io",
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private var activeConnection: WebSocketConnection? = null

    suspend fun connect() {
        if (activeConnection != null) {
            return
        }

        val client = httpClientProvider.buildClient()
        val session = client.webSocketSession {
            host = polygonWebSocketDomain

            url.protocol = URLProtocol.WSS
            url.port = URLProtocol.WSS.defaultPort
            url.path("crypto")
        }

        activeConnection = WebSocketConnection(client, session)
    }

    fun subscribe() {

    }

    fun unsubscribe() {

    }

    suspend fun disconnect() {
        activeConnection?.disconnect()
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
}

private class WebSocketConnection(
    val httpClient: HttpClient,
    val webSocketSession: WebSocketSession
) {

    suspend fun disconnect() {
        webSocketSession.close()
        httpClient.close()
    }

}