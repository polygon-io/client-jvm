package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getMarketStatusBlocking] */
@SafeVarargs
suspend fun PolygonReferenceClient.getMarketStatus(vararg opts: PolygonRestOption): MarketStatusDTO =
    polygonClient.fetchResult({
        path("v1", "marketstatus", "now")
    }, *opts)

@Serializable
data class MarketStatusDTO(
    val market: String? = null,
    val serverTime: String? = null,
    val afterHours: Boolean = false,
    val earlyHours: Boolean = false,
    val exchanges: ExchangesStatusDTO = ExchangesStatusDTO(),
    val indicesGroups: IndicesGroupsStatusDTO = IndicesGroupsStatusDTO(),
    val currencies: CurrenciesStatusDTO = CurrenciesStatusDTO()
)

@Serializable
data class ExchangesStatusDTO(
    val nyse: String? = null,
    val nasdaq: String? = null,
    val otc: String? = null
)

@Serializable
data class IndicesGroupsStatusDTO(
    @SerialName("s_and_p") val sAndP: String? = null,
    @SerialName("societe_generale") val societeGenerale: String? = null,
    val msci: String? = null,
    @SerialName("ftse_russell") val ftseRussell: String? = null,
    val mstar: String? = null,
    val mstarc: String? = null,
    val cccy: String? = null,
    val nasdaq: String? = null,
    @SerialName("dow_jones") val dowJones: String? = null
)

@Serializable
data class CurrenciesStatusDTO(
    val fx: String? = null,
    val crypto: String? = null
)
