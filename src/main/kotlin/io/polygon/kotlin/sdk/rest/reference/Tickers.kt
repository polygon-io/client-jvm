package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedTickersBlocking] */
suspend fun PolygonReferenceClient.getSupportedTickers(
    params: SupportedTickersParameters
): TickersDTO =
    polygonClient.fetchResult {
        path("v2", "reference", "tickers")

        parameters["sort"] = if (params.sortDescending) {
            "-${params.sortBy}"
        } else {
            params.sortBy
        }

        parameters["perpage"] = params.tickersPerPage.toString()
        parameters["page"] = params.page.toString()

        params.type?.let { parameters["type"] = it }
        params.market?.let { parameters["market"] = it }
        params.locale?.let { parameters["locale"] = it }
        params.search?.let { parameters["search"] = it }
        params.activeSymbolsOnly?.let { parameters["active"] = it.toString() }
    }

@Builder
data class SupportedTickersParameters(

    /**
     * Which field to sort by (ex: "ticker", "type", etc). Defaults to "ticker"
     */
    @DefaultValue("ticker")
    val sortBy: String = "ticker",

    /**
     * Whether or not to sort ascending (A-Z) or descending (Z-A). Defaults to false
     */
    @DefaultValue("false")
    val sortDescending: Boolean = false,

    /**
     * (Optional) The type of tickers to return.
     * See [PolygonReferenceClient.getSupportedTickerTypes] for supported types.
     * If not set, returns all ticker types.
     */
    val type: String? = null,

    /**
     * (Optional) If set, only return tickers for a specific market (ex: "stocks", "indices").
     * See [PolygonReferenceClient.getSupportedMarkets].
     * If not set, returns tickers from all markets
     */
    val market: String? = null,

    /**
     * (Optional) If set, only return tickers for a specific region/locale.
     * See [PolygonReferenceClient.getSupportedLocales].
     * If not set, returns tickers for all regions/locales.
     */
    val locale: String? = null,

    /**
     * (Optional) Search the name of tickers (ex: "microsoft")
     */
    val search: String? = null,

    /**
     * How many items to be on each page during pagination. Max: 50, defaults to 25.
     */
    @DefaultValue("25")
    val tickersPerPage: Int = 25,

    /**
     * The page of results to return. Defaults to 1.
     */
    @DefaultValue("1")
    val page: Int = 1,

    /**
     * (Optional) Filter for only active or inactive symbols.
     * If not set, returns both active and inactive symbols
     */
    val activeSymbolsOnly: Boolean? = null
)

@Serializable
data class TickersDTO(
    val status: String? = null,
    val page: Int,
    @SerialName("perPage") val tickersPerPage: Int? = null,
    val count: Int,
    val tickers: List<TickerDTO> = emptyList()
)

@Serializable
data class TickerDTO(
    val ticker: String? = null,
    val name: String? = null,
    val market: String? = null,
    val locale: String? = null,
    val type: String? = null,
    val currency: String? = null,
    val active: Boolean = false,
    @SerialName("primaryExch") val primaryExchange: String? = null,
    @SerialName("updated") val lastUpdated: String? = null,
    val url: String? = null,
    val codes: Map<String, String> = emptyMap(),
    val attrs: Map<String, String> = emptyMap()
)