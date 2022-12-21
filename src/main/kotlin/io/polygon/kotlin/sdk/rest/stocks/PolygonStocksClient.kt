package io.polygon.kotlin.sdk.rest.stocks

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceClient
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Stocks / Equities" pricing data RESTful APIs
 * For common pricing APIs shared across asset classes, see [PolygonRestClient].
 * For reference data, see [PolygonReferenceClient].
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
    @SafeVarargs
    @Deprecated("use getExchanges in PolygonReferenceClient instead")
    fun getSupportedExchangesBlocking(vararg opts: PolygonRestOption): List<ExchangeDTO> =
        runBlocking { getSupportedExchanges(*opts) }

    /** See [getSupportedExchangesBlocking] */
    @SafeVarargs
    @Deprecated("use getExchanges in PolygonReferenceClient instead")
    fun getSupportedExchanges(callback: PolygonRestApiCallback<List<ExchangeDTO>>, vararg opts: PolygonRestOption) =
        coroutineToRestCallback(callback, { getSupportedExchanges(*opts) })

    /**
     * Get historic trades for a ticker.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v2_ticks_stocks_trades_ticker_date
     */
    @SafeVarargs
    @Deprecated("use listTrades or getTradesBlocking in PolygonRestClient", ReplaceWith("listTrades(params, *opts)"))
    fun getHistoricTradesBlocking(params: HistoricTradesParameters, vararg opts: PolygonRestOption): HistoricTradesDTO =
        runBlocking { getHistoricTrades(params, *opts) }

    /** See [getHistoricTradesBlocking] */
    @SafeVarargs
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
    @SafeVarargs
    @Deprecated("superseded by listQuotes/getQuotesBlocking in PolygonRestClient",ReplaceWith("getQuotesBlocking(params, *ops)"))
    fun getHistoricQuotesBlocking(params: HistoricQuotesParameters, vararg opts: PolygonRestOption): HistoricQuotesDTO =
        runBlocking { getHistoricQuotes(params, *opts) }

    /** See [getHistoricQuotesBlocking] */
    @SafeVarargs
    @Deprecated("superseded by listQuotes/getQuotes in PolygonRestClient",ReplaceWith("getQuotes(params, callback, *ops)"))
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
    @SafeVarargs
    @Deprecated("superseded by getLastTradeV2 and will be replaced in a future version", ReplaceWith("getLastTradeV2(ticker, *opts)"))
    fun getLastTradeBlocking(symbol: String, vararg opts: PolygonRestOption): LastTradeResultDTO =
        runBlocking { getLastTrade(symbol, *opts) }

    /** See [getLastTradeBlocking] */
    @SafeVarargs
    @Deprecated("replaced by getLastTradeV2", ReplaceWith("getLastTradeV2(ticker, callback, *opts)"))
    fun getLastTrade(
        symbol: String,
        callback: PolygonRestApiCallback<LastTradeResultDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getLastTrade(symbol, *opts) })


    /**
     * Get the most recent trade for a given ticker.
     * Note: will be renamed getLastTradeBlocking in a future version
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_last_trade__stocksticker
     */
    @SafeVarargs
    fun getLastTradeBlockingV2(ticker: String, vararg opts: PolygonRestOption): LastTradeResultV2 =
        runBlocking { getLastTradeV2(ticker, *opts) }

    /** See [getLastTradeBlockingV2] */
    @SafeVarargs
    fun getLastTradeV2(ticker: String, callback: PolygonRestApiCallback<LastTradeResultV2>, vararg opts: PolygonRestOption) =
        coroutineToRestCallback(callback, { getLastTradeV2(ticker, *opts)} )

    /**
     * Get the last quote tick for a given stock.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_last_quote_stocks_symbol
     */
    @SafeVarargs
    @Deprecated("superseded by getLastQuoteBlockingV2", ReplaceWith("getLastQuoteBlockingV2(ticker, *opts)"))
    fun getLastQuoteBlocking(symbol: String, vararg opts: PolygonRestOption): LastQuoteResultDTO =
        runBlocking { getLastQuote(symbol, *opts) }

    /** See [getLastQuoteBlocking] */
    @SafeVarargs
    @Deprecated("superseded by getLastQuoteV2", ReplaceWith("getLastQuoteV2(ticker, callback, *opts)"))
    fun getLastQuote(
        symbol: String,
        callback: PolygonRestApiCallback<LastQuoteResultDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getLastQuote(symbol, *opts) })

    /**
     * Get the most recent NBBO (Quote) tick for a given stock.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_last_nbbo__stocksticker
     */
    @SafeVarargs
    fun getLastQuoteBlockingV2(ticker: String, vararg opts: PolygonRestOption): LastQuoteResultV2 =
        runBlocking { getLastQuoteV2(ticker, *opts) }

    /** See [getLastQuoteBlockingV2] */
    @SafeVarargs
    fun getLastQuoteV2(ticker: String, callback: PolygonRestApiCallback<LastQuoteResultV2>, vararg opts: PolygonRestOption) =
        coroutineToRestCallback(callback, { getLastQuoteV2(ticker, *opts)} )

    /**
     * Get the open, close and afterhours prices of a symbol on a certain date.
     * @param date The date to get the open, close and after hours prices for (YYYY-MM-DD)
     *
     * API Doc: https://polygon.io/docs/stocks/get_v1_open-close__stocksticker___date
     */
    @SafeVarargs
    fun getDailyOpenCloseBlocking(
        symbol: String,
        date: String,
        unadjusted: Boolean,
        vararg opts: PolygonRestOption
    ): DailyOpenCloseDTO =
        runBlocking { getDailyOpenClose(symbol, date, unadjusted, *opts) }

    /** See [getDailyOpenCloseBlocking] */
    @SafeVarargs
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
    @SafeVarargs
    @Deprecated("use getConditions in PolygonReferenceClient instead")
    fun getConditionMappingsBlocking(
        tickType: ConditionMappingTickerType,
        vararg opts: PolygonRestOption
    ): Map<String, String> =
        runBlocking { getConditionMappings(tickType, *opts) }

    /** See [getConditionMappingsBlocking] */
    @SafeVarargs
    @Deprecated("use getConditions in PolygonReferenceClient instead")
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
    @SafeVarargs
    fun getSnapshotAllTickersBlocking(vararg opts: PolygonRestOption): SnapshotAllTickersDTO =
        runBlocking { getSnapshotAllTickers(*opts) }

    /** See [getSnapshotAllTickersBlocking] */
    @SafeVarargs
    fun getSnapshotAllTickers(callback: PolygonRestApiCallback<SnapshotAllTickersDTO>, vararg opts: PolygonRestOption) =
        coroutineToRestCallback(callback, { getSnapshotAllTickers(*opts) })

    /**
     * See the current snapshot of a single ticker
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_snapshot_locale_us_markets_stocks_tickers__stocksticker
     */
    @SafeVarargs
    fun getSnapshotBlocking(symbol: String, vararg opts: PolygonRestOption): SnapshotSingleTickerDTO =
        runBlocking { getSnapshot(symbol, *opts) }

    /** See [getSnapshotBlocking] */
    @SafeVarargs
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
    @SafeVarargs
    fun getSnapshotGainersOrLosersBlocking(
        direction: GainersOrLosersDirection,
        vararg opts: PolygonRestOption
    ): SnapshotGainersOrLosersDTO =
        runBlocking { getSnapshotGainersOrLosers(direction, *opts) }

    /** See [getSnapshotGainersOrLosersBlocking] */
    @SafeVarargs
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
    @SafeVarargs
    fun getPreviousCloseBlocking(
        symbol: String,
        unadjusted: Boolean,
        vararg opts: PolygonRestOption
    ): PreviousCloseDTO =
        runBlocking { getPreviousClose(symbol, unadjusted, *opts) }

    /** See [getPreviousCloseBlocking] */
    @SafeVarargs
    fun getPreviousClose(
        symbol: String,
        unadjusted: Boolean,
        callback: PolygonRestApiCallback<PreviousCloseDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getPreviousClose(symbol, unadjusted, *opts) })

}