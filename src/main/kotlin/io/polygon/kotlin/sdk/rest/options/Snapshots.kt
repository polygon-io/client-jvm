package io.polygon.kotlin.sdk.rest.options

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonOptionsClient.getSnapshotBlocking] */
suspend fun PolygonOptionsClient.getSnapshot(
    underlyingAsset: String,
    contract: String,
    vararg opts: PolygonRestOption
): SnapshotResponse =
    polygonClient.fetchResult({ path("v3", "snapshot", "options", underlyingAsset, contract) }, *opts)

@Serializable
data class SnapshotResponse(
    val status: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    val results: Snapshot? = null,
)

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
