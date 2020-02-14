package io.polygon.kotlin.sdk.rest.reference

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Reference" RESTful APIs
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonReferenceRestClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * Gets the supported tickers based on the given parameters
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_tickers
     */
    fun getSupportedTickersBlocking(params: SupportedTickersParameters): TickersDTO =
        runBlocking { getSupportedTickers(params) }

    /** See [getSupportedTickersBlocking] */
    fun getSupportedTickers(
        params: SupportedTickersParameters,
        callback: PolygonRestApiCallback<TickersDTO>
    ) {
        coroutineToRestCallback(callback, { getSupportedTickers(params) })
    }

    /**
     * Gets all of Polygon's currently supported ticker types
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_types
     */
    fun getSupportedTickerTypesBlocking(): TickerTypesDTO =
        runBlocking { getSupportedTickerTypes() }

    /** See [getSupportedTickerTypesBlocking] */
    fun getSupportedTickerTypes(callback: PolygonRestApiCallback<TickerTypesDTO>) {
        coroutineToRestCallback(callback, { getSupportedTickerTypes() })
    }

    /**
     * Gets the details of the symbol company/entity.
     * These are important details which offer an overview of the entity.
     * Things like name, sector, description, logo and similar companies.
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v1_meta_symbols_symbol_company
     */
    fun getTickerDetailsBlocking(symbol: String): TickerDetailsDTO =
        runBlocking { getTickerDetails(symbol) }

    /** See [getTickerDetailsBlocking] */
    fun getTickerDetails(symbol: String, callback: PolygonRestApiCallback<TickerDetailsDTO>) {
        coroutineToRestCallback(callback, { getTickerDetails(symbol) })
    }

    /**
     * Get news articles for a given ticker
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v1_meta_symbols_symbol_news
     */
    fun getTickerNewsBlocking(params: TickerNewsParameters): List<TickerNewsDTO> =
        runBlocking { getTickerNews(params) }

    /** See [getTickerNewsBlocking] */
    fun getTickerNews(
        params: TickerNewsParameters,
        callback: PolygonRestApiCallback<List<TickerNewsDTO>>
    ) {
        coroutineToRestCallback(callback, { getTickerNews(params) })
    }

    /**
     * Gets all of Polygon's currently supported markets.
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_markets
     */
    fun getSupportedMarketsBlocking(): MarketsDTO =
        runBlocking { getSupportedMarkets() }

    /** See [getSupportedMarketsBlocking] */
    fun getSupportedMarkets(callback: PolygonRestApiCallback<MarketsDTO>) {
        coroutineToRestCallback(callback, { getSupportedMarkets() })
    }

    /**
     * Gets all of Polygon's currently supported locales
     *
     * API Doc: https://polygon.io/docs/#!/Reference/get_v2_reference_locales
     */
    fun getSupportedLocalesBlocking(): LocalesDTO =
        runBlocking { getSupportedLocales() }

    /** See [getSupportedLocalesBlocking] */
    fun getSupportedLocales(callback: PolygonRestApiCallback<LocalesDTO>) {
        coroutineToRestCallback(callback, { getSupportedLocales() })
    }

}