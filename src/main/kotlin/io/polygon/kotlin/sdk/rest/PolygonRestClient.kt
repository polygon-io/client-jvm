package io.polygon.kotlin.sdk.rest

import io.ktor.client.HttpClient
import io.ktor.client.request.*
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
): AutoCloseable {

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
     * Lazy delegate used for instantiating the internal [HttpClient].
     * Needed for providing NOOP close semantics if the httpClient wasn't instantiated.
     */
    val httpClientDelegate = lazy { httpClientProvider.buildClient() }

    /**
     * A [HttpClient] used to submit all API requests.
     */
    val httpClient by httpClientDelegate

    /**
     * Get aggregates for a date range, in custom time window sizes.
     *
     * Some tickers require a prefix, see examples below:
     *
     * Stocks: AAPL
     * Currencies: C:EURUSD
     * Crypto: X:BTCUSD
     *
     * API Doc: https://polygon.io/docs/#get_v2_aggs_ticker__ticker__range__multiplier___timespan___from___to__anchor
     */
    fun getAggregatesBlocking(params: AggregatesParameters): AggregatesDTO =
        runBlocking { getAggregates(params) }

    /** See [getAggregatesBlocking] */
    fun getAggregates(params: AggregatesParameters, callback: PolygonRestApiCallback<AggregatesDTO>) =
        coroutineToRestCallback(callback, { getAggregates(params) })

    /**
     * Get the daily OHLC for entire markets.
     * The response size is large.
     *
     * API Doc: https://polygon.io/docs/#get_v2_aggs_grouped_locale__locale__market__market___date__anchor
     */
    fun getGroupedDailyAggregatesBlocking(params: GroupedDailyParameters): AggregatesDTO =
        runBlocking { getGroupedDailyAggregates(params) }

    /** See [getGroupedDailyAggregatesBlocking] */
    fun getGroupedDailyAggregates(params: GroupedDailyParameters, callback: PolygonRestApiCallback<AggregatesDTO>) =
        coroutineToRestCallback(callback, { getGroupedDailyAggregates(params) })


    private val baseUrlBuilder: URLBuilder
        get() = httpClientProvider.getDefaultRestURLBuilder().apply {
            host = polygonApiDomain
            parameters["apiKey"] = apiKey
        }

    internal suspend inline fun <reified T> fetchResult(
        urlBuilderBlock: URLBuilder.() -> Unit
    ): T {
        val url = baseUrlBuilder.apply(urlBuilderBlock).build()
        return httpClient.get(url)
    }

    override fun close() {
        if (httpClientDelegate.isInitialized()) {
            httpClient.close()
        }
    }

}
