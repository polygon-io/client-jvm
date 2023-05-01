package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.Paginatable
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedTickersBlocking] */
@SafeVarargs
suspend fun PolygonReferenceClient.getSupportedTickers(
    params: SupportedTickersParameters,
    vararg opts: PolygonRestOption
): TickersDTO =
    polygonClient.fetchResult({
        path("v3", "reference", "tickers")

        params.ticker?.let { parameters["ticker"] = it }
        params.tickerLT?.let { parameters["ticker.lt"] = it }
        params.tickerLTE?.let { parameters["ticker.lte"] = it }
        params.tickerGT?.let { parameters["ticker.gt"] = it }
        params.tickerGTE?.let { parameters["ticker.gte"] = it }
        params.type?.let { parameters["type"] = it }
        params.market?.let { parameters["market"] = it }
        params.exchange?.let { parameters["exchange"] = it }
        params.cusip?.let { parameters["cusip"] = it }
        params.cik?.let { parameters["cik"] = it }
        params.date?.let { parameters["date"] = it }
        params.search?.let { parameters["search"] = it }
        params.activeSymbolsOnly?.let { parameters["active"] = it.toString() }
        parameters["order"] = if (params.sortDescending) {
            "desc"
        } else {
            "asc"
        }
        params.sortBy?.let { parameters["sort"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
    }, *opts)

@Builder
data class SupportedTickersParameters(

    /**
     * Specify a ticker symbol. Defaults to empty string which queries all tickers.
     */
    @DefaultValue("")
    val ticker: String = "",

    /**
     * Return results where this field is less than the value.
     */
    val tickerLT: String? = null,

    /**
     * Return results where this field is less than or equal to the value.
     */
    val tickerLTE: String? = null,

    /**
     * Return results where this field is greater than the value.
     */
    val tickerGT: String? = null,

    /**
     * Return results where this field is greater than or equal to the value.
     */
    val tickerGTE: String? = null,

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
     * Specify the asset's primary exchange Market Identifier Code (MIC) according to ISO 10383.
     * Defaults to empty string which queries all exchanges.
     */
    val exchange: String? = null,

    /**
     * Specify the CUSIP code of the asset you want to search for.
     * Find more information about CUSIP codes at their website. Defaults to empty string which queries all CUSIPs.
     * Note: Although you can query by CUSIP, due to legal reasons we do not return the CUSIP in the response.
     */
    val cusip: String? = null,

    /**
     * Specify the CIK of the asset you want to search for.
     * Find more information about CIK codes at their website.
     * Defaults to empty string which queries all CIKs.
     */
    val cik: String? = null,

    /**
     * Specify a point in time to retrieve tickers available on that date.
     * Defaults to the most recent available date.
     */
    val date: String? = null,

    /**
     * (Optional) Search the name of tickers (ex: "microsoft")
     */
    val search: String? = null,

    /**
     * (Optional) Filter for only active or inactive symbols.
     * If not set, returns both active and inactive symbols
     */
    val activeSymbolsOnly: Boolean? = null,

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
     * Limit the size of the response, default is 100 and max is 1000.
     *
     * If your query returns more than the max limit and you want to retrieve the next page of results,
     * see the next_url response attribute.
     */
    @DefaultValue("10")
    val limit: Int = 10

)

@Serializable
data class TickersDTO(
    val status: String? = null,
    val count: Int? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    @SerialName("results") override val results: List<TickerDTO>? = null
) : Paginatable<TickerDTO>

@Serializable
data class TickerDTO(
    val active: Boolean = false,
    val cik: String? = null,
    @SerialName("composite_figi") val compositeFigi: String? = null,
    @SerialName("currency_name") val currencyName: String? = null,
    @SerialName("last_updated_utc") val lastUpdatedUtc: String? = null,
    @SerialName("delisted_utc") val delistedUtc: String? = null,
    val locale: String? = null,
    val market: String? = null,
    val name: String? = null,
    @SerialName("primary_exchange") val primaryExchange: String? = null,
    @SerialName("share_class_figi") val shareClassFigi: String? = null,
    val ticker: String? = null,
    val type: String? = null
)
