package io.polygon.kotlin.sdk.sample

import com.tylerthrailkill.helpers.prettyprint.pp
import io.ktor.client.plugins.*
import io.polygon.kotlin.sdk.DefaultOkHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import io.polygon.kotlin.sdk.rest.*
import io.polygon.kotlin.sdk.rest.crypto.CryptoDailyOpenCloseParameters
import io.polygon.kotlin.sdk.rest.experimental.ExperimentalAPI
import io.polygon.kotlin.sdk.rest.experimental.FinancialsParameters
import io.polygon.kotlin.sdk.rest.forex.RealTimeConversionParameters
import io.polygon.kotlin.sdk.rest.indices.SnapshotIndicesParameters
import io.polygon.kotlin.sdk.rest.options.SnapshotChainParameters
import io.polygon.kotlin.sdk.rest.reference.*
import io.polygon.kotlin.sdk.rest.stocks.GainersOrLosersDirection
import io.polygon.kotlin.sdk.websocket.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.system.exitProcess

val okHttpClientProvider: HttpClientProvider
    get() = DefaultOkHttpClientProvider(
        applicationInterceptors = listOf(object : okhttp3.Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                println("Intercepting application level")
                println("request: ${chain.request().url}")
                return chain.proceed(chain.request())
            }
        }),
        networkInterceptors = listOf(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                println("Intercepting network level")
                return chain.proceed(chain.request())
            }
        })
    )

suspend fun main() {
    val polygonKey = System.getenv("POLYGON_API_KEY")

    if (polygonKey.isNullOrEmpty()) {
        println("Make sure you set your polygon API key in the POLYGON_API_KEY environment variable!")
        exitProcess(1)
    }

    val polygonClient = PolygonRestClient(
    	polygonKey, 
    	httpClientProvider = okHttpClientProvider
    )


	// Stocks section
	// Access to stocks data depends on your entitlements

	// Stocks function calls
	//stocksAggregatesBars(polygonClient)
	//stocksConditions(polygonClient)
	//stocksDailyOpenClose(polygonClient)
	//stocksDividends(polygonClient)
	//stocksExchanges(polygonClient)
	//stocksGroupedDailyBars(polygonClient)
	//stocksLastQuote(polygonClient)
	//stocksLastTrade(polygonClient)
	//stocksMarketHolidays(polygonClient)
	//stocksMarketStatus(polygonClient)
	//stocksPreviousClose(polygonClient)
	//stocksQuotes(polygonClient)
	//stocksSnapshotsAll(polygonClient)
	//stocksSnapshotsGainersLosers(polygonClient)
	//stocksSnapshotsTicker(polygonClient)
	//stocksStockFinancials(polygonClient) // not working yet
	//stocksStockSplits(polygonClient)
	//stocksTechnicalIndicatorsEMA(polygonClient)
	//stocksTechnicalIndicatorsMACD(polygonClient)
	//stocksTechnicalIndicatorsRSI(polygonClient)
	//stocksTechnicalIndicatorsSMA(polygonClient)
	//stocksTickerDetails(polygonClient)
	//stocksTickerEvents(polygonClient)
	//stocksTickerNews(polygonClient)
	//stocksTickerTypes(polygonClient)
	//stocksTickers(polygonClient)
	//stocksTrades(polygonClient)

	//universalSnapshot(polygonClient)

	// Stocks websocket sample
	//stocksWebsocketSample(polygonKey)


	// Options section
	// Access to options data depends on your entitlements

    // Options function calls
	//optionsAggregatesBars(polygonClient)
	//optionsConditions(polygonClient)
	//optionsContract(polygonClient)
	//optionsContracts(polygonClient)
	//optionsDailyOpenClose(polygonClient)
	//optionsExchanges(polygonClient)
	//optionsLastTrade(polygonClient)
	//optionsMarketHolidays(polygonClient)
	//optionsMarketStatus(polygonClient)
	//optionsPreviousClose(polygonClient)
	//optionsQuotes(polygonClient)
	//optionsSnapshotsOptionContract(polygonClient)
	//optionsSnapshotsOptionsChain(polygonClient)
	//optionsTechnicalIndicatorsEMA(polygonClient)
	//optionsTechnicalIndicatorsMACD(polygonClient)
	//optionsTechnicalIndicatorsRSI(polygonClient)
	//optionsTechnicalIndicatorsSMA(polygonClient)
	//optionsTickerDetails(polygonClient)
	//optionsTickerNews(polygonClient)
	//optionsTickers(polygonClient)
	//optionsTrades(polygonClient)

    // Options websocket sample
	//optionsWebsocketSample(polygonKey) // not implemented yet

	// Indices section
	// Access to indices data depends on your entitlements

    // Indices function calls
	//indicesAggregatesBars(polygonClient)
	//indicesDailyOpenClose(polygonClient)
	//indicesMarketHolidays(polygonClient)
	//indicesMarketStatus(polygonClient)
	//indicesPreviousClose(polygonClient)
	//indicesSnapshots(polygonClient)
	//indicesTechnicalIndicatorsEMA(polygonClient)
	//indicesTechnicalIndicatorsMACD(polygonClient)
	//indicesTechnicalIndicatorsRSI(polygonClient)
	//indicesTechnicalIndicatorsSMA(polygonClient)
	//indicesTickerTypes(polygonClient)
	//indicesTickers(polygonClient)

    // Indices websocket sample
	//indicesWebsocketSample(polygonKey)

	// Forex section
	// Access to forex data depends on your entitlements

    // Forex function calls
	//forexAggregatesBars(polygonClient)
	//forexConditions(polygonClient)
	//forexExchanges(polygonClient)
	//forexGroupedDailyBars(polygonClient)
	//forexLastQuoteForCurrencyPair(polygonClient)
	//forexMarketHolidays(polygonClient)
	//forexMarketStatus(polygonClient)
	//forexPreviousClose(polygonClient)
	//forexQuotes(polygonClient)
	//forexRealTimeCurrencyConversion(polygonClient)
	//forexSnapshotsAllTickers(polygonClient)
	//forexSnapshotsGainersLosers(polygonClient)
	//forexSnapshotsTicker(polygonClient) // not working yes
	//forexTechnicalIndicatorsEMA(polygonClient)
	//forexTechnicalIndicatorsMACD(polygonClient)
	//forexTechnicalIndicatorsRSI(polygonClient)
	//forexTechnicalIndicatorsSMA(polygonClient)
	//forexTickers(polygonClient)

    // Forex websocket sample
	//forexWebsocketSample(polygonKey)

	// Crypto section
	// Access to crypto data depends on your entitlements

    // Crypto function calls
	//cryptoAggregatesBars(polygonClient)
	//cryptoConditions(polygonClient)
	//cryptoDailyOpenClose(polygonClient)
	//cryptoExchanges(polygonClient)
	//cryptoGroupedDailyBars(polygonClient)
	//cryptoLastTradeForCryptoPair(polygonClient)
	//cryptoMarketHolidays(polygonClient)
	//cryptoMarketStatus(polygonClient)
	//cryptoPreviousClose(polygonClient)
	//cryptoSnapshotsAllTickers(polygonClient)
	//cryptoSnapshotsGainersLosers(polygonClient)
	//cryptoSnapshotsTicker(polygonClient)
	//cryptoSnapshotsTickerFullBookL2(polygonClient)
	//cryptoTechnicalIndicatorsEMA(polygonClient)
	//cryptoTechnicalIndicatorsMACD(polygonClient)
	//cryptoTechnicalIndicatorsRSI(polygonClient)
	//cryptoTechnicalIndicatorsSMA(polygonClient)
	//cryptoTickers(polygonClient)
	//cryptoTrades(polygonClient)

    // Crypto websocket sample
	//cryptoWebsocketSample(polygonKey)

	// launchpad
	//launchpadWebsocketSample(polygonKey)


/*

    println("Blocking for markets...")
    val markets = polygonClient.referenceClient.getSupportedMarketsBlocking()
    println("Got markets synchronously: $markets")

    println("Getting markets asynchronously...")
    val deferred = GlobalScope.async {
        val asyncMarkets = polygonClient.referenceClient.getSupportedMarkets()
        println("Got markets asynchronously: $asyncMarkets")
    }

    deferred.await()
    println("Done getting markets asynchronously!")

    println("Using options")
    val groupedDaily = polygonClient.getGroupedDailyAggregates(
        GroupedDailyParameters("us", "stocks", "2022-12-08"),
        PolygonRestOptions.withTimeout(10_000), // Custom timeout for this request
        PolygonRestOptions.withQueryParam("additional-param", "additional-value"), // Additional query parameter
        PolygonRestOptions.withHeader("X-Custom-Header", "custom-value"), // Custom header for this request
        { this.expectSuccess = true }, // Example of an arbitrary option that doesn't use a helper function
    )

    println("Got ${groupedDaily.results.size} results from grouped daily")

    iteratorExample(polygonClient)
    tradesIteratorExample(polygonClient)
    quotesIteratorExample(polygonClient)

    financialsSample(polygonClient)

    indicesSample(polygonClient)

    println("\n\nWebsocket sample:")
    websocketSample(polygonKey)

*/

}

@OptIn(ExperimentalAPI::class)
fun financialsSample(polygonClient: PolygonRestClient) {
    println("RDFN financials:")

    polygonClient.experimentalClient.getFinancialsBlocking(FinancialsParameters(ticker = "RDFN")).pp()
    polygonClient.experimentalClient.listFinancials(FinancialsParameters(limit = 5))
        .asSequence()
        .take(15)
        .forEach { println("got financials from ${it.sourceFilingURL}") }
}

fun snapshotAllTickersSample(polygonClient: PolygonRestClient) {
    println("All tickers snapshot: ")
    polygonClient.stocksClient.getSnapshotAllTickersBlocking().pp()
}

fun snapshotSingleTickerSample(polygonClient: PolygonRestClient) {
    println("RDFN snapshot:")
    polygonClient.stocksClient.getSnapshotBlocking("RDFN").pp()
}


fun universalSnapshot(polygonClient: PolygonRestClient) {
    println("Universal Snapshot:")
    polygonClient.referenceClient.getUniversalSnapshotBlocking(UniversalSnapshotParameters(tickers = listOf("NCLH", "O:SPY250321C00380000", "C:EURUSD", "X:BTCUSD", "I:SPX"), limit = 50)).pp()

}



