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

}