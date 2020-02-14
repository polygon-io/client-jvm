package io.polygon.kotlin.sdk.rest.reference

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceRestClient.getSupportedMarketsBlocking] */
suspend fun PolygonReferenceRestClient.getSupportedMarkets(): MarketsDTO =
    polygonClient.fetchResult {
        path("v2", "reference", "markets")
    }

@Serializable
data class MarketsDTO(
    val status: String? = null,
    val results: List<MarketDescriptionDTO> = emptyList()
)

@Serializable
data class MarketDescriptionDTO(
    val market: String,
    @SerialName("desc") val description: String
)