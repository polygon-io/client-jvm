package io.polygon.kotlin.sdk.rest.stocks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getPreviousCloseBlocking] */
suspend fun PolygonStocksClient.getPreviousClose(symbol: String, unadjusted: Boolean = false): PreviousCloseDTO =
    polygonClient.fetchResult { path("v2", "aggs", "ticker", symbol, "prev") }

@Serializable
data class PreviousCloseDTO(
    val status: String? = null,
    val ticker: String? = null,
    val queryCount: Long? = null,
    val resultsCount: Long? = null,
    val adjusted: Boolean? = null,
    val results: List<AggregateDTO> = emptyList()
)

@Serializable
data class AggregateDTO(
    @SerialName("T") val ticker: String? = null,
    @SerialName("v") val volume: Double? = null,
    @SerialName("o") val open: Double? = null,
    @SerialName("c") val close: Double? = null,
    @SerialName("l") val low: Double? = null,
    @SerialName("h") val high: Double? = null,
    @SerialName("t") val timestampMillis: Long? = null,
    @SerialName("n") val numItemsInWindow: Long? = null
)