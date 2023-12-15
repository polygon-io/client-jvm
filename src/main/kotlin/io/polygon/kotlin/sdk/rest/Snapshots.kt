package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.options.SnapshotContractDetails
import io.polygon.kotlin.sdk.rest.options.SnapshotGreeks
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SafeVarargs
suspend fun PolygonRestClient.getSnapshots(
    params: SnapshotsParameters,
    vararg opts: PolygonRestOption
): SnapshotsResponse =
    fetchResult({
        path(
            "v3",
            "snapshot",
        )
        params.tickers?.let{ parameters["ticker.any_of"] = it.joinToString(",") }
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }
    }, *opts)

@Builder
data class SnapshotsParameters(
    /**
     * List of tickers of any asset class.
     * Maximum of 250.
     * If no tickers are passed then all results will be returned in a paginated manner.
     */
    val tickers: List<String>? = null,

    /**
     * Order results based on the sort field.
     * Can be "asc" or "desc"
     */
    val order: String? = null,

    /**
     * Limit the number of results returned per page
     * Default is 10 and max is 250.
     */
    val limit: Int? = null,

    /**
     * Field used for ordering. See docs for valid fields
     */
    val sort: String? = null
)

@Serializable
data class SnapshotsResponse(
    val status: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    @SerialName("request_id") val requestID: String? = null,
    override val results: List<Snapshot>? = emptyList(),
) : Paginatable<Snapshot>

@Serializable
data class Snapshot (
    // Common: fields that apply to most/all of the asset types.
    val error: String? = null,
    val message: String? = null,
    val market_status: String? = null,
    val name: String? = null,
    val type: String? = null, // Type of the asset: stocks, options, fx, crypto, indices.
    @SerialName("session") val session: SnapshotSession? = null,

    // fmv is only available on Business plans, see docs for details.
    @SerialName("fmv") val fmv: Double? = null,

    // Quote is only returned if your current plan includes quotes.
    @SerialName("last_quote") val lastQuote: Quote? = null,

    // Trade is only returned if your current plan includes trades.
    @SerialName("last_trade") val lastTrade: SnapshotsTrade? = null,

    // Options
    @SerialName("break_even_price") val breakEvenPrice: Double? = null,
    @SerialName("details") val optionDetails: SnapshotContractDetails? = null,
    @SerialName("greeks") val optionGreeks: SnapshotGreeks? = null,
    @SerialName("implied_volatility") val impliedVolatility: Double? = null,
    @SerialName("open_interest") val openInterest: Double? = null,
    @SerialName("underlying_asset") val underlyingAsset: SnapshotUnderlyingAsset? = null,

    // Indices
    @SerialName("value") val value: Double? = null,
)

@Serializable
data class SnapshotSession(
    @SerialName("change") val change: Double? = null,
    @SerialName("change_percent") val changePercent: Double? = null,
    @SerialName("close") val close: Double? = null,
    @SerialName("early_trading_change") val earlyTradingChange: Double? = null,
    @SerialName("early_trading_change_percent") val earlyTradingChangePercent: Double? = null,
    @SerialName("high") val high: Double? = null,
    @SerialName("late_trading_change") val lateTradingChange: Double? = null,
    @SerialName("late_trading_change_percent") val lateTradingChangePercent: Double? = null,
    @SerialName("low") val low: Double? = null,
    @SerialName("open") val open: Double? = null,
    @SerialName("previous_close") val previousClose: Double? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("volume") val volume: Double? = null,
)

@Serializable
data class SnapshotUnderlyingAsset(
    @SerialName("change_to_break_even") val changeToBreakEven: Double? = null,
    @SerialName("last_updated") val lastUpdated: Long? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("ticker") val ticker: String? = null,
    @SerialName("timeframe") val timeframe: String? = null,

    // Value of underlying index, if parent is an index option.
    @SerialName("value") val value: Double? = null,
)

@Serializable
data class SnapshotsQuote(
    @SerialName("ask_exchange") val askExchange: Int? = null,
    @SerialName("ask") val ask: Double? = null,
    @SerialName("ask_size") val askSize: Double? = null,
    @SerialName("bid_exchange") val bidExchange: Int? = null,
    @SerialName("bid") val bid: Double? = null,
    @SerialName("bid_size") val bidSize: Double? = null,
    @SerialName("participant_timestamp") val participantTimestamp: Long? = null,
    @SerialName("last_updated") val lastUpdated: Long? = null,
    @SerialName("midpoint") val midpoint: Double? = null,
    @SerialName("timeframe") val timeframe: String? = null,
)

@Serializable
data class SnapshotsTrade(
    val conditions: List<Int>? = null,
    val exchange: Int? = null,
    @SerialName("id") val tradeID: String? = null,
    @SerialName("last_updated") val lastUpdated: Long? = null,
    @SerialName("participant_timestamp") val participantTimestamp: Long? = null,
    val price: Double? = null,
    @SerialName("sip_timestamp") val sipTimestamp: Long? = null,
    val size: Double? = null,
    @SerialName("timeframe") val timeframe: String? = null,
)
