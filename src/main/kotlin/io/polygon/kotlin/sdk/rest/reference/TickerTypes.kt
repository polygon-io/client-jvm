package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedTickerTypesBlocking] */
@Deprecated("use getSupportedTickerTypes with params arg", ReplaceWith("getSupportedTickerTypes(params, *opts)"))
suspend fun PolygonReferenceClient.getSupportedTickerTypes(vararg opts: PolygonRestOption): TickerTypesDTO =
    polygonClient.fetchResult({ path("v2", "reference", "types") }, *opts)

suspend fun PolygonReferenceClient.getSupportedTickerTypes(params: TickerTypeParameters, vararg opts: PolygonRestOption): TickerTypesResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "tickers", "types")

        params.assetClass?.let{ parameters["asset_class"]=it }
        params.locale?.let{ parameters["locale"]=it }
    }, *opts)

@Serializable @Deprecated("used in deprecated getSupportTickerType()")
data class TickerTypesDTO(
    val status: String? = null,
    val results: TickerTypeResultsDTO? = null
)

@Serializable @Deprecated("used in deprecated getSupportTickerType()")
data class TickerTypeResultsDTO(
    @SerialName("types") val tickerTypes: Map<String, String> = emptyMap(),
    val indexTypes: Map<String, String> = emptyMap()
)


@Builder
data class TickerTypeParameters(
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
    // The total number of results for this request.
    val count: Int? = null,
    // A request ID assigned by the server.
    val requestID: String? = null,
    // The status of this request's response.
    val status: String? = null,
    val results: List<TickerType> = emptyList<TickerType>()
)

@Serializable
data class TickerType(
    // An identifier for a group of similar financial instruments.
    val assetClass: String? = null,
    // A code used by Polygon.io to refer to this ticker type.
    val code: String? = null,
    // A short description of this ticker type.
    val description: String? = null,
    // An identifier for a geographical location.
    val locale: String? = null
)