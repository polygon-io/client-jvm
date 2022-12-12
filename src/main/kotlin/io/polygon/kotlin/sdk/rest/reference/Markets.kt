package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedMarketsBlocking] */
suspend fun PolygonReferenceClient.getSupportedMarkets(vararg opts: PolygonRestOption): MarketsDTO =
    polygonClient.fetchResultWithOptions({
        path("v2", "reference", "markets")
    }, *opts)

@Serializable
data class MarketsDTO(
    val status: String? = null,
    val results: List<MarketDescriptionDTO> = emptyList()
)

@Serializable
data class MarketDescriptionDTO(
    val market: String? = null,
    @SerialName("desc") val description: String? = null
)