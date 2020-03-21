package io.polygon.kotlin.sdk.websocket

/**
 * See https://polygon.io/sockets for details
 */
data class PolygonWebSocketSubscription(
    val channel: PolygonWebSocketChannel,
    val symbol: String
) {
    override fun toString() = "${channel.prefix}.$symbol"
}

/**
 * Available web socket channels. See https://polygon.io/sockets for details
 */
sealed class PolygonWebSocketChannel(val prefix: String) {

    /**
     * Usable in the [WebSocketCluster.Stocks] cluster
     */
    sealed class Stocks(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object Trades: Stocks("T")
        object Quotes: Stocks("Q")
        object AggPerSecond : Stocks("A")
        object AggPerMinute : Stocks("AM")
    }

    /**
     * Usable in the [WebSocketCluster.Forex] cluster
     */
    sealed class Forex(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object Currencies: Forex("C")
        object CurrenciesAggPerMinute: Forex("CA")
        object Bonds: Forex("BONDS")
        object Commodities: Forex("COMMODITIES")
        object Metals: Forex("METALS")
    }

    /**
     * Usable in the [WebSocketCluster.Crypto] cluster
     */
    sealed class Crypto(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object Trades : Crypto("XT")
        object Quotes : Crypto("XQ")
        object Level2Books : Crypto("XL2")
    }

    /**
     * Use this if there's a new channel that this SDK doesn't fully support yet
     */
    class Other(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix)
}