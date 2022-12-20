package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getLastQuoteBlocking] */
@SafeVarargs
@Deprecated("superseded by getLastQuoteV2 and will be replaced in a future verison")
suspend fun PolygonStocksClient.getLastQuote(symbol: String, vararg opts: PolygonRestOption): LastQuoteResultDTO =
    polygonClient.fetchResult({ path("v1", "last_quote", "stocks", symbol) }, *opts)

@Serializable
data class LastQuoteResultDTO(
    val status: String? = null,
    val symbol: String? = null,
    @SerialName("last") val lastQuote: LastQuoteDTO = LastQuoteDTO()
)

@Serializable
data class LastQuoteDTO(
    @SerialName("askprice") val askPrice: Double? = null,
    @SerialName("asksize") val askSize: Long? = null,
    @SerialName("askexchange") val askExchangeId: Long? = null,
    @SerialName("bidprice") val bidPrice: Double? = null,
    @SerialName("bidsize") val bidSize: Long? = null,
    @SerialName("bidexchange") val bidExchangeId: Long? = null,
    val timestamp: Long? = null
)

suspend fun PolygonStocksClient.getLastQuoteV2(ticker: String, vararg opts: PolygonRestOption): LastQuoteResultV2 =
    polygonClient.fetchResult({path("v2", "last", "nbbo", ticker)}, *opts)

@Serializable
data class LastQuoteResultV2(
    @SerialName("request_id") val requestID: String? = null,
    val status: String? = null,
    val results: LastQuoteV2
)

@Serializable
data class LastQuoteV2(
    @SerialName("P") val askPrice: Double? = null,
    @SerialName("S") val askSize: Long? = null,
    @SerialName("T") val ticker: String? = null,
    @SerialName("X") val exchangeID: Int? = null,
    @SerialName("c") val conditions: List<Int> = emptyList(),
    @SerialName("f") val trfTimestampNanos: Long? = null,
    @SerialName("i") val indicators: List<Int> = emptyList(),
    @SerialName("p") val bidPrice: Double? = null,
    @SerialName("s") val bidSize: Int? = null,
    @SerialName("q") val sequenceNumber: Long? = null,
    @SerialName("t") val sipTimestampNanos: Long? = null,
    @SerialName("x") val exchangeId: Long? = null,
    @SerialName("y") val exchangeTimestampNanos: Long? = null,
    @SerialName("z") val tape: String? = null,
)