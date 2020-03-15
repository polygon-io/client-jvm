package io.polygon.kotlin.sdk.rest.reference

import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getMarketHolidaysBlocking] */
suspend fun PolygonReferenceClient.getMarketHolidays(): List<MarketHolidayDTO> =
    polygonClient.fetchResult {
        path("v1", "marketstatus", "upcoming")
    }

@Serializable
data class MarketHolidayDTO(
    val exchange: String? = null,
    val name: String? = null,
    val status: String? = null,
    val date: String? = null,
    val open: String? = null,
    val close: String? = null
)