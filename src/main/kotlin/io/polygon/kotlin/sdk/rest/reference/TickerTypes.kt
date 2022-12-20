package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getTickerTypesBlocking] */
@SafeVarargs
suspend fun PolygonReferenceClient.getTickerTypes(params: TickerTypesParameters, vararg opts: PolygonRestOption): TickerTypesResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "tickers", "types")

        params.assetClass?.let { parameters["asset_class"] = it }
        params.locale?.let { parameters["locale"] = it }
    }, *opts)

@Builder
data class TickerTypesParameters(
    /**
     * Filter by asset class.
     */
    val assetClass: String? = null,

    /**
     * Filter by locale.
     */
    val locale: String? = null
)

@Serializable
data class TickerTypesResponse(
    val status: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    val results: List<TickerType>? = null,
)

@Serializable
data class TickerType(
    @SerialName("asset_class") val assetClass: String = "",
    val code: String = "",
    val description: String = "",
    val locale: String = "",
)

/** See [PolygonReferenceClient.getSupportedTickerTypesBlocking] */
@Deprecated("use getTickerTypes instead")
suspend fun PolygonReferenceClient.getSupportedTickerTypes(vararg opts: PolygonRestOption): TickerTypesDTO =
    polygonClient.fetchResult({ path("v2", "reference", "types") }, *opts)

@Serializable
@Deprecated("used in deprecated getSupportedTickerTypes")
data class TickerTypesDTO(
    val status: String? = null,
    val results: TickerTypeResultsDTO? = null
)

@Serializable
@Deprecated("used in deprecated getSupportedTickerTypes")
data class TickerTypeResultsDTO(
    @SerialName("types") val tickerTypes: Map<String, String> = emptyMap(),
    val indexTypes: Map<String, String> = emptyMap()
)