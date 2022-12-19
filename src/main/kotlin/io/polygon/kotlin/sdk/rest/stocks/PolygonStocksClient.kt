package io.polygon.kotlin.sdk.rest.stocks

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Stocks / Equities" RESTful APIs
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonStocksClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * List of stock exchanges which are supported by Polygon.io
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_meta_exchanges
     */
    fun getSupportedExchangesBlocking(vararg opts: PolygonRestOption): List<ExchangeDTO> =
        runBlocking { getSupportedExchanges(*opts) }

    /** See [getSupportedExchangesBlocking] */
    fun getSupportedExchanges(callback: PolygonRestApiCallback<List<ExchangeDTO>>, vararg opts: PolygonRestOption) =
        coroutineToRestCallback(callback, { getSupportedExchanges(*opts) })

    /**
     * Get historic trades for a ticker.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v2_ticks_stocks_trades_ticker_date
     */
    @Deprecated("use listTrades or getTradesBlocking in PolygonRestClient", ReplaceWith("listTrades(params, *opts)"))
    fun getHistoricTradesBlocking(params: HistoricTradesParameters, vararg opts: PolygonRestOption): HistoricTradesDTO =
        runBlocking { getHistoricTrades(params, *opts) }

    /** See [getHistoricTradesBlocking] */
    @Deprecated("use listTrades or getTrades in PolygonRestClient", ReplaceWith("listTrades(params, *opts)"))
    fun getHistoricTrades(
        params: HistoricTradesParameters,
        callback: PolygonRestApiCallback<HistoricTradesDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getHistoricTrades(params, *opts) })

    /**
     * Get historic NBBO quotes for a ticker.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v2_ticks_stocks_nbbo_ticker_date
     */
    @Deprecated("superseded by listQuotes/getQuotesBlocking in PolygonRestClient",ReplaceWith("getQuotesBlocking(params, *ops)"))
    fun getHistoricQuotesBlocking(params: HistoricQuotesParameters, vararg opts: PolygonRestOption): HistoricQuotesDTO =
        runBlocking { getHistoricQuotes(params, *opts) }

    /** See [getHistoricQuotesBlocking] */
    @Deprecated("superseded by listQuotes/getQuotes in PolygonRestClient",ReplaceWith("getQuotes(params, *ops)"))
    fun getHistoricQuotes(
        params: HistoricQuotesParameters,
        callback: PolygonRestApiCallback<HistoricQuotesDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getHistoricQuotes(params, *opts) })

    /**
     * Get the last trade for a given stock.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_last_stocks_symbol
     */
    fun getLastTradeBlocking(symbol: String, vararg opts: PolygonRestOption): LastTradeResultDTO =
        runBlocking { getLastTrade(symbol, *opts) }

    /** See [getLastTradeBlocking] */
    fun getLastTrade(
        symbol: String,
        callback: PolygonRestApiCallback<LastTradeResultDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getLastTrade(symbol, *opts) })

    /**
     * Get the last quote tick for a given stock.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_last_quote_stocks_symbol
     */
    fun getLastQuoteBlocking(symbol: String, vararg opts: PolygonRestOption): LastQuoteResultDTO =
        runBlocking { getLastQuote(symbol, *opts) }

    /** See [getLastQuoteBlocking] */
    fun getLastQuote(
        symbol: String,
        callback: PolygonRestApiCallback<LastQuoteResultDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getLastQuote(symbol, *opts) })

    /**
     * Get the open, close and afterhours prices of a symbol on a certain date.
     * @param date The date to get the open, close and after hours prices for (YYYY-MM-DD)
     *
     * API Doc: https://polygon.io/docs/stocks/get_v1_open-close__stocksticker___date
     */
    fun getDailyOpenCloseBlocking(
        symbol: String,
        date: String,
        unadjusted: Boolean,
        vararg opts: PolygonRestOption
    ): DailyOpenCloseDTO =
        runBlocking { getDailyOpenClose(symbol, date, unadjusted, *opts) }

    /** See [getDailyOpenCloseBlocking] */
    fun getDailyOpenClose(
        symbol: String,
        date: String,
        unadjusted: Boolean,
        callback: PolygonRestApiCallback<DailyOpenCloseDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getDailyOpenClose(symbol, date, unadjusted, *opts) })

    /**
     * The mappings for conditions on trades and quotes.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_meta_conditions_ticktype
     */
    fun getConditionMappingsBlocking(
        tickType: ConditionMappingTickerType,
        vararg opts: PolygonRestOption
    ): Map<String, String> =
        runBlocking { getConditionMappings(tickType, *opts) }

    /** See [getConditionMappingsBlocking] */
    fun getConditionMappings(
        tickType: ConditionMappingTickerType,
        callback: PolygonRestApiCallback<Map<String, String>>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getConditionMappings(tickType, *opts) })

    /**
     * Snapshot allows you to see all tickers current minute aggregate, daily aggregate and last trade.
     * As well as previous days aggregate and calculated change for today.
     * The response size is large.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_snapshot_locale_us_markets_stocks_tickers
     */
    fun getSnapshotAllTickersBlocking(vararg opts: PolygonRestOption): SnapshotAllTickersDTO =
        runBlocking { getSnapshotAllTickers(*opts) }

    /** See [getSnapshotAllTickersBlocking] */
    fun getSnapshotAllTickers(callback: PolygonRestApiCallback<SnapshotAllTickersDTO>, vararg opts: PolygonRestOption) =
        coroutineToRestCallback(callback, { getSnapshotAllTickers(*opts) })

    /**
     * See the current snapshot of a single ticker
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_snapshot_locale_us_markets_stocks_tickers__stocksticker
     */
    fun getSnapshotBlocking(symbol: String, vararg opts: PolygonRestOption): SnapshotSingleTickerDTO =
        runBlocking { getSnapshot(symbol, *opts) }

    /** See [getSnapshotBlocking] */
    fun getSnapshot(
        symbol: String,
        callback: PolygonRestApiCallback<SnapshotSingleTickerDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getSnapshot(symbol, *opts) })

    /**
     * See the current snapshot of the top 20 gainers or losers of the day at the moment.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_snapshot_locale_us_markets_stocks__direction
     */
    fun getSnapshotGainersOrLosersBlocking(
        direction: GainersOrLosersDirection,
        vararg opts: PolygonRestOption
    ): SnapshotGainersOrLosersDTO =
        runBlocking { getSnapshotGainersOrLosers(direction, *opts) }

    /** See [getSnapshotGainersOrLosersBlocking] */
    fun getSnapshotGainersOrLosers(
        direction: GainersOrLosersDirection,
        callback: PolygonRestApiCallback<SnapshotGainersOrLosersDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getSnapshotGainersOrLosers(direction, *opts) })

    /**
     * Get the previous day close for the specified ticker
     *
     * @param unadjusted Set to true if the results should NOT be adjusted for splits.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_aggs_ticker__stocksticker__prev
     */
    fun getPreviousCloseBlocking(
        symbol: String,
        unadjusted: Boolean,
        vararg opts: PolygonRestOption
    ): PreviousCloseDTO =
        runBlocking { getPreviousClose(symbol, unadjusted, *opts) }

    /** See [getPreviousCloseBlocking] */
    fun getPreviousClose(
        symbol: String,
        unadjusted: Boolean,
        callback: PolygonRestApiCallback<PreviousCloseDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getPreviousClose(symbol, unadjusted, *opts) })

}