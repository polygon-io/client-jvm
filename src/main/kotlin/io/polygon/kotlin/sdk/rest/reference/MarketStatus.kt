package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
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
    val s_and_p: String? = null,
    val societe_generale: String? = null,
    val msci: String? = null,
    val ftse_russell: String? = null,
    val mstar: String? = null,
    val mstarc: String? = null,
    val cccy: String? = null,
    val nasdaq: String? = null,
    val dow_jones: String? = null
)

@Serializable
data class CurrenciesStatusDTO(
    val fx: String? = null,
    val crypto: String? = null
)
