package io.polygon.kotlin.sdk.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Messages that Polygon web sockets might return. See https://polygon.io/sockets for documentation
 */
sealed class PolygonWebSocketMessage {

    data class RawMessage(val data: ByteArray) : PolygonWebSocketMessage()

    @Serializable
    data class StatusMessage(
        val ev: String? = null,
        val status: String? = null,
        val message: String? = null
    ) : PolygonWebSocketMessage()

    sealed class StocksMessage : PolygonWebSocketMessage() {

        @Serializable
        data class Trade(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("sym") val ticker: String? = null,
            @SerialName("x") val exchangeId: Long? = null,
            @SerialName("i") val tradeId: String? = null,
            @SerialName("z") val tape: String? = null,
            @SerialName("p") val price: Double? = null,
            @SerialName("s") val size: Double? = null,
            @SerialName("c") val conditions: List<Int> = emptyList(),
            @SerialName("t") val timestampMillis: Long? = null,
            @SerialName("q") val sequenceNumber: Long? = null,
            @SerialName("trfi") val trf : Long? = null,
            @SerialName("trft") val trfTimestampMillis: Long? = null
        ) : StocksMessage()

        @Serializable
        data class Quote(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("sym") val ticker: String? = null,
            @SerialName("bx") val bidExchangeId: Long? = null,
            @SerialName("bp") val bidPrice: Double? = null,
            @SerialName("bs") val bidSize: Double? = null,
            @SerialName("ax") val askExchangeId: Long? = null,
            @SerialName("ap") val askPrice: Double? = null,
            @SerialName("as") val askSize: Double? = null,
            @SerialName("c") val condition: Int? = null,
            @SerialName("i") val indicators: List<Int> = emptyList(),
            @SerialName("t") val timestampMillis: Long? = null,
            @SerialName("q") val sequenceNumber: Long? = null,
            @SerialName("z") val tape: String? = null
        ) : StocksMessage()

        @Serializable
        data class Aggregate(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("sym") val ticker: String? = null,
            @SerialName("v") val volume: Double? = null,
            @SerialName("av") val accumulatedVolume: Double? = null,
            @SerialName("op") val officialOpenPrice: Double? = null,
            @SerialName("vw") val volumeWeightedAveragePrice: Double? = null,
            @SerialName("o") val openPrice: Double? = null,
            @SerialName("c") val closePrice: Double? = null,
            @SerialName("h") val highPrice: Double? = null,
            @SerialName("l") val lowPrice: Double? = null,
            @SerialName("a") val averagePrice: Double? = null,
            @SerialName("z") val averageTradeSize: Double? = null,
            @SerialName("s") val startTimestampMillis: Long? = null,
            @SerialName("e") val endTimestampMillis: Long? = null,
            @SerialName("otc") val otcTicker: Boolean? = null
        ) : StocksMessage()
    }

    sealed class OptionsMessage : PolygonWebSocketMessage() {

        @Serializable
        data class Trade(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("sym") val ticker: String? = null,
            @SerialName("x") val exchangeId: Long? = null,
            @SerialName("p") val price: Double? = null,
            @SerialName("s") val size: Double? = null,
            @SerialName("c") val conditions: List<Int> = emptyList(),
            @SerialName("t") val timestampMillis: Long? = null,
            @SerialName("q") val sequenceNumber: Long? = null
        ) : OptionsMessage()

        @Serializable
        data class Quote(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("sym") val ticker: String? = null,
            @SerialName("bx") val bidExchangeId: Long? = null,
            @SerialName("bp") val bidPrice: Double? = null,
            @SerialName("bs") val bidSize: Double? = null,
            @SerialName("ax") val askExchangeId: Long? = null,
            @SerialName("ap") val askPrice: Double? = null,
            @SerialName("as") val askSize: Double? = null,
            @SerialName("t") val timestampMillis: Long? = null,
            @SerialName("q") val sequenceNumber: Long? = null
        ) : OptionsMessage()

        @Serializable
        data class Aggregate(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("sym") val ticker: String? = null,
            @SerialName("v") val volume: Double? = null,
            @SerialName("av") val accumulatedVolume: Double? = null,
            @SerialName("op") val officialOpenPrice: Double? = null,
            @SerialName("vw") val volumeWeightedAveragePrice: Double? = null,
            @SerialName("o") val openPrice: Double? = null,
            @SerialName("c") val closePrice: Double? = null,
            @SerialName("h") val highPrice: Double? = null,
            @SerialName("l") val lowPrice: Double? = null,
            @SerialName("a") val averagePrice: Double? = null,
            @SerialName("z") val averageTradeSize: Double? = null,
            @SerialName("s") val startTimestampMillis: Long? = null,
            @SerialName("e") val endTimestampMillis: Long? = null
        ) : OptionsMessage()
    }

    sealed class ForexMessage : PolygonWebSocketMessage() {

        @Serializable
        data class Quote(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("p") val currencyPair: String? = null,
            @SerialName("x") val exchangeId: Long? = null,
            @SerialName("a") val askPrice: Double? = null,
            @SerialName("b") val bidPrice: Double? = null,
            @SerialName("t") val timestampMillis: Long? = null
        ) : ForexMessage()

        @Serializable
        data class Aggregate(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("pair") val currencyPair: String? = null,
            @SerialName("o") val openPrice: Double? = null,
            @SerialName("c") val closePrice: Double? = null,
            @SerialName("h") val highPrice: Double? = null,
            @SerialName("l") val lowPrice: Double? = null,
            @SerialName("v") val volumeInAgg: Double? = null,
            @SerialName("s") val startTimestampMillis: Long? = null,
            @SerialName("e") val endTimestampMillis: Long? = null
        ) : ForexMessage()
    }

    sealed class CryptoMessage : PolygonWebSocketMessage() {

        @Serializable
        data class Quote(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("pair") val cryptoPair: String? = null,
            @SerialName("bp") val bidPrice: Double? = null,
            @SerialName("bs") val bidSize: Double? = null,
            @SerialName("ap") val askPrice: Double? = null,
            @SerialName("as") val askSize: Double? = null,
            @SerialName("t") val exchangeTimesampeMillis: Long? = null,
            @SerialName("x") val exchangeId: Long? = null,
            @SerialName("r") val receivedAtPolygonTimestamp: Long? = null
        ) : CryptoMessage()

        @Serializable
        data class Trade(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("pair") val cryptoPair: String? = null,
            @SerialName("p") val price: Double? = null,
            @SerialName("s") val size: Double? = null,
            @SerialName("c") val conditions: List<Int> = emptyList(),
            @SerialName("i") val tradeId: String? = null,
            @SerialName("t") val exchangeTimestampMillis: Long? = null,
            @SerialName("x") val exchangeId: Long? = null,
            @SerialName("r") val receivedAtPolygonTimestamp: Long? = null
        ) : CryptoMessage()

        @Serializable
        data class Aggregate(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("pair") val cryptoPair: String? = null,
            @SerialName("o") val openPrice: Double? = null,
            @SerialName("c") val closePrice: Double? = null,
            @SerialName("h") val highPrice: Double? = null,
            @SerialName("l") val lowPrice: Double? = null,
            @SerialName("v") val volumeInAgg: Double? = null,
            @SerialName("s") val aggStartTimestamp: Long? = null,
            @SerialName("e") val aggEndTimestamp: Long? = null,
            @SerialName("vw") val volumeWeightedAveragePrice: Double? = null,
            @SerialName("z") val averageTradeSize: Double? = null
        ) : CryptoMessage()

        @Serializable
        data class ConsolidatedQuote(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("pair") val cryptoPair: String? = null,
            @SerialName("as") val askSize: Double? = null,
            @SerialName("ap") val askPrice: Double? = null,
            @SerialName("ax") val askExchangeId: Long? = null,
            @SerialName("bs") val bidSize: Double? = null,
            @SerialName("bp") val bidPrice: Double? = null,
            @SerialName("bx") val bidExchangeId: Long? = null,
            @SerialName("t") val timestamp: Long? = null
        ) : CryptoMessage()

        @Serializable
        data class Level2Tick(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("pair") val cryptoPair: String? = null,
            @SerialName("b") val bidPrices: List<List<Double>> = emptyList(),
            @SerialName("a") val askPrices: List<List<Double>> = emptyList(),
            @SerialName("t") val timestampMillis: Long? = null,
            @SerialName("x") val exchangeId: Long? = null,
            @SerialName("r") val receivedAtPolygonTimestamp: Long? = null
        ) : CryptoMessage()
    }

    sealed class IndicesMessage : PolygonWebSocketMessage() {

        @Serializable
        data class Value(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("val") val value: Double? = null,
            @SerialName("T") val ticker: String? = null,
            @SerialName("t") val exchangeTimestampMillis: Long? = null
        ) : IndicesMessage()

        @Serializable
        data class Aggregate(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("sym") val ticker: String? = null,
            @SerialName("op") val officialOpenPrice: Double? = null,
            @SerialName("o") val openPrice: Double? = null,
            @SerialName("c") val closePrice: Double? = null,
            @SerialName("h") val highPrice: Double? = null,
            @SerialName("l") val lowPrice: Double? = null,
            @SerialName("s") val startTimestampMillis: Long? = null,
            @SerialName("e") val endTimestampMillis: Long? = null
        ) : IndicesMessage()

    }

    sealed class LaunchpadMessage : PolygonWebSocketMessage() {

        @Serializable
        data class LaunchpadValue(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("val") val value: Double? = null,
            @SerialName("sym") val symbol: String? = null,
            @SerialName("t") val timestampMillis: Long? = null
        ) : LaunchpadMessage()

        @Serializable
        data class Aggregate(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("sym") val ticker: String? = null,
            @SerialName("op") val officialOpenPrice: Double? = null,
            @SerialName("o") val openPrice: Double? = null,
            @SerialName("c") val closePrice: Double? = null,
            @SerialName("h") val highPrice: Double? = null,
            @SerialName("l") val lowPrice: Double? = null,
            @SerialName("s") val startTimestampMillis: Long? = null,
            @SerialName("e") val endTimestampMillis: Long? = null
        ) : LaunchpadMessage()

    }

    sealed class BusinessMessage : PolygonWebSocketMessage() {

        @Serializable
        data class FairMarketValue(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("fmv") val value: Double? = null,
            @SerialName("sym") val symbol: String? = null,
            @SerialName("t") val timestampMillis: Long? = null
        ) : BusinessMessage()
    }
}
