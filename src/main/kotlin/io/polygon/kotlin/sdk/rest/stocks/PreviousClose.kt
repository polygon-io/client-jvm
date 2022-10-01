package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.AggregateDTO
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
