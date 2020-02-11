package io.polygon.kotlin.sdk.rest.reference

import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun PolygonReferenceRestClient.getSupportedMarkets(): MarketsDTO =
    polygonClient.withHttpClient { httpClient ->
        val url = polygonClient.urlBuilder.path("v2", "reference", "markets").build()
        return httpClient.get(url)
    }

@Serializable
data class MarketsDTO(
    val status: String,
    val results: List<MarketDescriptionDTO> = listOf()
)

@Serializable
data class MarketDescriptionDTO(
    val market: String,
    @SerialName("desc") val description: String
)