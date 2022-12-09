package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getMarketStatusBlocking] */
suspend fun PolygonReferenceClient.getMarketStatus(vararg opts: PolygonRestOption): MarketStatusDTO =
    polygonClient.fetchResultWithOptions({
        path("v1", "marketstatus", "now")
    }, *opts)

@Serializable
data class MarketStatusDTO(
    val market: String? = null,
    val serverTime: String? = null,
    val exchanges: ExchangesStatusDTO = ExchangesStatusDTO(),
    val currencies: CurrenciesStatusDTO = CurrenciesStatusDTO()
)

@Serializable
data class ExchangesStatusDTO(
    val nyse: String? = null,
    val nasdaq: String? = null,
    val otc: String? = null
)

@Serializable
data class CurrenciesStatusDTO(
    val fx: String? = null,
    val crypto: String? = null
)