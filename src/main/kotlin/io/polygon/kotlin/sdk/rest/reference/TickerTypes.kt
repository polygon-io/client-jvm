package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedTickerTypesBlocking] */
suspend fun PolygonReferenceClient.getSupportedTickerTypes(vararg opts: PolygonRestOption): TickerTypesDTO =
    polygonClient.fetchResultWithOptions({ path("v2", "reference", "types") }, *opts)

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