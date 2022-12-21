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
suspend fun PolygonReferenceClient.getOptionsContractDetails(contract: String, params: OptionsContractDetailsParameters, vararg opts: PolygonRestOption): OptionsContractDetailsResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "options", "contracts", contract)

        params.asOf?.let { parameters["as_of"] = it }
    }, *opts)

@SafeVarargs
suspend fun PolygonReferenceClient.getOptionsContracts(params: OptionsContractsParameters, vararg opts: PolygonRestOption): OptionsContractsResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "options", "contracts")

        applyComparisonQueryFilterParameters("underlying_ticker", params.underlyingTicker)
        params.contractType?.let { parameters["contract_type"] = it }
        applyComparisonQueryFilterParameters("strike_price", params.strikePrice)
        params.expired?.let { parameters["expired"] = it.toString() }
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }
    }, *opts)

@Builder
data class OptionsContractsParameters(
    /**
     * Query for contracts relating to an underlying stock ticker.
     */
    val underlyingTicker: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by the type of contract ('call' or 'put')
     */
    val contractType: String? = null,

    /**
     * Query by strike price of a contract
     */
    val strikePrice: ComparisonQueryFilterParameters<Double>? = null,

    /**
     * Query for expired contracts. Default is false.
     */
    val expired: Boolean? = null,

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
     * Field used for ordering. See API docs for valid fields
     */
    val sort: String? = null
)

@Builder
data class OptionsContractDetailsParameters(
    /**
     * Specify a point in time for the contract as of this date with format YYYY-MM-DD. Defaults to today's date.
     */
    val asOf: String? = null,
)

@Serializable
data class OptionsContractsResponse(
    val status: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    override val results: List<OptionsContract>? = null,
) : Paginatable<OptionsContract>

@Serializable
data class OptionsContractDetailsResponse(
    val status: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    val results: OptionsContract? = null,
)

@Serializable
data class OptionsContract(
    @SerialName("additional_underlyings") val additionalUnderlyings: List<OptionsContractAdditionalUnderlying>? = null,
    @SerialName("cfi") val cfi: String? = null,
    @SerialName("contract_type") val contractType: String? = null,
    @SerialName("correction") val correction: Int? = null,
    @SerialName("exercise_style") val exerciseStyle: String? = null,
    @SerialName("expiration_date") val expirationDate: String? = null,
    @SerialName("primary_exchange") val primaryExchange: String? = null,
    @SerialName("shares_per_contract") val sharesPerContract: Double? = null,
    @SerialName("strike_price") val strikePrice: Double? = null,
    @SerialName("ticker") val ticker: String? = null,
    @SerialName("underlying_ticker") val underlyingTicker: String? = null,
)

@Serializable
data class OptionsContractAdditionalUnderlying(
    @SerialName("amount") val amount: Double? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("underlying") val underlying: String? = null,
)
