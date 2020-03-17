package io.polygon.kotlin.sdk.rest.crypto

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.stocks.GainersOrLosersDirection
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Crypto" RESTful APIs
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonCryptoClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * List of crypto currency exchanges which are supported by Polygon.io
     *
     * API Doc: https://polygon.io/docs/#get_v1_meta_crypto-exchanges_anchor
     */
    fun getSupportedExchangesBlocking(): List<CryptoExchangeDTO> =
        runBlocking { getSupportedExchanges() }
    
    /** See [getSupportedExchangesBlocking] */
    fun getSupportedExchanges(callback: PolygonRestApiCallback<List<CryptoExchangeDTO>>) =
        coroutineToRestCallback(callback, { getSupportedExchanges() })

    /**
     * Get Last Trade Tick for a Currency Pair.
     *
     * API Doc: https://polygon.io/docs/#get_v1_last_crypto__from___to__anchor
     */
    fun getLastTradeBlocking(from: String, to: String): CryptoLastTradeDTO =
        runBlocking { getLastTrade(from, to) }
    
    /** See [getLastTradeBlocking] */
    fun getLastTrade(from: String, to: String, callback: PolygonRestApiCallback<CryptoLastTradeDTO>) =
        coroutineToRestCallback(callback, { getLastTrade(from, to) })
    
    /**
     * Get the open, close prices of a symbol on a certain day.
     *
     * API Doc: https://polygon.io/docs/#get_v1_open-close_crypto__from___to___date__anchor
     */
    fun getDailyOpenCloseBlocking(params: CryptoDailyOpenCloseParameters): CryptoDailyOpenCloseDTO =
        runBlocking { getDailyOpenClose(params) }
    
    /** See [getDailyOpenCloseBlocking] */
    fun getDailyOpenClose(params: CryptoDailyOpenCloseParameters, callback: PolygonRestApiCallback<CryptoDailyOpenCloseDTO>) =
        coroutineToRestCallback(callback, { getDailyOpenClose(params) })
    
    /**
     * Get historic trade ticks for a crypto pair.
     *
     * API Doc: https://polygon.io/docs/#get_v1_historic_crypto__from___to___date__anchor
     */
    fun getHistoricTradesBlocking(params: HistoricCryptoTradesParameters): HistoricCryptoTradesDTO =
        runBlocking { getHistoricTrades(params) }
    
    /** See [getHistoricTradesBlocking] */
    fun getHistoricTrades(params: HistoricCryptoTradesParameters, callback: PolygonRestApiCallback<HistoricCryptoTradesDTO>) =
        coroutineToRestCallback(callback, { getHistoricTrades(params) })

    /**
     * Snapshot allows you to see all tickers current minute aggregate, daily aggregate and last trade.
     * As well as previous days aggregate and calculated change for today.
     * The response size is large.
     *
     * API Doc: https://polygon.io/docs/#get_v2_snapshot_locale_global_markets_crypto_tickers_anchor
     */
    fun getSnapshotAllTickersBlocking(): CryptoMultiTickerSnapshotDTO =
        runBlocking { getSnapshotAllTickers() }
    
    /** See [getSnapshotAllTickersBlocking] */
    fun getSnapshotAllTickers(callback: PolygonRestApiCallback<CryptoMultiTickerSnapshotDTO>) =
        coroutineToRestCallback(callback, { getSnapshotAllTickers() })
    
    /**
     * See the current snapshot of a single ticker
     *
     * API Doc: https://polygon.io/docs/#get_v2_snapshot_locale_global_markets_crypto_tickers__ticker__anchor
     */
    fun getSnapshotSingleTickerBlocking(ticker: String): CryptoSingleTickerSnapshotDTO =
        runBlocking { getSnapshotSingleTicker(ticker) }
    
    /** See [getSnapshotSingleTickerBlocking] */
    fun getSnapshotSingleTicker(ticker: String, callback: PolygonRestApiCallback<CryptoSingleTickerSnapshotDTO>) =
        coroutineToRestCallback(callback, { getSnapshotSingleTicker(ticker) })

    /**
     * See the current snapshot of the top 20 gainers or losers of the day at the moment.
     *
     * API Doc: https://polygon.io/docs/#get_v2_snapshot_locale_global_markets_crypto__direction__anchor
     */
    fun getSnapshotGainersOrLosersBlocking(direction: GainersOrLosersDirection): CryptoMultiTickerSnapshotDTO =
        runBlocking { getSnapshotGainersOrLosers(direction) }
    
    /** See [getSnapshotGainersOrLosersBlocking] */
    fun getSnapshotGainersOrLosers(direction: GainersOrLosersDirection, callback: PolygonRestApiCallback<CryptoMultiTickerSnapshotDTO>) =
        coroutineToRestCallback(callback, { getSnapshotGainersOrLosers(direction) })
    
}