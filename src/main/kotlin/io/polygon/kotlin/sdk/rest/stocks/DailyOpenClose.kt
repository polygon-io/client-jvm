package io.polygon.kotlin.sdk.rest.stocks

import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getDailyOpenCloseBlocking] */
suspend fun PolygonStocksClient.getDailyOpenClose(symbol: String, date: String): DailyOpenCloseDTO =
    polygonClient.fetchResult { path("v1", "open-close", symbol, date) }

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
    val volume: Long? = null
)