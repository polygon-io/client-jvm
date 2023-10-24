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
     * Usable in the [PolygonWebSocketCluster.Stocks] cluster
     */
    sealed class Stocks(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object Trades: Stocks("T")
        object Quotes: Stocks("Q")
        object AggPerSecond : Stocks("A")
        object AggPerMinute : Stocks("AM")
        object AggPerMinuteLaunchpad : Stocks("AM")
    }

    /**
     * Usable in the [PolygonWebSocketCluster.LaunchpadStocks] cluster
     */
    sealed class LaunchpadStocks(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object AggPerMinute : LaunchpadStocks("AM")
        object LaunchpadValue : LaunchpadStocks("LV")
    }

    /**
     * Usable in the [PolygonWebSocketCluster.Options] cluster
     */
    sealed class Options(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object Trades: Options("T")
        object Quotes: Options("Q")
        object AggPerSecond : Options("A")
        object AggPerMinute : Options("AM")
    }

    /**
     * Usable in the [PolygonWebSocketCluster.LaunchpadOptions] cluster
     */
    sealed class LaunchpadOptions(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object AggPerMinute : LaunchpadOptions("AM")
        object LaunchpadValue : LaunchpadOptions("LV")
    }

    /**
     * Usable in the [PolygonWebSocketCluster.Forex] cluster
     */
    sealed class Forex(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object Quotes: Forex("C")
        object AggPerSecond : Forex("CAS")
        object AggPerMinute: Forex("CA")
    }

    /**
     * Usable in the [PolygonWebSocketCluster.LaunchpadForex] cluster
     */
    sealed class LaunchpadForex(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object AggPerMinute : LaunchpadForex("AM")
        object LaunchpadValue : LaunchpadForex("LV")
    }

    /**
     * Usable in the [PolygonWebSocketCluster.Crypto] cluster
     */
    sealed class Crypto(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object Trades : Crypto("XT")
        object Quotes : Crypto("XQ")
        object AggPerSecond : Crypto("XAS")
        object AggPerMinute : Crypto("XA")
        object ConsolidatedQuotes : Crypto("XS")
        object Level2Books : Crypto("XL2")
    }

    /**
     * Usable in the [PolygonWebSocketCluster.LaunchpadCrypto] cluster
     */
    sealed class LaunchpadCrypto(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object AggPerMinute : LaunchpadCrypto("AM")
        object LaunchpadValue : LaunchpadCrypto("LV")
    }

    /**
     * Usable in the [PolygonWebSocketCluster.Indices] cluster
     */
    sealed class Indices(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object AggPerSecond : Indices("A")
        object AggPerMinute : Indices("AM")
        object Value : Indices("V")
    }

    sealed class Business(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix) {
        object FairMarketValue : Business("FMV")
    }

    /**
     * Use this if there's a new channel that this SDK doesn't fully support yet
     */
    class Other(channelPrefix: String) : PolygonWebSocketChannel(channelPrefix)
}