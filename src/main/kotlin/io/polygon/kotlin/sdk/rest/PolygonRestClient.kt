package io.polygon.kotlin.sdk.rest

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.*
import io.polygon.kotlin.sdk.DefaultJvmHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import io.polygon.kotlin.sdk.Version
import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.crypto.PolygonCryptoClient
import io.polygon.kotlin.sdk.rest.experimental.ExperimentalAPI
import io.polygon.kotlin.sdk.rest.experimental.PolygonExperimentalClient
import io.polygon.kotlin.sdk.rest.forex.PolygonForexClient
import io.polygon.kotlin.sdk.rest.indices.PolygonIndicesClient
import io.polygon.kotlin.sdk.rest.options.PolygonOptionsClient
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceClient
import io.polygon.kotlin.sdk.rest.stocks.PolygonStocksClient
import kotlinx.coroutines.runBlocking

/**
 * A client for the Polygon.io RESTful API
 *
 * @param apiKey the API key to use with all API requests
 * @param httpClientProvider (Optional) A provider for the ktor [HttpClient] to use; defaults to [DefaultJvmHttpClientProvider]
 * @param polygonApiDomain (Optional) The domain to hit for all API requests; defaults to Polygon's API domain "api.polygon.io". Useful for overriding in a testing environment
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
     * A [PolygonOptionsClient] that can be used to access Polygon options pricing data APIs
     */
    val optionsClient by lazy { PolygonOptionsClient(this) }

    /**
     * A [PolygonForexClient] that can be used to access Polygon forex/currencies APIs
     */
    val forexClient by lazy { PolygonForexClient(this) }

    /**
     * A [PolygonCryptoClient] that can be used to access Polygon crypto APIs
     */
    val cryptoClient by lazy { PolygonCryptoClient(this) }

    /**
     * A [PolygonIndicesClient] that can be used to access Polygon indices APIs
     */
    val indicesClient by lazy { PolygonIndicesClient(this) }

    /**
     * A [PolygonExperimentalClient] that can be used to access vX Polygon APIs
     * @see PolygonExperimentalClient
     */
    @ExperimentalAPI
    val experimentalClient by lazy { PolygonExperimentalClient(this) }

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
    @SafeVarargs
    fun getAggregatesBlocking(params: AggregatesParameters, vararg opts: PolygonRestOption): AggregatesDTO =
        runBlocking { getAggregates(params, *opts) }

    /** See [getAggregatesBlocking] */
    @SafeVarargs
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
    @SafeVarargs
    fun getGroupedDailyAggregatesBlocking(
        params: GroupedDailyParameters,
        vararg opts: PolygonRestOption
    ): AggregatesDTO =
        runBlocking { getGroupedDailyAggregates(params, *opts) }

    /** See [getGroupedDailyAggregatesBlocking] */
    @SafeVarargs
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
    @SafeVarargs
    fun getTradesBlocking(
        ticker: String,
        params: TradesParameters,
        vararg opts: PolygonRestOption
    ): TradesResponse =
        runBlocking { getTrades(ticker, params, *opts) }

    @SafeVarargs
    fun getTrades(
        ticker: String,
        params: TradesParameters,
        callback: PolygonRestApiCallback<TradesResponse>,
        vararg opts: PolygonRestOption
    ) {
        coroutineToRestCallback(callback, { getTrades(ticker, params, *opts) })
    }

    /**
     * listTrades is an iterator wrapper for getTrades
     */
    @SafeVarargs
    fun listTrades(
        ticker: String,
        params: TradesParameters,
        vararg opts: PolygonRestOption
    ): RequestIterator<Trade> =
        RequestIterator(
            { getTradesBlocking(ticker, params, *opts) },
            requestIteratorFetch<TradesResponse>()
        )

    /**
     *  Get quotes for an equity/forex ticker within a range
     *  Note that fx symbols are prepended with "C:" and options with "O:"
     *  e.g. "C:EUR-USD" & "O:SPY241220P00720000"
     *
     * API Docs:
     *     https://polygon.io/docs/stocks/get_v3_quotes__stockticker
     *     https://polygon.io/docs/forex/get_v3_quotes__fxticker
     *     https://polygon.io/docs/options/get_v3_quotes__optionsticker
     */
    @SafeVarargs
    fun getQuotesBlocking(
        ticker: String,
        params: QuotesParameters,
        vararg opts: PolygonRestOption
    ): QuotesResponse =
        runBlocking { getQuotes(ticker, params, *opts) }

    @SafeVarargs
    fun getQuotes(
        ticker: String,
        params: QuotesParameters,
        callback: PolygonRestApiCallback<QuotesResponse>,
        vararg opts: PolygonRestOption
    ) {
        coroutineToRestCallback(callback, { getQuotes(ticker, params, *opts) })
    }

    /**
     *  Get snapshots for a list of tickers of any asset class
     *  Note that non-stocks symbols must be prepended, e.g.
     *      Options:  "O:<ticker>"
     *      Crypto:   "X:<ticker>"
     *      Indices: "I:<ticker>"
     *      Forex:    "C:<ticker>"
     * API Docs:
     *     https://polygon.io/docs/stocks/get_v3_snapshot
     *     https://polygon.io/docs/options/get_v3_snapshot
     *     https://polygon.io/docs/indices/get_v3_snapshot
     *     https://polygon.io/docs/forex/get_v3_snapshot
     *     https://polygon.io/docs/crypto/get_v3_snapshot
     */
    @SafeVarargs
    fun getSnapshotsBlocking(
        params: SnapshotsParameters,
        vararg opts: PolygonRestOption
    ) : SnapshotsResponse =
        runBlocking { getSnapshots(params, *opts) }

    @SafeVarargs
    fun getSnapshots(
        params: SnapshotsParameters,
        callback: PolygonRestApiCallback<SnapshotsResponse>,
        vararg opts: PolygonRestOption
    ) {
        coroutineToRestCallback(callback, { getSnapshots(params, *opts) })
    }

    /**
     * Get an iterator to iterate through all pages of results for the given parameters.
     *
     * See [getQuotesBlocking] if you instead need to get exactly one page of results.
     * See section "Pagination" in the README for more details on iterators.
     */
    @SafeVarargs
    fun listQuotes(
        ticker: String,
        params: QuotesParameters,
        vararg opts: PolygonRestOption
    ): RequestIterator<Quote> = RequestIterator(
        { getQuotesBlocking(ticker, params, *opts) },
        requestIteratorFetch<QuotesResponse>()
    )

    /**
     * Get the simple moving average (SMA) for a ticker symbol over a given time range.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v1_indicators_sma__stockticker
     */
    @SafeVarargs
    fun getTechnicalIndicatorSMABlocking(
        ticker: String,
        params: SMAParameters,
        vararg opts: PolygonRestOption
    ): TechnicalIndicatorsResponse =
        runBlocking { getTechnicalIndicatorSMA(ticker, params, *opts) }

    @SafeVarargs
    fun getTechnicalIndicatorSMA(
        ticker: String,
        params: SMAParameters,
        callback: PolygonRestApiCallback<TechnicalIndicatorsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getTechnicalIndicatorSMA(ticker, params, *opts) })

    /**
     * Get the exponential moving average (EMA) for a ticker symbol over a given time range.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v1_indicators_ema__stockticker
     */
    @SafeVarargs
    fun getTechnicalIndicatorEMABlocking(
        ticker: String,
        params: EMAParameters,
        vararg opts: PolygonRestOption
    ): TechnicalIndicatorsResponse =
        runBlocking { getTechnicalIndicatorEMA(ticker, params, *opts) }

    @SafeVarargs
    fun getTechnicalIndicatorEMA(
        ticker: String,
        params: EMAParameters,
        callback: PolygonRestApiCallback<TechnicalIndicatorsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getTechnicalIndicatorEMA(ticker, params, *opts) })


    /**
     * Get moving average convergence/divergence (MACD) data for a ticker symbol over a given time range.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v1_indicators_macd__stockticker
     */
    @SafeVarargs
    fun getTechnicalIndicatorMACDBlocking(
        ticker: String,
        params: MACDParameters,
        vararg opts: PolygonRestOption
    ): MACDTechnicalIndicatorsResponse =
        runBlocking { getTechnicalIndicatorMACD(ticker, params, *opts) }

    @SafeVarargs
    fun getTechnicalIndicatorMACD(
        ticker: String,
        params: MACDParameters,
        callback: PolygonRestApiCallback<MACDTechnicalIndicatorsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getTechnicalIndicatorMACD(ticker, params, *opts) })


    /**
     * Get the relative strength index (RSI) for a ticker symbol over a given time range.
     *
     * API Doc: https://polygon.io/docs/stocks/get_v1_indicators_rsi__stockticker
     */
    @SafeVarargs
    fun getTechnicalIndicatorRSIBlocking(
        ticker: String,
        params: RSIParameters,
        vararg opts: PolygonRestOption
    ): TechnicalIndicatorsResponse =
        runBlocking { getTechnicalIndicatorRSI(ticker, params, *opts) }

    @SafeVarargs
    fun getTechnicalIndicatorRSI(
        ticker: String,
        params: RSIParameters,
        callback: PolygonRestApiCallback<TechnicalIndicatorsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getTechnicalIndicatorRSI(ticker, params, *opts) })

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

                // Set after options are applied to be sure it doesn't get over-written.
                headers["User-Agent"] = Version.userAgent
            }
        }.body()
    }

    /**
     * Helper function for creating request iterators
     */
    internal inline fun <reified T> requestIteratorFetch(vararg opts: PolygonRestOption): (String) -> T =
        { url -> runBlocking { fetchResult({ takeFrom(url) }, *opts) } }

}
