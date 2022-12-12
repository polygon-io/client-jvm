package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getDailyOpenCloseBlocking] */
suspend fun PolygonStocksClient.getDailyOpenClose(
    symbol: String,
    date: String,
    unadjusted: Boolean,
    vararg opts: PolygonRestOption
): DailyOpenCloseDTO = polygonClient.fetchResult({
    path("v1", "open-close", symbol, date)
    parameters["unadjusted"] = unadjusted.toString()
}, *opts)

@Serializable
data class DailyOpenCloseDTO(
    val status: String? = null,
    val from: String? = null,
    val symbol: String? = null,
    val open: Double? = null,
    val high: Double? = null,
    val low: Double? = null,
    val close: Double? = null,
    val afterHours: Double? = null,
    val preMarket: Double? = null,
    val volume: Double? = null
)