package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getSnapshotAllTickersBlocking] */
@SafeVarargs
suspend fun PolygonStocksClient.getSnapshotAllTickers(vararg opts: PolygonRestOption): SnapshotAllTickersDTO =
    polygonClient.fetchResult({
        path("v2", "snapshot", "locale", "us", "markets", "stocks", "tickers")
    }, *opts)

/** See [PolygonStocksClient.getSnapshotBlocking] */
@SafeVarargs
suspend fun PolygonStocksClient.getSnapshot(symbol: String, vararg opts: PolygonRestOption): SnapshotSingleTickerDTO =
    polygonClient.fetchResult({
        path("v2", "snapshot", "locale", "us", "markets", "stocks", "tickers", symbol)
    }, *opts)

/** See [PolygonStocksClient.getSnapshotGainersOrLosersBlocking] */
@SafeVarargs
suspend fun PolygonStocksClient.getSnapshotGainersOrLosers(
    direction: GainersOrLosersDirection,
    vararg opts: PolygonRestOption
): SnapshotGainersOrLosersDTO =
    polygonClient.fetchResult({
        path("v2", "snapshot", "locale", "us", "markets", "stocks", direction.queryParamValue)
    }, *opts)

enum class GainersOrLosersDirection(internal val queryParamValue: String) {
    GAINERS("gainers"),
    LOSERS("losers")
}

@Serializable
data class SnapshotAllTickersDTO(
    val status: String? = null,
    val tickers: List<SnapshotTickerDTO> = emptyList()
)

@Serializable
data class SnapshotSingleTickerDTO(
    val status: String? = null,
    val ticker: SnapshotTickerDTO = SnapshotTickerDTO()
)

@Serializable
data class SnapshotGainersOrLosersDTO(
    val status: String? = null,
    val tickers: List<SnapshotTickerDTO> = emptyList()
)

@Serializable
data class SnapshotTickerDTO(
    val ticker: String? = null,
    val todaysChange: Double? = null,
    val todaysChangePerc: Double? = null,
    val updated: Long? = null,
    val day: SnapshotAggregateDTO = SnapshotAggregateDTO(),
    val lastTrade: SnapshotLastTradeDTO = SnapshotLastTradeDTO(),
    val lastQuote: SnapshotLastQuoteDTO = SnapshotLastQuoteDTO(),
    val min: SnapshotAggregateDTO = SnapshotAggregateDTO(),
    val prevDay: SnapshotAggregateDTO = SnapshotAggregateDTO(),
    val fmv: Double? = null
)

@Serializable
data class SnapshotAggregateDTO(
    @SerialName("c") val close: Double? = null,
    @SerialName("h") val high: Double? = null,
    @SerialName("l") val low: Double? = null,
    @SerialName("o") val open: Double? = null,
    @SerialName("v") val volume: Double? = null
)

@Serializable
data class SnapshotLastQuoteDTO(
    @SerialName("P") val askPrice: Double? = null,
    @SerialName("p") val bidPrice: Double? = null,
    @SerialName("S") val askSize: Double? = null,
    @SerialName("s") val bidSize: Double? = null,
    @SerialName("t") val timestamp: Long? = null
)

@Serializable
data class SnapshotLastTradeDTO(
    @SerialName("p") val price: Double? = null,
    @SerialName("s") val size: Long? = null,
    @SerialName("x") val exchange: Long? = null,
    @SerialName("c1") val cond1: Long? = null,
    @SerialName("c2") val cond2: Long? = null,
    @SerialName("c3") val cond3: Long? = null,
    @SerialName("c4") val cond4: Long? = null,
    @SerialName("t") val timestamp: Long? = null
)
