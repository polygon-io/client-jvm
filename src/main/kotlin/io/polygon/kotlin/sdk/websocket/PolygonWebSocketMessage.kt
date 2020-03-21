package io.polygon.kotlin.sdk.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Messages that Polygon web sockets might return. See https://polygon.io/sockets for documentation
 */
sealed class PolygonWebSocketMessage {

    @Serializable
    data class StatusMessage(
        val ev: String? = null,
        val status: String? = null,
        val message: String? = null
    ) : PolygonWebSocketMessage()

    sealed class CryptoMessage : PolygonWebSocketMessage() {

        @Serializable
        data class Quote(
            @SerialName("ev") val eventType: String? = null,
            @SerialName("pair") val cryptoPair: String? = null,
            @SerialName("lp") val lastTradePrice: Double? = null,
            @SerialName("ls") val lastTradeSize: Double? = null,
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

    }

    data class RawMessage(val data: ByteArray) : PolygonWebSocketMessage()

}