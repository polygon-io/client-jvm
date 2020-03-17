package io.polygon.kotlin.sdk.rest.crypto

import io.polygon.kotlin.sdk.rest.stocks.GainersOrLosersDirection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonCryptoClient.getSnapshotAllTickersBlocking] */
suspend fun PolygonCryptoClient.getSnapshotAllTickers(): CryptoMultiTickerSnapshotDTO =
    polygonClient.fetchResult { path("v2", "snapshot", "locale", "global", "markets", "crypto", "tickers") }

/** See [PolygonCryptoClient.getSnapshotSingleTickerBlocking] */
suspend fun PolygonCryptoClient.getSnapshotSingleTicker(ticker: String): CryptoSingleTickerSnapshotDTO =
    polygonClient.fetchResult { path("v2", "snapshot", "locale", "global", "markets", "crypto", "tickers", ticker) }

/** See [PolygonCryptoClient.getSnapshotGainersOrLosersBlocking] */
suspend fun PolygonCryptoClient.getSnapshotGainersOrLosers(direction: GainersOrLosersDirection): CryptoMultiTickerSnapshotDTO =
    polygonClient.fetchResult { path("v2", "snapshot", "locale", "global", "markets", "crypto", direction.queryParamValue) }

@Serializable
data class CryptoMultiTickerSnapshotDTO(
    val status: String? = null,
    val tickers: List<CryptoTickerSnapshotDTO> = emptyList()
)

@Serializable
data class CryptoSingleTickerSnapshotDTO(
    val status: String? = null,
    val ticker: CryptoTickerSnapshotDTO = CryptoTickerSnapshotDTO()
)

@Serializable
data class CryptoTickerSnapshotDTO(
    val ticker: String? = null,
    val todaysChange: Double? = null,
    val todaysChangePerc: Double? = null,
    val updated: Long? = null,
    val day: CryptoSnapshotAggregateDTO = CryptoSnapshotAggregateDTO(),
    val lastTrade: CryptoTickDTO = CryptoTickDTO(),
    val min: CryptoSnapshotAggregateDTO = CryptoSnapshotAggregateDTO(),
    val prevDay: CryptoSnapshotAggregateDTO = CryptoSnapshotAggregateDTO()
)

@Serializable
data class CryptoSnapshotAggregateDTO(
    @SerialName("c") val close: Double? = null,
    @SerialName("o") val open: Double? = null,
    @SerialName("l") val low: Double? = null,
    @SerialName("h") val high: Double? = null,
    @SerialName("v") val volume: Double? = null
)