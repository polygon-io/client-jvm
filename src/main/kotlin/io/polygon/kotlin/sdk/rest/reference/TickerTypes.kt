package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedTickerTypesBlocking] */
suspend fun PolygonReferenceClient.getSupportedTickerTypes(): TickerTypesDTO =
    polygonClient.fetchResult { path("v2", "reference", "types") }

@Serializable
data class TickerTypesDTO(
    val status: String? = null,
    val results: TickerTypeResultsDTO? = null
)

@Serializable
data class TickerTypeResultsDTO(
    @SerialName("types") val tickerTypes: Map<String, String> = emptyMap(),
    val indexTypes: Map<String, String> = emptyMap()
)