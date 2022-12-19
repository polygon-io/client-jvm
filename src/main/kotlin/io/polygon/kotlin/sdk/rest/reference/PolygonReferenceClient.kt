package io.polygon.kotlin.sdk.rest.reference

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import io.polygon.kotlin.sdk.rest.RequestIterator
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
     * Get an iterator to iterate through all pages of results for the given parameters.
     *
     * See [getSupportedTickers] if you instead need to get exactly one page of results.
     * See section "Pagination" in the README for more details on iterators.
     */
    @SafeVarargs
    fun listSupportedTickers(
        params: SupportedTickersParameters,
        vararg opts: PolygonRestOption
    ): RequestIterator<TickerDTO> =
        RequestIterator(
            { getSupportedTickersBlocking(params, *opts) },
            polygonClient.requestIteratorFetch<TickersDTO>()
        )

    /**
     * Gets all of Polygon's currently supported ticker types
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_types
     */
    @Deprecated("use getTickerTypesBlocking instead")
    fun getSupportedTickerTypesBlocking(vararg opts: PolygonRestOption): TickerTypesDTO =
        runBlocking { getSupportedTickerTypes(*opts) }

    /** See [getSupportedTickerTypesBlocking] */
    @Deprecated("use getTickerTypes instead")
    fun getSupportedTickerTypes(callback: PolygonRestApiCallback<TickerTypesDTO>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, { getSupportedTickerTypes(*opts) })
    }

    /**
     * List all ticker types that Polygon.io has.
     *
     * API Docs: https://polygon.io/docs/stocks/get_v3_reference_tickers_types
     */
    @SafeVarargs
    fun getTickerTypesBlocking(params: TickerTypesParameters, vararg opts: PolygonRestOption): TickerTypesResponse =
        runBlocking { getTickerTypes(params, *opts) }

    /** See [getTickerTypesBlocking] */
    @SafeVarargs
    fun getTickerTypes(
        params: TickerTypesParameters,
        callback: PolygonRestApiCallback<TickerTypesResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getTickerTypes(params, *opts) })

    /**
     * Gets the details of the symbol company/entity.
     * These are important details which offer an overview of the entity.
     * Things like name, sector, description, logo and similar companies.
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v1_meta_symbols_symbol_company
     */
    @Deprecated(
        "supserseded by getTickerDetailsV3Blocking and will be replaced in a future verison",
        ReplaceWith("getTickerDetailsV3Blocking(symbol, params, *opts)")
    )
    fun getTickerDetailsBlocking(symbol: String, vararg opts: PolygonRestOption): TickerDetailsDTO =
        runBlocking { getTickerDetails(symbol, *opts) }

    /** See [getTickerDetailsBlocking] */
    @Deprecated("supserseded by getTickerDetailsV3 and will be replaced in a future verison", ReplaceWith("getTickerDetailsV3(symbol, params, *opts)"))
    fun getTickerDetails(
        symbol: String,
        callback: PolygonRestApiCallback<TickerDetailsDTO>,
        vararg opts: PolygonRestOption
    ) {
        coroutineToRestCallback(callback, { getTickerDetails(symbol, *opts) })
    }

    /**
     * Get a single ticker supported by Polygon.io.
     * This response will have detailed information about the ticker and the company behind it.
     *
     * Note: will be renamed getTickerDetailsBlocking in a future version.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v3_reference_tickers__ticker
     */
    fun getTickerDetailsV3Blocking(
        ticker: String,
        params: TickerDetailsParameters,
        vararg opts: PolygonRestOption
    ): TickerDetailsResponse =
        runBlocking { getTickerDetailsV3(ticker, params, *opts) }

    /** See [getTickerDetailsV3Blocking] */
    fun getTickerDetailsV3(
        ticker: String,
        params: TickerDetailsParameters,
        callback: PolygonRestApiCallback<TickerDetailsResponse>,
        vararg opts: PolygonRestOption,
    ) =
        coroutineToRestCallback(callback, { getTickerDetailsV3(ticker, params, *opts) })

    /**
     * Get news articles for a given ticker
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v1_meta_symbols_symbol_news
     */
    @Deprecated(
        "superseded by getTickerNewsBlockingV2 and listTickerNewsV2",
        ReplaceWith("getTickerNewsBlockingV2(params, *opts)")
    )
    fun getTickerNewsBlocking(params: TickerNewsParameters, vararg opts: PolygonRestOption): List<TickerNewsDTO> =
        runBlocking { getTickerNews(params, *opts) }

    /** See [getTickerNewsBlocking] */
    @Deprecated(
        "superseded by getTickerNewsV2 and listTickerNewsV2",
        ReplaceWith("getTickerNewsV2(params, *opts)")
    )
    fun getTickerNews(
        params: TickerNewsParameters,
        callback: PolygonRestApiCallback<List<TickerNewsDTO>>,
        vararg opts: PolygonRestOption
    ) {
        coroutineToRestCallback(callback, { getTickerNews(params, *opts) })
    }

    /**
     * Get the most recent news articles relating to a stock ticker symbol,
     * including a summary of the article and a link to the original source.
     *
     * Note: This will be renamed to getTickerNewsBlocking in a future release
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_reference_news
     */
    @SafeVarargs
    fun getTickerNewsBlockingV2(params: TickerNewsParametersV2, vararg opts: PolygonRestOption): TickerNewsResponse =
        runBlocking { getTickerNewsV2(params, *opts) }

    @SafeVarargs
    fun getTickerNewsV2(
        params: TickerNewsParametersV2,
        callback: PolygonRestApiCallback<TickerNewsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getTickerNewsV2(params, *opts) })

    /**
     * Get an iterator to iterate through all pages of results for the given parameters.
     *
     * See [getTickerNewsBlockingV2] if you instead need to get exactly one page of results.
     * See section "Pagination" in the README for more details on iterators.
     */
    @SafeVarargs
    fun listTickerNewsV2(params: TickerNewsParametersV2, vararg opts: PolygonRestOption): RequestIterator<TickerNews> =
        RequestIterator(
            { getTickerNewsBlockingV2(params, *opts) },
            polygonClient.requestIteratorFetch(*opts)
        )

    /**
     * Gets all of Polygon's currently supported markets.
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_markets
     */
    @Deprecated("This API is no longer supported and will be sunset some time in the future")
    fun getSupportedMarketsBlocking(vararg opts: PolygonRestOption): MarketsDTO =
        runBlocking { getSupportedMarkets(*opts) }

    /** See [getSupportedMarketsBlocking] */
    @Deprecated("This API is no longer supported and will be sunset some time in the future")
    fun getSupportedMarkets(callback: PolygonRestApiCallback<MarketsDTO>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, { getSupportedMarkets(*opts) })
    }

    /**
     * Gets all of Polygon's currently supported locales
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_locales
     */
    @Deprecated("This API is no longer supported and will be sunset some time in the future")
    fun getSupportedLocalesBlocking(vararg opts: PolygonRestOption): LocalesDTO =
        runBlocking { getSupportedLocales(*opts) }

    /** See [getSupportedLocalesBlocking] */
    @Deprecated("This API is no longer supported and will be sunset some time in the future")
    fun getSupportedLocales(callback: PolygonRestApiCallback<LocalesDTO>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, { getSupportedLocales(*opts) })
    }

    /**
     * Gets the historical splits for a symbol
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_splits_symbol
     */
    @Deprecated("use getSplitsBlocking or listSplits instead", ReplaceWith("getSplitsBlocking(params, *opts)"))
    fun getStockSplitsBlocking(symbol: String, vararg opts: PolygonRestOption): StockSplitsDTO =
        runBlocking { getStockSplits(symbol, *opts) }

    /** See [getStockSplitsBlocking] */
    @Deprecated("use getSplits or listSplits instead", ReplaceWith("getSplits(params, callback, *opts)"))
    fun getStockSplits(
        symbol: String,
        callback: PolygonRestApiCallback<StockSplitsDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getStockSplits(symbol, *opts) })

    /**
     * Get a list of historical stock splits,
     * including the ticker symbol, the execution date, and the factors of the split ratio.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v3_reference_splits
     */
    fun getSplitsBlocking(params: SplitsParameters, vararg opts: PolygonRestOption): SplitsResponse =
        runBlocking { getSplits(params, *opts) }

    /** See [getSplitsBlocking] */
    fun getSplits(
        params: SplitsParameters,
        callback: PolygonRestApiCallback<SplitsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getSplits(params, *opts) })

    /**
     * Get an iterator to iterate through all pages of results for the given parameters.
     *
     * See [getSplitsBlocking] if you instead need to get exactly one page of results.
     * See section "Pagination" in the README for more details on iterators.
     */
    @SafeVarargs
    fun listSplits(params: SplitsParameters, vararg opts: PolygonRestOption): RequestIterator<Split> =
        RequestIterator(
            { getSplitsBlocking(params, *opts) },
            polygonClient.requestIteratorFetch(*opts)
        )

    /**
     * Gets the historical dividends for a symbol
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_dividends_symbol
     */
    @Deprecated("use getDividendsBlocking or listDividends", ReplaceWith("getDividendsBlocking(params, *opts)"))
    fun getStockDividendsBlocking(symbol: String, vararg opts: PolygonRestOption): StockDividendsDTO =
        runBlocking { getStockDividends(symbol, *opts) }

    /** See [getStockDividendsBlocking] */
    @Deprecated("use getDividends or listDividends", ReplaceWith("getDividends(params, callback, *opts)"))
    fun getStockDividends(
        symbol: String,
        callback: PolygonRestApiCallback<StockDividendsDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getStockDividends(symbol, *opts) })

    /**
     * Get a list of historical cash dividends,
     * including the ticker symbol, declaration date, ex-dividend date, record date, pay date, frequency, and amount.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v3_reference_dividends
     */
    @SafeVarargs
    fun getDividendsBlocking(params: DividendsParameters, vararg opts: PolygonRestOption): DividendsResponse =
        runBlocking { getDividends(params, *opts) }

    /** See [getDividendsBlocking] */
    @SafeVarargs
    fun getDividends(
        params: DividendsParameters,
        callback: PolygonRestApiCallback<DividendsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getDividends(params, *opts) })

    /**
     * Get an iterator to iterate through all pages of results for the given parameters.
     *
     * See [getDividendsBlocking] if you instead need to get exactly one page of results.
     * See section "Pagination" in the README for more details on iterators.
     */
    @SafeVarargs
    fun listDividends(params: DividendsParameters, vararg opts: PolygonRestOption): RequestIterator<Dividend> =
        RequestIterator(
            { getDividendsBlocking(params, *opts) },
            polygonClient.requestIteratorFetch(*opts)
        )

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

    /**
     * List all conditions that Polygon.io uses.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v3_reference_conditions
     */
    fun getConditionsBlocking(params: ConditionsParameters, vararg opts: PolygonRestOption): ConditionsResponse =
        runBlocking { getConditions(params, *opts) }

    /** See [PolygonReferenceClient.getConditionsBlocking] */
    @SafeVarargs
    fun getConditions(
        params: ConditionsParameters,
        callback: PolygonRestApiCallback<ConditionsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getConditions(params, *opts) })

    /**
     * Get an iterator to iterate through all pages of results for the given parameters.
     *
     * See [getConditionsBlocking] if you instead need to get exactly one page of results.
     * See section "Pagination" in the README for more details on iterators.
     */
    @SafeVarargs
    fun listConditions(params: ConditionsParameters, vararg opts: PolygonRestOption): RequestIterator<Condition> =
        RequestIterator(
            { getConditionsBlocking(params, *opts) },
            polygonClient.requestIteratorFetch(*opts)
        )
}