package io.polygon.kotlin.sdk.rest.forex

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.stocks.GainersOrLosersDirection
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Forex / Currencies" RESTful APIs
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonForexClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * Get historic ticks for a currency pair.
     * Example for USD/JPY the from would be USD and to would be JPY.
     * The date formatted like 2017-6-22
     *
     * API Doc: https://polygon.io/docs/#get_v1_historic_forex__from___to___date__anchor
     */
    fun getHistoricTicksBlocking(params: HistoricTicksParameters): HistoricTicksDTO =
        runBlocking { getHistoricTicks(params) }
    
    /** See [getHistoricTicksBlocking] */
    fun getHistoricTicks(params: HistoricTicksParameters, callback: PolygonRestApiCallback<HistoricTicksDTO>) =
        coroutineToRestCallback(callback, { getHistoricTicks(params) })
    
    /**
     * Convert currencies using the latest market conversion rates.
     * Note than you can convert in both directions. For example USD->CAD or CAD->USD.
     *
     * API Doc: https://polygon.io/docs/#get_v1_conversion__from___to__anchor
     */
    fun getRealTimeConversionBlocking(params: RealTimeConversionParameters): RealTimeConversionDTO =
        runBlocking { getRealTimeConversion(params) }
    
    /** See [getRealTimeConversionBlocking] */
    fun getRealTimeConversion(params: RealTimeConversionParameters, callback: PolygonRestApiCallback<RealTimeConversionDTO>) =
        coroutineToRestCallback(callback, { getRealTimeConversion(params) })
    
    /**
     * Get Last Quote Tick for a Currency Pair.
     *
     * API Doc: https://polygon.io/docs/#get_v1_last_quote_currencies__from___to__anchor
     */
    fun getLastQuoteBlocking(fromCurrency: String, toCurrency: String): LastQuoteForexDTO =
        runBlocking { getLastQuote(fromCurrency, toCurrency) }
    
    /** See [getLastQuoteBlocking] */
    fun getLastQuote(fromCurrency: String, toCurrency: String, callback: PolygonRestApiCallback<LastQuoteForexDTO>) =
        coroutineToRestCallback(callback, { getLastQuote(fromCurrency, toCurrency) })
    
    /**
     * Snapshot allows you to see all tickers current minute aggregate, daily aggregate and last trade.
     * As well as previous days aggregate and calculated change for today.
     * The response size is large.
     *
     * API Doc: https://polygon.io/docs/#get_v2_snapshot_locale_global_markets_forex_tickers_anchor
     */
    fun getSnapshotAllTickersBlocking(): SnapshotForexTickersDTO =
        runBlocking { getSnapshotAllTickers() }
    
    /** See [getSnapshotAllTickersBlocking] */
    fun getSnapshotAllTickers(callback: PolygonRestApiCallback<SnapshotForexTickersDTO>) =
        coroutineToRestCallback(callback, { getSnapshotAllTickers() })

    /**
     * See the current snapshot of the top 20 gainers or losers of the day at the moment.
     *
     * API Doc: https://polygon.io/docs/#get_v2_snapshot_locale_global_markets_forex__direction__anchor
     */
    fun getSnapshotGainersOrLosersBlocking(direction: GainersOrLosersDirection): SnapshotForexTickersDTO =
        runBlocking { getSnapshotGainersOrLosers(direction) }
    
    /** See [getSnapshotGainersOrLosersBlocking] */
    fun getSnapshotGainersOrLosers(direction: GainersOrLosersDirection, callback: PolygonRestApiCallback<SnapshotForexTickersDTO>) =
        coroutineToRestCallback(callback, { getSnapshotGainersOrLosers(direction) })

}