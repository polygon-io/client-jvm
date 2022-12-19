package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.ComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.applyComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.Paginatable
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SafeVarargs
suspend fun PolygonReferenceClient.getDividends(
    params: DividendsParameters,
    vararg opts: PolygonRestOption
): DividendsResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "dividends")

        applyComparisonQueryFilterParameters("ticker", params.ticker)
        applyComparisonQueryFilterParameters("ex_dividend_date", params.exDividendDate)
        applyComparisonQueryFilterParameters("record_date", params.recordDate)
        applyComparisonQueryFilterParameters("declaration_date", params.declarationDate)
        applyComparisonQueryFilterParameters("pay_date", params.payDate)
        params.frequency?.let { parameters["frequency"] = it.toString() }
        applyComparisonQueryFilterParameters("cash_amount", params.cashAmount)
        params.dividendType?.let { parameters["dividend_type"] = it }
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }

    }, *opts)

@Builder
data class DividendsParameters(

    /**
     * Query by ticker.
     */
    val ticker: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by ex-dividend date with the format YYYY-MM-DD.
     */
    val exDividendDate: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by record date with the format YYYY-MM-DD.
     */
    val recordDate: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by declaration date with the format YYYY-MM-DD.
     */
    val declarationDate: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by pay date with the format YYYY-MM-DD.
     */
    val payDate: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by the number of times per year the dividend is paid out.
     * Possible values are 0 (one-time), 1 (annually), 2 (bi-annually), 4 (quarterly), and 12 (monthly).
     */
    val frequency: Int? = null,

    /**
     * Query by the cash amount of the dividend.
     */
    val cashAmount: ComparisonQueryFilterParameters<Double>? = null,

    /**
     * Query by the type of dividend.
     *
     * Dividends that have been paid and/or are expected to be paid on consistent schedules are denoted as CD.
     * Special Cash dividends that have been paid that are infrequent or unusual,
     * and/or can not be expected to occur in the future are denoted as SC.
     */
    val dividendType: String? = null,

    /**
     * Order results based on the sort field.
     * Can be "asc" or "desc"
     */
    val order: String? = null,

    /**
     * Limit the number of results returned, default is 10 and max is 1000.
     */
    val limit: Int? = null,

    /**
     * Field used for ordering. See docs for valid fields
     */
    val sort: String? = null
)

@Serializable
data class DividendsResponse(
    val status: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    override val results: List<Dividend>? = null,
) : Paginatable<Dividend>

@Serializable
data class Dividend(
    @SerialName("cash_amount") val cashAmount: Double? = null,
    val currency: String? = null,
    @SerialName("declaration_date") val declarationDate: String? = null,
    @SerialName("dividend_type") val dividendType: String? = null,
    @SerialName("ex_dividend_date") val exDividendDate: String? = null,
    val frequency: Int? = null,
    @SerialName("pay_date") val payDate: String? = null,
    @SerialName("record_date") val recordDate: String? = null,
    val ticker: String? = null
)
