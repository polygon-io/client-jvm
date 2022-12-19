package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getExchangesBlocking] */
@SafeVarargs
suspend fun PolygonReferenceClient.getExchanges(params: ExchangesParameters, vararg opts: PolygonRestOption): ExchangesResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "exchanges")

        params.assetClass?.let { parameters["asset_class"] = it }
        params.locale?.let { parameters["locale"] = it }
    }, *opts)

@Builder
data class ExchangesParameters(

    /**
     * Filter by asset class.
     */
    val assetClass: String? = null,

    /**
     * Filter by locale.
     */
    val locale: String? = null,
)

@Serializable
data class ExchangesResponse(
    val status: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    val results: List<Exchange>? = null,
)

@Serializable
data class Exchange(
    @SerialName("id") val id: Int = 0,
    @SerialName("acryonm") val acryonm: String? = null,
    @SerialName("asset_class") val assetClass: String = "",
    @SerialName("locale") val locale: String = "",
    @SerialName("mic") val mic: String? = null,
    @SerialName("operating_mic") val operatingMic: String? = null,
    @SerialName("name") val name: String = "",
    @SerialName("participant_id") val participantId: String? = null,
    @SerialName("type") val type: String = "",
    @SerialName("url") val url: String? = null,
)
