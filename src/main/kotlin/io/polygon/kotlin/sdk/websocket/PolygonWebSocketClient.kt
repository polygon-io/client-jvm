package io.polygon.kotlin.sdk.websocket

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import io.polygon.kotlin.sdk.DefaultJvmHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import io.polygon.kotlin.sdk.Version
import io.polygon.kotlin.sdk.ext.PolygonCompletionCallback
import io.polygon.kotlin.sdk.ext.coroutineToCompletionCallback
import io.polygon.kotlin.sdk.websocket.PolygonWebSocketMessage.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.*

private const val EVENT_TYPE_MESSAGE_KEY = "ev"

/** Feed is the data feed (e.g. Delayed, RealTime) which represents the server host. */
sealed class Feed(val url: String) {
    object Delayed : Feed("delayed.polygon.io")
    object RealTime : Feed("socket.polygon.io")
    object Nasdaq : Feed("nasdaqfeed.polygon.io")
    object PolyFeed : Feed("polyfeed.polygon.io")
    object PolyFeedPlus : Feed("polyfeedplus.polygon.io")
    object StarterFeed : Feed("starterfeed.polygon.io")
    object LaunchpadFeed : Feed("launchpad.polygon.io")
    object BusinessFeed : Feed("business.polygon.io")
    object EdgxBusinessFeed : Feed("edgx-business.polygon.io")
    object DelayedBusinessFeed : Feed("delayed-business.polygon.io")
    object DelayedEdgxBusinessFeed : Feed("delayed-edgx-business.polygon.io")
    object DelayedNasdaqLastSaleBusinessFeed : Feed("delayed-nasdaq-last-sale-business.polygon.io")
    object DelayedNasdaqBasicFeed : Feed("delayed-nasdaq-basic-business.polygon.io")
    object DelayedFullMarketBusinessFeed : Feed("delayed-fullmarket-business.polygon.io")
    object FullMarketBusinessFeed : Feed("fullmarket-business.polygon.io")
    object NasdaqLastSaleBusinessFeed : Feed("nasdaq-last-sale-business.polygon.io")
    object NasdaqBasicBusinessFeed : Feed("nasdaq-basic-business.polygon.io")

    /** Use Other if there's a new feed that this client library doesn't yet support. */
    class Other(url: String) : Feed(url)
}

/** Market is the type of market (e.g. Stocks, Crypto) used to connect to the server. */
sealed class Market(val market: String) {
    object Stocks : Market("stocks")
    object Options : Market("options")
    object Forex : Market("forex")
    object Crypto : Market("crypto")
    object Indices : Market("indices")
    object LaunchpadStocks : Market("stocks")
    object LaunchpadOptions : Market("options")
    object LaunchpadForex : Market("forex")
    object LaunchpadCrypto : Market("crypto")
    object BusinessStocks : Market("stocks")
    object BusinessOptions : Market("options")
    object BusinessForex : Market("forex")
    object BusinessCrypto : Market("crypto")

    /** Use Other if there's a new market that this client library doesn't yet support. */
    class Other(market: String) : Market(market)
}

/**
 * A client for the Polygon.io WebSocket API
 *
 * https://polygon.io/sockets
 *
 * @param apiKey the API key to use with all API requests
 * @param feed the data feed (e.g. Delayed, RealTime) which represents the server host
 * @param market the type of market (e.g. Stocks, Crypto) used to connect to the server
 * @param listener the [PolygonWebSocketListener] to send events to
 * @param bufferSize the size of the back buffer to use when websocket events start coming in faster
 * than they can be processed. To drop all but the latest event, use [Channel.CONFLATED]
 * @param httpClientProvider (Optional) A provider for the ktor [HttpClient] to use; defaults to
 * [DefaultJvmHttpClientProvider]
 * @param polygonWebSocketDomain (Optional) The domain to connect to for all websockets; defaults to
 * Polygon's websocket domain "socket.polyhon.io". Useful for overriding in a testing environment
 * @param coroutineScope The coroutine scope to launch the web socket processing in. For info on
 * coroutines, see here: https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html
 */
class PolygonWebSocketClient
@JvmOverloads
constructor(
        private val apiKey: String,
        private val feed: Feed = Feed.RealTime,
        private val market: Market = Market.Stocks,
        private val listener: PolygonWebSocketListener,
        private val bufferSize: Int = Channel.UNLIMITED,
        private val httpClientProvider: HttpClientProvider = DefaultJvmHttpClientProvider(),
) {

    private val serializer by lazy {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    private var activeConnection: WebSocketConnection? = null

    /** Set this to true if you want to parse incoming messages yourself */
    var sendRaw: Boolean = false

    /**
     * The coroutine scope to launch the web socket processing in. This should be set before
     * connecting to the websocket For info on coroutines, see here:
     * https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html
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
        val session =
                client.webSocketSession {
                    url {
                        protocol = URLProtocol.WSS
                        host = feed.url
                        port = URLProtocol.WSS.defaultPort
                        encodedPath = market.market
                    }
                    headers["User-Agent"] = Version.userAgent
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

        activeConnection?.webSocketSession?.send(
                """{"action": "subscribe", "params":"${subscriptions.joinToString(separator = ",")}"}"""
        )
    }

    /** Blocking version of [subscribe] */
    fun subscribeBlocking(subscriptions: List<PolygonWebSocketSubscription>) = runBlocking {
        subscribe(subscriptions)
    }

    /** Async/callback version of [subscribe] */
    fun subscribeAsync(
            subscriptions: List<PolygonWebSocketSubscription>,
            callback: PolygonCompletionCallback?
    ) = coroutineToCompletionCallback(callback, coroutineScope) { subscribe(subscriptions) }

    /**
     * Unsubscribe to one or more data streams
     *
     * Calling from Java? See [unsubscribeBlocking] and [unsubscribeAsync]
     */
    suspend fun unsubscribe(subscriptions: List<PolygonWebSocketSubscription>) {
        if (subscriptions.isEmpty()) return

        activeConnection?.webSocketSession?.send(
                """{"action": "unsubscribe", "params":"${subscriptions.joinToString(separator = ",")}"}"""
        )
    }

    /** Blocking version of [unsubscribe] */
    fun unsubscribeBlocking(subscriptions: List<PolygonWebSocketSubscription>) = runBlocking {
        unsubscribe(subscriptions)
    }

    /** Callback/async version of [unsubscribe] */
    fun unsubscribeAsync(
            subsciptions: List<PolygonWebSocketSubscription>,
            callback: PolygonCompletionCallback?
    ) = coroutineToCompletionCallback(callback, coroutineScope) { unsubscribe(subsciptions) }

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
                val json = serializer.parseToJsonElement(String(frame.readBytes()))
                processFrameJson(market, feed, json).forEach { listener.onReceive(this, it) }
            }
        } catch (ex: Exception) {
            listener.onReceive(this, RawMessage(frame.readBytes()))
            listener.onError(this, ex)
        }
    }

    @Throws(SerializationException::class)
    private fun processFrameJson(
            market: Market,
            feed: Feed,
            frame: JsonElement,
            collector: MutableList<PolygonWebSocketMessage> = mutableListOf()
    ): List<PolygonWebSocketMessage> {

        if (frame is JsonArray) {
            frame.jsonArray.forEach { processFrameJson(market, feed, it, collector) }
        }

        if (frame is JsonObject) {
            val message =
                    when (market) {
                        Market.Stocks -> parseStockMessage(frame)
                        Market.Options -> parseOptionMessage(frame)
                        Market.Indices -> parseIndicesMessage(frame)
                        Market.Forex -> parseForexMessage(frame)
                        Market.Crypto -> parseCryptoMessage(frame)
                        Market.LaunchpadStocks -> parseLaunchpadMessage(frame)
                        Market.LaunchpadOptions -> parseLaunchpadMessage(frame)
                        Market.LaunchpadForex -> parseLaunchpadMessage(frame)
                        Market.LaunchpadCrypto -> parseLaunchpadMessage(frame)
                        Market.BusinessStocks -> parseBusinessMessage(frame)
                        Market.BusinessOptions -> parseBusinessMessage(frame)
                        Market.BusinessForex -> parseBusinessMessage(frame)
                        Market.BusinessCrypto -> parseBusinessMessage(frame)
                        is Market.Other -> parseOtherMessage(frame)
                    }
            collector.add(message)
        }

        return collector
    }

    private fun parseStockMessage(frame: JsonObject): PolygonWebSocketMessage {
        return when (frame.jsonObject[EVENT_TYPE_MESSAGE_KEY]?.jsonPrimitive?.content) {
            "status" -> serializer.decodeFromJsonElement(StatusMessage.serializer(), frame)
            "T" -> serializer.decodeFromJsonElement(StocksMessage.Trade.serializer(), frame)
            "Q" -> serializer.decodeFromJsonElement(StocksMessage.Quote.serializer(), frame)
            "A", "AM" ->
                    serializer.decodeFromJsonElement(StocksMessage.Aggregate.serializer(), frame)
            else -> RawMessage(frame.toString().toByteArray())
        }
    }

    private fun parseOptionMessage(frame: JsonObject): PolygonWebSocketMessage {
        return when (frame.jsonObject[EVENT_TYPE_MESSAGE_KEY]?.jsonPrimitive?.content) {
            "status" -> serializer.decodeFromJsonElement(StatusMessage.serializer(), frame)
            "T" -> serializer.decodeFromJsonElement(OptionsMessage.Trade.serializer(), frame)
            "Q" -> serializer.decodeFromJsonElement(OptionsMessage.Quote.serializer(), frame)
            "A", "AM" ->
                    serializer.decodeFromJsonElement(OptionsMessage.Aggregate.serializer(), frame)
            else -> RawMessage(frame.toString().toByteArray())
        }
    }

    private fun parseIndicesMessage(frame: JsonObject): PolygonWebSocketMessage {
        return when (frame.jsonObject[EVENT_TYPE_MESSAGE_KEY]?.jsonPrimitive?.content) {
            "status" -> serializer.decodeFromJsonElement(StatusMessage.serializer(), frame)
            "V" -> serializer.decodeFromJsonElement(IndicesMessage.Value.serializer(), frame)
            "A", "AM" ->
                    serializer.decodeFromJsonElement(IndicesMessage.Aggregate.serializer(), frame)
            else -> RawMessage(frame.toString().toByteArray())
        }
    }

    private fun parseForexMessage(frame: JsonObject): PolygonWebSocketMessage {
        return when (frame.jsonObject[EVENT_TYPE_MESSAGE_KEY]?.jsonPrimitive?.content) {
            "status" -> serializer.decodeFromJsonElement(StatusMessage.serializer(), frame)
            "C" -> serializer.decodeFromJsonElement(ForexMessage.Quote.serializer(), frame)
            "CA", "CAS" ->
                    serializer.decodeFromJsonElement(ForexMessage.Aggregate.serializer(), frame)
            else -> RawMessage(frame.toString().toByteArray())
        }
    }

    private fun parseCryptoMessage(frame: JsonObject): PolygonWebSocketMessage {
        return when (frame.jsonObject[EVENT_TYPE_MESSAGE_KEY]?.jsonPrimitive?.content) {
            "status" -> serializer.decodeFromJsonElement(StatusMessage.serializer(), frame)
            "XQ" -> serializer.decodeFromJsonElement(CryptoMessage.Quote.serializer(), frame)
            "XT" -> serializer.decodeFromJsonElement(CryptoMessage.Trade.serializer(), frame)
            "XA", "XAS" ->
                    serializer.decodeFromJsonElement(CryptoMessage.Aggregate.serializer(), frame)
            "XS" ->
                    serializer.decodeFromJsonElement(
                            CryptoMessage.ConsolidatedQuote.serializer(),
                            frame
                    )
            "XL2" -> serializer.decodeFromJsonElement(CryptoMessage.Level2Tick.serializer(), frame)
            else -> RawMessage(frame.toString().toByteArray())
        }
    }

    private fun parseLaunchpadMessage(frame: JsonObject): PolygonWebSocketMessage {
        return when (frame.jsonObject[EVENT_TYPE_MESSAGE_KEY]?.jsonPrimitive?.content) {
            "status" -> serializer.decodeFromJsonElement(StatusMessage.serializer(), frame)
            "LV" ->
                    serializer.decodeFromJsonElement(
                            LaunchpadMessage.LaunchpadValue.serializer(),
                            frame
                    )
            "AM" -> serializer.decodeFromJsonElement(LaunchpadMessage.Aggregate.serializer(), frame)
            else -> RawMessage(frame.toString().toByteArray())
        }
    }

    private fun parseBusinessMessage(frame: JsonObject): PolygonWebSocketMessage {
        return when (frame.jsonObject[EVENT_TYPE_MESSAGE_KEY]?.jsonPrimitive?.content) {
            "status" -> serializer.decodeFromJsonElement(StatusMessage.serializer(), frame)
            "FMV" ->
                    serializer.decodeFromJsonElement(
                            BusinessMessage.FairMarketValue.serializer(),
                            frame
                    )
            else -> RawMessage(frame.toString().toByteArray())
        }
    }

    private fun parseOtherMessage(frame: JsonObject): PolygonWebSocketMessage {
        return RawMessage(frame.toString().toByteArray())
    }

    @Throws(SerializationException::class)
    private fun parseAuthenticationFrame(frame: Frame): Boolean {
        val response = serializer.parseToJsonElement(String(frame.readBytes()))

        if (response is JsonArray) {
            return response.any { parseStatusMessageForAuthenticationResult(it) }
        }

        if (response is JsonObject) {
            return parseStatusMessageForAuthenticationResult(response)
        }

        return false
    }

    @Throws(SerializationException::class)
    private fun parseStatusMessageForAuthenticationResult(message: JsonElement): Boolean {
        if (message.isStatusMessage()) {
            val status = serializer.decodeFromJsonElement(StatusMessage.serializer(), message)
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
            this is JsonObject && JsonPrimitive(EVENT_TYPE_MESSAGE_KEY).contentOrNull == "status"
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
