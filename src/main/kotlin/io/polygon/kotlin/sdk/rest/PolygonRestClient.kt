package io.polygon.kotlin.sdk.rest

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.*
import io.polygon.kotlin.sdk.DefaultJvmHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.crypto.PolygonCryptoClient
import io.polygon.kotlin.sdk.rest.forex.PolygonForexClient
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceClient
import io.polygon.kotlin.sdk.rest.stocks.PolygonStocksClient
import kotlinx.coroutines.runBlocking

/**
 * A client for the Polygon.io RESTful API
 *
 * @param apiKey the API key to use with all API requests
 * @param httpClientProvider (Optional) A provider for the ktor [HttpClient] to use; defaults to [DefaultJvmHttpClientProvider]
 * @param polygonApiDomain (Optional) The domain to hit for all API requests; defaults to Polygon's API domain "api.polyhon.io". Useful for overriding in a testing environment
 */
class PolygonRestClient
@JvmOverloads
constructor(
    private val apiKey: String,
    private val httpClientProvider: HttpClientProvider = DefaultJvmHttpClientProvider(),
    private val polygonApiDomain: String = "api.polygon.io"
) {

    /**
     * A [PolygonReferenceClient] that can be used to access Polygon reference APIs
     */
    val referenceClient by lazy { PolygonReferenceClient(this) }

    /**
     * A [PolygonStocksClient] that can be used to access Polygon stocks/equities APIs
     */
    val stocksClient by lazy { PolygonStocksClient(this) }

    /**
     * A [PolygonForexClient] that can be used to access Polygon forex/currencies APIs
     */
    val forexClient by lazy { PolygonForexClient(this) }

    /**
     * A [PolygonCryptoClient] that can be used to access Polygon crypto APIs
     */
    val cryptoClient by lazy { PolygonCryptoClient(this) }

    /**
     * Get aggregates for a date range, in custom time window sizes.
     *
     * Some tickers require a prefix, see examples below:
     *
     * Stocks: AAPL
     * Currencies: C:EURUSD
     * Crypto: X:BTCUSD
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_aggs_ticker__stocksticker__range__multiplier___timespan___from___to
     */
    fun getAggregatesBlocking(params: AggregatesParameters, vararg opts: PolygonRestOption): AggregatesDTO =
        runBlocking { getAggregates(params, *opts) }

    /** See [getAggregatesBlocking] */
    fun getAggregates(
        params: AggregatesParameters,
        callback: PolygonRestApiCallback<AggregatesDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getAggregates(params, *opts) })

    /**
     * Get the daily OHLC for entire markets.
     * The response size is large.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v2_aggs_grouped_locale_us_market_stocks__date
     */
    fun getGroupedDailyAggregatesBlocking(
        params: GroupedDailyParameters,
        vararg opts: PolygonRestOption
    ): AggregatesDTO =
        runBlocking { getGroupedDailyAggregates(params, *opts) }

    /** See [getGroupedDailyAggregatesBlocking] */
    fun getGroupedDailyAggregates(
        params: GroupedDailyParameters,
        callback: PolygonRestApiCallback<AggregatesDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getGroupedDailyAggregates(params, *opts) })

    /**
     *  Get trades for an equity/options/crypto ticker within a range
     *  Note that options symbols are prepended with "O:" and crypto with "X:"
     *
     * API Docs:
     *     https://polygon.io/docs/stocks/get_v3_trades__stockticker
     *     https://polygon.io/docs/options/get_v3_trades__optionsticker
     *     https://polygon.io/docs/crypto/get_v3_trades__cryptoticker
     */

    fun getTradesBlocking(
        params: TradesParameters,
        vararg opts: PolygonRestOption): TradesResponse =
        runBlocking { getTrades(params, *opts)}

    fun getTrades(params: TradesParameters, callback: PolygonRestApiCallback<TradesResponse>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, {getTrades(params, *opts)})
    }

    /**
     * listTrades is an iterator wrapper for getTrades
     */
    @SafeVarargs
    fun listTrades(
        params: TradesParameters,
        vararg opts: PolygonRestOption
    ): RequestIterator<TradeResult> =
        RequestIterator(
            { getTradesBlocking(params, *opts) },
            requestIteratorFetch<TradesResponse>()
        )

    /**
     *  Get quotes for an equity/forex ticker within a range
     *  Note that fx symbols are prepended with "C:" e.g. "C:EUR-USD"
     *
     * API Docs:
     *     https://polygon.io/docs/stocks/get_v3_quotes__stockticker
     *     https://polygon.io/docs/forex/get_v3_quotes__fxticker
     */
    fun getQuotesBlocking(
        params: QuotesParameters,
        vararg opts: PolygonRestOption): QuotesResponse =
        runBlocking { getQuotes(params, *opts)}

    fun getQuotes(params: QuotesParameters, callback: PolygonRestApiCallback<QuotesResponse>, vararg opts: PolygonRestOption) {
        coroutineToRestCallback(callback, {getQuotes(params, *opts)})
    }

    /**
     * listQuotes is an iterator wrapper for getQuotes
     */
    @SafeVarargs
    fun listQuotes(
        params: QuotesParameters,
        vararg opts: PolygonRestOption
    ): RequestIterator<QuoteResult> =
        RequestIterator(
            { getQuotesBlocking(params, *opts) },
            requestIteratorFetch<QuotesResponse>()
        )

    private val baseUrlBuilder: URLBuilder
        get() = httpClientProvider.getDefaultRestURLBuilder().apply {
            host = polygonApiDomain
            parameters["apiKey"] = apiKey
        }

    private inline fun <R> withHttpClient(codeBlock: (client: HttpClient) -> R) =
        httpClientProvider.buildClient().use(codeBlock)

    internal suspend inline fun <reified T> fetchResult(
        urlBuilderBlock: URLBuilder.() -> Unit,
        vararg options: PolygonRestOption
    ): T {
        val url = baseUrlBuilder.apply(urlBuilderBlock).build()
        return withHttpClient { httpClient ->
            httpClient.get(url) {
                options.forEach { this.it() }
            }
        }.body()
    }

    /**
     * Helper function for creating request iterators
     */
    internal inline fun <reified T> requestIteratorFetch(vararg opts: PolygonRestOption): (String) -> T =
        { url -> runBlocking { fetchResult({ takeFrom(url) }, *opts) } }

}
