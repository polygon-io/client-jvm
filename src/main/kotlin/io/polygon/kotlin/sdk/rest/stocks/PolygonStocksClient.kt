package io.polygon.kotlin.sdk.rest.stocks

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
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
    fun getSupportedExchangesBlocking(): List<ExchangeDTO> =
        runBlocking { getSupportedExchanges() }
    
    /** See [getSupportedExchangesBlocking] */
    fun getSupportedExchanges(callback: PolygonRestApiCallback<List<ExchangeDTO>>) =
        coroutineToRestCallback(callback, { getSupportedExchanges() })
 
    /**
     * Get historic trades for a ticker.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v2_ticks_stocks_trades_ticker_date
     */
    fun getHistoricTradesBlocking(params: HistoricTradesParameters): HistoricTradesDTO =
        runBlocking { getHistoricTrades(params) }
    
    /** See [getHistoricTradesBlocking] */
    fun getHistoricTrades(params: HistoricTradesParameters, callback: PolygonRestApiCallback<HistoricTradesDTO>) =
        coroutineToRestCallback(callback, { getHistoricTrades(params) })
    
    /**
     * Get historic NBBO quotes for a ticker.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v2_ticks_stocks_nbbo_ticker_date
     */
    fun getHistoricQuotesBlocking(params: HistoricQuotesParameters): HistoricQuotesDTO =
        runBlocking { getHistoricQuotes(params) }
    
    /** See [getHistoricQuotesBlocking] */
    fun getHistoricQuotes(params: HistoricQuotesParameters, callback: PolygonRestApiCallback<HistoricQuotesDTO>) =
        coroutineToRestCallback(callback, { getHistoricQuotes(params) })

    /**
     * Get the last trade for a given stock.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_last_stocks_symbol
     */
    fun getLastTradeBlocking(symbol: String): LastTradeResultDTO =
        runBlocking { getLastTrade(symbol) }
    
    /** See [getLastTradeBlocking] */
    fun getLastTrade(symbol: String, callback: PolygonRestApiCallback<LastTradeResultDTO>) =
        coroutineToRestCallback(callback, { getLastTrade(symbol) })

    /**
     * Get the last quote tick for a given stock.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_last_quote_stocks_symbol
     */
    fun getLastQuoteBlocking(symbol: String): LastQuoteResultDTO =
        runBlocking { getLastQuote(symbol) }
    
    /** See [getLastQuoteBlocking] */
    fun getLastQuote(symbol: String, callback: PolygonRestApiCallback<LastQuoteResultDTO>) =
        coroutineToRestCallback(callback, { getLastQuote(symbol) })
    
    /**
     * Get the open, close and afterhours prices of a symbol on a certain date.
     * @param date The date to get the open, close and after hours prices for (YYYY-MM-DD)
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_open_close_symbol_date
     */
    fun getDailyOpenCloseBlocking(symbol: String, date: String): DailyOpenCloseDTO =
        runBlocking { getDailyOpenClose(symbol, date) }
    
    /** See [getDailyOpenCloseBlocking] */
    fun getDailyOpenClose(symbol: String, date: String, callback: PolygonRestApiCallback<DailyOpenCloseDTO>) =
        coroutineToRestCallback(callback, { getDailyOpenClose(symbol, date) })

    /**
     * The mappings for conditions on trades and quotes.
     *
     * API Doc: https://polygon.io/docs/#!/Stocks--Equities/get_v1_meta_conditions_ticktype
     */
    fun getConditionMappingsBlocking(tickType: ConditionMappingTickerType): Map<String, String> =
        runBlocking { getConditionMappings(tickType) }
    
    /** See [getConditionMappingsBlocking] */
    fun getConditionMappings(tickType: ConditionMappingTickerType, callback: PolygonRestApiCallback<Map<String, String>>) =
        coroutineToRestCallback(callback, { getConditionMappings(tickType) })

    /**
     * Snapshot allows you to see all tickers current minute aggregate, daily aggregate and last trade.
     * As well as previous days aggregate and calculated change for today.
     * The response size is large.
     *
     * API Doc: https://polygon.io/docs/#get_v2_snapshot_locale_us_markets_stocks_tickers_anchor
     */
    fun getSnapshotAllTickersBlocking(): SnapshotAllTickersDTO =
        runBlocking { getSnapshotAllTickers() }
    
    /** See [getSnapshotAllTickersBlocking] */
    fun getSnapshotAllTickers(callback: PolygonRestApiCallback<SnapshotAllTickersDTO>) =
        coroutineToRestCallback(callback, { getSnapshotAllTickers() })

    /**
     * See the current snapshot of a single ticker
     *
     * API Doc: https://polygon.io/docs/#get_v2_snapshot_locale_us_markets_stocks_tickers__ticker__anchor
     */
    fun getSnapshotBlocking(symbol: String): SnapshotSingleTickerDTO =
        runBlocking { getSnapshot(symbol) }
    
    /** See [getSnapshotBlocking] */
    fun getSnapshot(symbol: String, callback: PolygonRestApiCallback<SnapshotSingleTickerDTO>) =
        coroutineToRestCallback(callback, { getSnapshot(symbol) })

    /**
     * See the current snapshot of the top 20 gainers or losers of the day at the moment.
     *
     * API Doc: https://polygon.io/docs/#get_v2_snapshot_locale_us_markets_stocks__direction__anchor
     */
    fun getSnapshotGainersOrLosersBlocking(direction: GainersOrLosersDirection): SnapshotGainersOrLosersDTO =
        runBlocking { getSnapshotGainersOrLosers(direction) }
    
    /** See [getSnapshotGainersOrLosersBlocking] */
    fun getSnapshotGainersOrLosers(direction: GainersOrLosersDirection, callback: PolygonRestApiCallback<SnapshotGainersOrLosersDTO>) =
        coroutineToRestCallback(callback, { getSnapshotGainersOrLosers(direction) })
}