package io.polygon.kotlin.sdk.rest.reference

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Reference" RESTful APIs
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonReferenceClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * Gets the supported tickers based on the given parameters
     *
     * API Doc: https://polygon.io/docs/stocks/get_v3_reference_tickers
     */
    fun getSupportedTickersBlocking(params: SupportedTickersParameters, vararg opts: PolygonRestOption): TickersDTO =
        runBlocking { getSupportedTickers(params, *opts) }

    /** See [getSupportedTickersBlocking] */
    fun getSupportedTickers(
        params: SupportedTickersParameters,
        callback: PolygonRestApiCallback<TickersDTO>,
        vararg opts: PolygonRestOption
    ) {
        coroutineToRestCallback(callback, { getSupportedTickers(params, *opts) })
    }

    /**
     * @deprecated use new getSupportTickerTypes with params arg
     * Gets all of Polygon's currently supported ticker types
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_types
     */

    @Deprecated("use getSupportedTickerTypesBlocking with params arg", ReplaceWith("getSupportedTickerTypesBlocking(params, *opts)"))
    fun getSupportedTickerTypesBlocking(vararg opts: PolygonRestOption): TickerTypesDTO =
        runBlocking { getSupportedTickerTypes(*opts) }

    /** See [getSupportedTickerTypesBlocking] */
    @Deprecated("use getSupportedTickerTypes with params arg", ReplaceWith("getSupportedTickerTypes(params, callback, *opts)"))
    fun getSupportedTickerTypes(callback: PolygonRestApiCallback<TickerTypesDTO>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, { getSupportedTickerTypes(*opts) })
    }

    /**
     * List all ticker types that Polygon.io has.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v3_reference_tickers_types
     */
    fun getSupportedTickerTypesBlocking(params: TickerTypeParameters, vararg opts: PolygonRestOption): TickerTypesResponse =
        runBlocking { getSupportedTickerTypes(params, *opts )}

    fun getSupportedTickerTypes(params: TickerTypeParameters, callback: PolygonRestApiCallback<TickerTypesResponse>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, { getSupportedTickerTypes(params, *opts) })
    }
    /**
     * Gets the details of the symbol company/entity.
     * These are important details which offer an overview of the entity.
     * Things like name, sector, description, logo and similar companies.
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v1_meta_symbols_symbol_company
     */
    fun getTickerDetailsBlocking(symbol: String, vararg opts: PolygonRestOption): TickerDetailsDTO =
        runBlocking { getTickerDetails(symbol, *opts) }

    /** See [getTickerDetailsBlocking] */
    fun getTickerDetails(
        symbol: String,
        callback: PolygonRestApiCallback<TickerDetailsDTO>,
        vararg opts: PolygonRestOption
    ) {
        coroutineToRestCallback(callback, { getTickerDetails(symbol, *opts) })
    }

    /**
     * Get news articles for a given ticker
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v1_meta_symbols_symbol_news
     */
    fun getTickerNewsBlocking(params: TickerNewsParameters, vararg opts: PolygonRestOption): List<TickerNewsDTO> =
        runBlocking { getTickerNews(params, *opts) }

    /** See [getTickerNewsBlocking] */
    fun getTickerNews(
        params: TickerNewsParameters,
        callback: PolygonRestApiCallback<List<TickerNewsDTO>>,
        vararg opts: PolygonRestOption
    ) {
        coroutineToRestCallback(callback, { getTickerNews(params, *opts) })
    }

    /**
     * Gets all of Polygon's currently supported markets.
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_markets
     */
    fun getSupportedMarketsBlocking(vararg opts: PolygonRestOption): MarketsDTO =
        runBlocking { getSupportedMarkets(*opts) }

    /** See [getSupportedMarketsBlocking] */
    fun getSupportedMarkets(callback: PolygonRestApiCallback<MarketsDTO>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, { getSupportedMarkets(*opts) })
    }

    /**
     * Gets all of Polygon's currently supported locales
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_locales
     */
    fun getSupportedLocalesBlocking(vararg opts: PolygonRestOption): LocalesDTO =
        runBlocking { getSupportedLocales(*opts) }

    /** See [getSupportedLocalesBlocking] */
    fun getSupportedLocales(callback: PolygonRestApiCallback<LocalesDTO>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, { getSupportedLocales(*opts) })
    }

    /**
     * Gets the historical splits for a symbol
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_splits_symbol
     */
    fun getStockSplitsBlocking(symbol: String, vararg opts: PolygonRestOption): StockSplitsDTO =
        runBlocking { getStockSplits(symbol, *opts) }

    /** See [getStockSplitsBlocking] */
    fun getStockSplits(
        symbol: String,
        callback: PolygonRestApiCallback<StockSplitsDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getStockSplits(symbol, *opts) })

    /**
     * Gets the historical dividends for a symbol
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_dividends_symbol
     */
    fun getStockDividendsBlocking(symbol: String, vararg opts: PolygonRestOption): StockDividendsDTO =
        runBlocking { getStockDividends(symbol, *opts) }

    /** See [getStockDividendsBlocking] */
    fun getStockDividends(
        symbol: String,
        callback: PolygonRestApiCallback<StockDividendsDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getStockDividends(symbol, *opts) })

    /**
     * Gets the historical financials for a symbol
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_financials_symbol
     */
    fun getStockFinancialsBlocking(
        params: StockFinancialsParameters,
        vararg opts: PolygonRestOption
    ): StockFinancialsResultsDTO =
        runBlocking { getStockFinancials(params, *opts) }

    /** See [getStockFinancialsBlocking] */
    fun getStockFinancials(
        params: StockFinancialsParameters,
        callback: PolygonRestApiCallback<StockFinancialsResultsDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getStockFinancials(params, *opts) })

    /**
     * Current status of each market
     *
     * API Doc: https://polygon.io/docs/stocks/get_v1_marketstatus_now
     */
    fun getMarketStatusBlocking(vararg opts: PolygonRestOption): MarketStatusDTO =
        runBlocking { getMarketStatus(*opts) }

    /** See [getMarketStatusBlocking] */
    fun getMarketStatus(callback: PolygonRestApiCallback<MarketStatusDTO>, vararg opts: PolygonRestOption) =
        coroutineToRestCallback(callback, { getMarketStatus(*opts) })

    /**
     * Get upcoming market holidays and their open/close times
     *
     * API Doc: https://polygon.io/docs/stocks/get_v1_marketstatus_upcoming
     */
    fun getMarketHolidaysBlocking(vararg opts: PolygonRestOption): List<MarketHolidayDTO> =
        runBlocking { getMarketHolidays(*opts) }

    /** See [getMarketHolidaysBlocking] */
    fun getMarketHolidays(callback: PolygonRestApiCallback<List<MarketHolidayDTO>>, vararg opts: PolygonRestOption) =
        coroutineToRestCallback(callback, { getMarketHolidays(*opts) })
}