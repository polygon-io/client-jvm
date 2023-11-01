package io.polygon.kotlin.sdk.rest.options

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.ComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.Paginatable
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import io.polygon.kotlin.sdk.rest.applyComparisonQueryFilterParameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonOptionsClient.getSnapshotBlocking] */
@SafeVarargs
suspend fun PolygonOptionsClient.getSnapshot(
    underlyingAsset: String,
    contract: String,
    vararg opts: PolygonRestOption
): SnapshotResponse =
    polygonClient.fetchResult({ path("v3", "snapshot", "options", underlyingAsset, contract) }, *opts)

@SafeVarargs
suspend fun PolygonOptionsClient.getSnapshotChain(
    underlyingAsset: String,
    params: SnapshotChainParameters,
    vararg opts: PolygonRestOption
): SnapshotChainResponse =
    polygonClient.fetchResult({
        path("v3", "snapshot", "options", underlyingAsset)

        applyComparisonQueryFilterParameters("strike_price", params.strikePrice)
        applyComparisonQueryFilterParameters("expiration_date", params.expirationDate)
        params.contractType?.let { parameters["contract_type"] = it }
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }

    }, *opts)

@Builder
data class SnapshotChainParameters(

    /**
     * Query by strike price of the contract.
     */
    val strikePrice: ComparisonQueryFilterParameters<Double>? = null,

    /**
     * Query by expiration date of the contract with date format YYYY-MM-DD.
     */
    val expirationDate: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by the type of contract. 'call' or 'put'.
     */
    val contractType: String? = null,

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
data class SnapshotResponse(
    val status: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    val results: Snapshot? = null,
)

@Serializable
data class SnapshotChainResponse(
    val status: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    override val results: List<Snapshot> = emptyList()
) : Paginatable<Snapshot>

@Serializable
data class Snapshot(
    @SerialName("break_even_price") val breakEvenPrice: Double? = null,
    @SerialName("day") val day: SnapshotDayAggregate? = null,
    @SerialName("details") val details: SnapshotContractDetails? = null,
    @SerialName("greeks") val greeks: SnapshotGreeks? = null,
    @SerialName("implied_volatility") val impliedVolatility: Double? = null,
    @SerialName("last_quote") val lastQuote: SnapshotLastQuote? = null,
    @SerialName("open_interest") val openInterest: Double? = null,
    @SerialName("underlying_asset") val underlyingAsset: SnapshotUnderlyingAssetInfo? = null,
    @SerialName("fmv") val fairMarketValue: Double? = null,
)

@Serializable
data class SnapshotDayAggregate(
    @SerialName("change") val change: Double? = null,
    @SerialName("change_percent") val changePercent: Double? = null,
    @SerialName("open") val open: Double? = null,
    @SerialName("high") val high: Double? = null,
    @SerialName("low") val low: Double? = null,
    @SerialName("close") val close: Double? = null,
    @SerialName("last_updated") val lastUpdated: Long? = null,
    @SerialName("previous_close") val previousClose: Double? = null,
    @SerialName("volume") val volume: Double? = null,
    @SerialName("vwap") val vwap: Double? = null,
)

@Serializable
data class SnapshotContractDetails(
    @SerialName("contract_type") val contractType: String? = null,
    @SerialName("exercise_style") val exerciseStyle: String? = null,
    @SerialName("expiration_date") val expirationDate: String? = null,
    @SerialName("shares_per_contract") val sharesPerContract: Double? = null,
    @SerialName("ticker") val ticker: String? = null,
)

@Serializable
data class SnapshotGreeks(
    @SerialName("delta") val delta: Double? = null,
    @SerialName("gamma") val gamma: Double? = null,
    @SerialName("theta") val theta: Double? = null,
    @SerialName("vega") val vega: Double? = null,
)

@Serializable
data class SnapshotLastQuote(
    @SerialName("ask") val ask: Double? = null,
    @SerialName("ask_size") val askSize: Double? = null,
    @SerialName("bid") val bid: Double? = null,
    @SerialName("bid_size") val bidSize: Double? = null,
    @SerialName("last_updated") val lastUpdated: Long? = null,
    @SerialName("midpoint") val midpoint: Double? = null,
    @SerialName("timeframe") val timeframe: String? = null,
)

@Serializable
data class SnapshotUnderlyingAssetInfo(
    @SerialName("change_to_break_even") val changeToBreakEven: Double? = null,
    @SerialName("last_updated") val lastUpdated: Long? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("timeframe") val timeframe: String? = null,
)
