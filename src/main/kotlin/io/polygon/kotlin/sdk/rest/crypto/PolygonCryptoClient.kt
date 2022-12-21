package io.polygon.kotlin.sdk.rest.crypto

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceClient
import io.polygon.kotlin.sdk.rest.stocks.GainersOrLosersDirection
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's crypto pricing data RESTful APIs.
 * For common pricing APIs shared across asset classes, see [PolygonRestClient].
 * For reference data, see [PolygonReferenceClient].
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonCryptoClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * List of cryptocurrency exchanges which are supported by Polygon.io
     *
     * API Doc: https://polygon.io/docs/#get_v1_meta_crypto-exchanges_anchor
     */
    @SafeVarargs
    @Deprecated("use getExchanges in PolygonReferenceClient instead")
    fun getSupportedExchangesBlocking(vararg opts: PolygonRestOption): List<CryptoExchangeDTO> =
        runBlocking { getSupportedExchanges(*opts) }

    /** See [getSupportedExchangesBlocking] */
    @SafeVarargs
    @Deprecated("use getExchanges in PolygonReferenceClient instead")
    fun getSupportedExchanges(callback: PolygonRestApiCallback<List<CryptoExchangeDTO>>) =
        coroutineToRestCallback(callback, { getSupportedExchanges() })

    /**
     * Get Last Trade Tick for a Currency Pair.
     *
     * API Doc: https://polygon.io/docs/crypto/get_v1_last_crypto__from___to
     */
    @SafeVarargs
    fun getLastTradeBlocking(from: String, to: String, vararg opts: PolygonRestOption): CryptoLastTradeDTO =
        runBlocking { getLastTrade(from, to, *opts) }

    /** See [getLastTradeBlocking] */
    @SafeVarargs
    fun getLastTrade(
        from: String, to: String, callback: PolygonRestApiCallback<CryptoLastTradeDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getLastTrade(from, to, *opts) })

    /**
     * Get the daily open, high, low, and close (OHLC) for the entire cryptocurrency markets.
     *
     * API Doc: https://polygon.io/docs/crypto/get_v2_aggs_grouped_locale_global_market_crypto__date
     */
    @SafeVarargs
    fun getDailyOpenCloseBlocking(params: CryptoDailyOpenCloseParameters, vararg opts: PolygonRestOption):
            CryptoDailyOpenCloseDTO =
        runBlocking { getDailyOpenClose(params, *opts) }

    /** See [getDailyOpenCloseBlocking] */
    @SafeVarargs
    fun getDailyOpenClose(
        params: CryptoDailyOpenCloseParameters,
        callback: PolygonRestApiCallback<CryptoDailyOpenCloseDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getDailyOpenClose(params, *opts) })

    /**
     * Get historic trade ticks for a crypto pair.
     *
     * API Doc: https://polygon.io/docs/crypto/deprecated/get_v1_historic_crypto__from___to___date
     */
    @SafeVarargs
    @Deprecated("use listTrades or getTradesBlocking in PolygonRestClient", ReplaceWith("listTrades(params, *opts)"))
    fun getHistoricTradesBlocking(
        params: HistoricCryptoTradesParameters,
        vararg opts: PolygonRestOption
    ): HistoricCryptoTradesDTO =
        runBlocking { getHistoricTrades(params, *opts) }

    /** See [getHistoricTradesBlocking] */
    @SafeVarargs
    @Deprecated("use listTrades or getTrades in PolygonRestClient", ReplaceWith("listTrades(params, *opts)"))
    fun getHistoricTrades(
        params: HistoricCryptoTradesParameters,
        callback: PolygonRestApiCallback<HistoricCryptoTradesDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getHistoricTrades(params, *opts) })

    /**
     * Snapshot allows you to see all tickers current minute aggregate, daily aggregate and last trade.
     * As well as previous days aggregate and calculated change for today.
     * The response size is large.
     *
     * API Doc: https://polygon.io/docs/crypto/get_v2_snapshot_locale_global_markets_crypto_tickers
     */
    @SafeVarargs
    fun getSnapshotAllTickersBlocking(vararg opts: PolygonRestOption): CryptoMultiTickerSnapshotDTO =
        runBlocking { getSnapshotAllTickers(*opts) }

    /** See [getSnapshotAllTickersBlocking] */
    @SafeVarargs
    fun getSnapshotAllTickers(
        callback: PolygonRestApiCallback<CryptoMultiTickerSnapshotDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getSnapshotAllTickers(*opts) })

    /**
     * See the current snapshot of a single ticker
     *
     * API Doc: https://polygon.io/docs/crypto/get_v2_snapshot_locale_global_markets_crypto_tickers__ticker
     */
    @SafeVarargs
    fun getSnapshotSingleTickerBlocking(ticker: String, vararg opts: PolygonRestOption): CryptoSingleTickerSnapshotDTO =
        runBlocking { getSnapshotSingleTicker(ticker, *opts) }

    /** See [getSnapshotSingleTickerBlocking] */
    @SafeVarargs
    fun getSnapshotSingleTicker(
        ticker: String,
        callback: PolygonRestApiCallback<CryptoSingleTickerSnapshotDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getSnapshotSingleTicker(ticker, *opts) })

    /**
     * See the current snapshot of the top 20 gainers or losers of the day at the moment.
     *
     * API Doc: https://polygon.io/docs/crypto/get_v2_snapshot_locale_global_markets_crypto__direction
     */
    @SafeVarargs
    fun getSnapshotGainersOrLosersBlocking(
        direction: GainersOrLosersDirection,
        vararg opts: PolygonRestOption
    ): CryptoMultiTickerSnapshotDTO =
        runBlocking { getSnapshotGainersOrLosers(direction, *opts) }

    /** See [getSnapshotGainersOrLosersBlocking] */
    @SafeVarargs
    fun getSnapshotGainersOrLosers(
        direction: GainersOrLosersDirection,
        callback: PolygonRestApiCallback<CryptoMultiTickerSnapshotDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getSnapshotGainersOrLosers(direction, *opts) })

    /**
     * See the current level 2 book of a single ticker. This is the combined book from all the exchanges.
     *
     * API Doc: https://polygon.io/docs/crypto/get_v2_snapshot_locale_global_markets_crypto_tickers__ticker__book
     */
    @SafeVarargs
    fun getL2SnapshotSingleTickerBlocking(
        ticker: String,
        vararg opts: PolygonRestOption
    ): CryptoTickerL2SnapshotResponseDTO =
        runBlocking { getL2SnapshotSingleTicker(ticker, *opts) }

    /** See [getL2SnapshotSingleTickerBlocking] */
    @SafeVarargs
    fun getL2SnapshotSingleTicker(
        ticker: String,
        callback: PolygonRestApiCallback<CryptoTickerL2SnapshotResponseDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getL2SnapshotSingleTicker(ticker, *opts) })
}