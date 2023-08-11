package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getTickerDetailsV3Blocking] */
@SafeVarargs
suspend fun PolygonReferenceClient.getTickerDetailsV3(ticker: String, params: TickerDetailsParameters, vararg opts: PolygonRestOption): TickerDetailsResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "tickers", ticker)

        params.date?.let { parameters["date"] = it }
    }, *opts)

@Builder
data class TickerDetailsParameters(

    /**
     * Specify a point in time to get information about the ticker available on that date.
     * When retrieving information from SEC filings, we compare this date with the period of report date on the SEC filing.
     *
     * For example, consider an SEC filing submitted by AAPL on 2019-07-31, with a period of report date ending on 2019-06-29.
     * That means that the filing was submitted on 2019-07-31, but the filing was created based on information from 2019-06-29.
     * If you were to query for AAPL details on 2019-06-29, the ticker details would include information from the SEC filing.
     *
     * Defaults to the most recent available date.
     */
    val date: String? = null
)

@Serializable
data class TickerDetailsResponse(
    val status: String? = null,
    @SerialName("request_id") val requestID: String? = null,
    val results: TickerDetails? = null
)

@Serializable
data class TickerDetails(
    val active: Boolean? = null,
    val address: CompanyAddress? = null,
    val branding: TickerBranding? = null,
    val cik: String? = null,
    @SerialName("composite_figi") val compositeFIGI: String? = null,
    @SerialName("share_class_figi") val shareClassFIGI: String? = null,
    @SerialName("delisted_utc") val delistedUTC: String? = null,
    val description: String? = null,
    @SerialName("homepage_url") val homepageUrl: String? = null,
    @SerialName("list_date") val listDate: String? = null,
    val locale: String? = null,
    val market: String? = null,
    @SerialName("market_cap") val marketCap: Double? = null,
    val name: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("primary_exchange") val primaryExchange: String? = null,
    @SerialName("share_class_shares_outstanding") val shareClassSharesOutstanding: Long? = null,
    @SerialName("weighted_shares_outstanding") val weightedSharesOutstanding: Double? = null,
    @SerialName("sic_code") val sicCode: String? = null,
    @SerialName("sic_description") val sicDescription: String? = null,
    val ticker: String = "",
    @SerialName("ticker_root") val tickerRoot: String? = null,
    @SerialName("ticker_suffix") val tickerSuffix: String? = null,
    @SerialName("total_employees") val totalEmployees: Long? = null,
    val type: String? = null,
    @SerialName("currency_name") val currencyName: String? = null,
    @SerialName("round_lot") val roundLot: Long? = null
)

@Serializable
data class CompanyAddress(
    @SerialName("address1") val address1: String? = null,
    val city: String? = null,
    val state: String? = null,
    @SerialName("postal_code") val postalCode: String? = null
)

@Serializable
data class TickerBranding(
    @SerialName("icon_url") val iconUrl: String? = null,
    @SerialName("logo_url") val logoUrl: String? = null
)

/** See [PolygonReferenceClient.getTickerDetailsBlocking] */
@Deprecated("superseded by getTickerDetailsV3", ReplaceWith("getTickerDetailsV3(symbol, params, *opts)"))
suspend fun PolygonReferenceClient.getTickerDetails(symbol: String, vararg opts: PolygonRestOption): TickerDetailsDTO =
    polygonClient.fetchResult({
        path("v1", "meta", "symbols", symbol, "company")
    }, *opts)

@Serializable
@Deprecated("used in deprecated getTickerDetails")
data class TickerDetailsDTO(
    @SerialName("logo") val logoUrl: String? = null,
    val exchange: String? = null,
    val exchangeSymbol: String? = null,
    val name: String? = null,
    val symbol: String? = null,
    val description: String? = null,
    @SerialName("updated") val lastUpdated: String? = null,
    @SerialName("listdate") val listDate: String? = null,
    val type: String? = null,
    val cik: String? = null,
    val bloomberg: String? = null,
    val figi: String? = null,
    val lei: String? = null,
    val sic: Long? = null,
    val country: String? = null,
    val industry: String? = null,
    val sector: String? = null,
    @SerialName("marketcap") val marketCap: Double? = null,
    val employees: Long? = null,
    val phone: String? = null,
    val ceo: String? = null,
    @SerialName("hq_address") val headquartersAddress: String? = null,
    @SerialName("hq_state") val headquartersState: String? = null,
    @SerialName("hq_country") val headquartersCountry: String? = null,
    val url: String? = null,
    val similar: List<String> = emptyList(),
    val tags: List<String> = emptyList()
)
