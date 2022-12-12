package io.polygon.kotlin.sdk.rest.forex

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
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
     * API Doc: https://polygon.io/docs/forex/deprecated/get_v1_historic_forex__from___to___date
     */
    fun getHistoricTicksBlocking(params: HistoricTicksParameters, vararg opts: PolygonRestOption): HistoricTicksDTO =
        runBlocking { getHistoricTicks(params, *opts) }

    /** See [getHistoricTicksBlocking] */
    fun getHistoricTicks(
        params: HistoricTicksParameters,
        callback: PolygonRestApiCallback<HistoricTicksDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getHistoricTicks(params, *opts) })

    /**
     * Convert currencies using the latest market conversion rates.
     * Note than you can convert in both directions. For example USD->CAD or CAD->USD.
     *
     * API Doc: https://polygon.io/docs/forex/get_v1_conversion__from___to
     */
    fun getRealTimeConversionBlocking(
        params: RealTimeConversionParameters,
        vararg opts: PolygonRestOption
    ): RealTimeConversionDTO = runBlocking { getRealTimeConversion(params, *opts) }

    /** See [getRealTimeConversionBlocking] */
    fun getRealTimeConversion(
        params: RealTimeConversionParameters,
        callback: PolygonRestApiCallback<RealTimeConversionDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getRealTimeConversion(params, *opts) })

    /**
     * Get Last Quote Tick for a Currency Pair.
     *
     * API Doc: https://polygon.io/docs/forex/get_v1_last_quote_currencies__from___to
     */
    fun getLastQuoteBlocking(
        fromCurrency: String,
        toCurrency: String,
        vararg opts: PolygonRestOption
    ): LastQuoteForexDTO = runBlocking { getLastQuote(fromCurrency, toCurrency, *opts) }

    /** See [getLastQuoteBlocking] */
    fun getLastQuote(
        fromCurrency: String,
        toCurrency: String,
        callback: PolygonRestApiCallback<LastQuoteForexDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getLastQuote(fromCurrency, toCurrency, *opts) })

    /**
     * Snapshot allows you to see all tickers current minute aggregate, daily aggregate and last trade.
     * As well as previous days aggregate and calculated change for today.
     * The response size is large.
     *
     * API Doc: https://polygon.io/docs/forex/get_v2_snapshot_locale_global_markets_forex_tickers
     */
    fun getSnapshotAllTickersBlocking(vararg opts: PolygonRestOption): SnapshotForexTickersDTO =
        runBlocking { getSnapshotAllTickers(*opts) }

    /** See [getSnapshotAllTickersBlocking] */
    fun getSnapshotAllTickers(
        callback: PolygonRestApiCallback<SnapshotForexTickersDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getSnapshotAllTickers(*opts) })

    /**
     * See the current snapshot of the top 20 gainers or losers of the day at the moment.
     *
     * API Doc: https://polygon.io/docs/forex/get_v2_snapshot_locale_global_markets_forex__direction
     */
    fun getSnapshotGainersOrLosersBlocking(
        direction: GainersOrLosersDirection,
        vararg opts: PolygonRestOption
    ): SnapshotForexTickersDTO = runBlocking { getSnapshotGainersOrLosers(direction, *opts) }

    /** See [getSnapshotGainersOrLosersBlocking] */
    fun getSnapshotGainersOrLosers(
        direction: GainersOrLosersDirection,
        callback: PolygonRestApiCallback<SnapshotForexTickersDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, { getSnapshotGainersOrLosers(direction, *opts) })

}