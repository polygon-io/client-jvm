package io.polygon.kotlin.sdk.rest.indices

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.AggregateDTO
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonIndicesClient.getPreviousCloseBlocking] */
@SafeVarargs
suspend fun PolygonIndicesClient.getPreviousClose(
    ticker: String,
    unadjusted: Boolean = false,
    vararg opts: PolygonRestOption
): PreviousCloseDTO =
    polygonClient.fetchResult({
        path("v2", "aggs", "ticker", ticker, "prev")

        parameters["adjusted"] = (!unadjusted).toString()
    }, *opts)

@Serializable
data class PreviousCloseDTO(
    val status: String? = null,
    val ticker: String? = null,
    val queryCount: Long? = null,
    val resultsCount: Long? = null,
    val results: List<AggregateDTO> = emptyList()
)
