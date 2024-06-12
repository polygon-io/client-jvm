package io.polygon.kotlin.sdk.rest.experimental

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.ComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.applyComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.Paginatable
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonExperimentalClient.getFinancialsBlocking] */
@SafeVarargs
@ExperimentalAPI
suspend fun PolygonExperimentalClient.getFinancials(params: FinancialsParameters, vararg opts: PolygonRestOption): FinancialsResponse =
    polygonClient.fetchResult({
        path("vX", "reference", "financials")

        params.ticker?.let { parameters["ticker"] = it }
        params.cik?.let { parameters["cik"] = it }
        params.companyName?.let { parameters["company_name"] = it }
        params.companyNameSearch?.let { parameters["company_name.search"] = it }
        params.sic?.let { parameters["sic"] = it }
        applyComparisonQueryFilterParameters("filing_date", params.filingDate)
        applyComparisonQueryFilterParameters("period_of_report_date", params.periodOfReportDate)
        params.timeframe?.let { parameters["timeframe"] = it }
        params.includeSources?.let { parameters["include_sources"] = it.toString() }
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }

    }, *opts)

@Builder
@ExperimentalAPI
data class FinancialsParameters(

    /**
     * Query by company ticker.
     */
    val ticker: String? = null,

    /**
     * Query by SEC central index key (CIK) number.
     */
    val cik: String? = null,

    /**
     * Query by company name.
     */
    val companyName: String? = null,

    /**
     * Search via partial-match text search for company name
     */
    val companyNameSearch: String? = null,

    /**
     * Query by standard industrial classification (SIC)
     */
    val sic: String? = null,

    /**
     * Query by the date when the filing with financials data was filed in YYYY-MM-DD format.
     * Best used when querying over date ranges to find financials based on filings that happen in a time period.
     *
     * Examples:
     * To get financials based on filings that have happened after January 1, 2009 use the query param filing_date.gte=2009-01-01
     * To get financials based on filings that happened in the year 2009 use the query params filing_date.gte=2009-01-01&filing_date.lt=2010-01-01
     */
    val filingDate: ComparisonQueryFilterParameters<String>? = null,

    /**
     * The period of report for the filing with financials data in YYYY-MM-DD format.
     */
    val periodOfReportDate: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by timeframe.
     *
     * Annual financials originate from 10-K filings, and quarterly financials originate from 10-Q filings.
     * Note: Most companies do not file quarterly reports for Q4 and instead include those financials in their annual report,
     * so some companies my not return quarterly financials for Q4.
     *
     * Can be 'annual' or 'quarterly'
     */
    val timeframe: String? = null,

    /**
     * Whether or not to include the xpath and formula attributes for each financial data point.
     * See the xpath and formula response attributes in the API docs for more info.
     *
     * False by default.
     */
    val includeSources: Boolean? = null,

    /**
     * Order results based on the sort field.
     * Can be "asc" or "desc"
     */
    val order: String? = null,

    /**
     * Limit the number of results returned, default is 10 and max is 100.
     */
    val limit: Int? = null,

    /**
     * Field used for ordering. See API docs for valid fields
     */
    val sort: String? = null
)

@Serializable
@ExperimentalAPI
data class FinancialsResponse(
    val status: String? = null,
    @SerialName("request_id") val requestID: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    override val results: List<Financials>? = null
) : Paginatable<Financials>

@Serializable
@ExperimentalAPI
data class Financials(
    val cik: String = "",
    val financials: FinancialForms? = null,
    @SerialName("company_name") val companyName: String = "",
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("end_date") val endDate: String? = null,
    @SerialName("filing_date") val filingDate: String? = null,
    @SerialName("fiscal_period") val fiscalPeriod: String? = null,
    @SerialName("fiscal_year") val fiscalYear: String? = null,
    @SerialName("source_filing_file_url") val sourceFilingFileURL: String = "",
    @SerialName("source_filing_url") val sourceFilingURL: String = "",
)

@Serializable
@ExperimentalAPI
data class FinancialForms(
    @SerialName("balance_sheet") val balanceSheet: FinancialForm? = null,
    @SerialName("cash_flow_statement") val cashFlowStatement: FinancialForm? = null,
    @SerialName("compreshensive_income") val comprehensiveIncome: FinancialForm? = null,
    @SerialName("income_statement") val incomeStatement: FinancialForm? = null,
)

@ExperimentalAPI
typealias FinancialForm = Map<String, FinancialsDataPoint>

@Serializable
@ExperimentalAPI
data class FinancialsDataPoint(
    val formula: String? = null,
    val label: String = "",
    val order: Int = 0,
    val unit: String? = null,
    val value: Double = 0.0,
    val xpath: String? = null,
)
