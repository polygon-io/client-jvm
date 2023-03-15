package io.polygon.kotlin.sdk.rest.indices


import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonIndicesClient.getDailyOpenCloseBlocking] */
@SafeVarargs
suspend fun PolygonIndicesClient.getDailyOpenClose(
    ticker: String,
    date: String,
    unadjusted: Boolean,
    vararg opts: PolygonRestOption
): DailyOpenCloseDTO = polygonClient.fetchResult({
    path("v1", "open-close", ticker, date)
    parameters["unadjusted"] = unadjusted.toString()
}, *opts)

@Serializable
data class DailyOpenCloseDTO(
    val afterHours: Double? = null,
    val close: Double? = null,
    val from: String? = null,
    val high: Double? = null,
    val low: Double? = null,
    val open: Double? = null,
    val preMarket: Double? = null,
    val status: String? = null,
    val symbol: String? = null,
    val volume: Double? = null
)