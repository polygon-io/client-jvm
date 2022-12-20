package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedMarketsBlocking] */
@SafeVarargs
@Deprecated("This API is no longer supported and will be sunset some time in the future")
suspend fun PolygonReferenceClient.getSupportedMarkets(vararg opts: PolygonRestOption): MarketsDTO =
    polygonClient.fetchResult({
        path("v2", "reference", "markets")
    }, *opts)

@Serializable
@Deprecated("used in deprecated getSupportedMarkets")
data class MarketsDTO(
    val status: String? = null,
    val results: List<MarketDescriptionDTO> = emptyList()
)

@Serializable
@Deprecated("used in deprecated getSupportedMarkets")
data class MarketDescriptionDTO(
    val market: String? = null,
    @SerialName("desc") val description: String? = null
)