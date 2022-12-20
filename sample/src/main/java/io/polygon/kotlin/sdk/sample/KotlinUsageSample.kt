package io.polygon.kotlin.sdk.sample

import com.tylerthrailkill.helpers.prettyprint.pp
import io.ktor.client.plugins.*
import io.polygon.kotlin.sdk.ComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.DefaultOkHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import io.polygon.kotlin.sdk.rest.*
import io.polygon.kotlin.sdk.rest.crypto.CryptoDailyOpenCloseParameters
import io.polygon.kotlin.sdk.rest.crypto.HistoricCryptoTradesParameters
import io.polygon.kotlin.sdk.rest.forex.HistoricTicksParameters
import io.polygon.kotlin.sdk.rest.forex.RealTimeConversionParameters
import io.polygon.kotlin.sdk.rest.reference.*
import io.polygon.kotlin.sdk.rest.stocks.*
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

    val polygonClient = PolygonRestClient(polygonKey, httpClientProvider = okHttpClientProvider)

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

    splitsSample(polygonClient)

    println("\n\nWebsocket sample:")
    websocketSample(polygonKey)
}

suspend fun websocketSample(polygonKey: String) {
    val websocketClient = PolygonWebSocketClient(
        polygonKey,
        PolygonWebSocketCluster.Crypto,
        object : PolygonWebSocketListener {
            override fun onAuthenticated(client: PolygonWebSocketClient) {
                println("Connected!")
            }

            override fun onReceive(
                client: PolygonWebSocketClient,
                message: PolygonWebSocketMessage
            ) {
                when (message) {
                    is PolygonWebSocketMessage.RawMessage -> println(String(message.data))
                    else -> println("Receieved Message: $message")
                }
            }

            override fun onDisconnect(client: PolygonWebSocketClient) {
                println("Disconnected!")
            }

            override fun onError(client: PolygonWebSocketClient, error: Throwable) {
                println("Error: ")
                error.printStackTrace()
            }

        })

    val subscriptions = listOf(
        PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.Trades, "ETH-USD"),
        PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.Trades, "BTC-USD")
    )

    websocketClient.connect()
    websocketClient.subscribe(subscriptions)
    delay(5000)
    websocketClient.unsubscribe(subscriptions)
    websocketClient.disconnect()
}

fun supportedTickersSample(polygonClient: PolygonRestClient) {
    println("3 Supported Tickers:")
    val params = SupportedTickersParameters(
        sortBy = "ticker",
        sortDescending = false,
        market = "fx",
        limit = 3
    )

    polygonClient.referenceClient.getSupportedTickersBlocking(params).pp()
}

fun tickerTypesSample(polygonClient: PolygonRestClient) {
    println("Ticker Types: ")
    polygonClient.referenceClient.getTickerTypesBlocking(TickerTypesParameters()).pp()
}

fun supportedLocalesSample(polygonClient: PolygonRestClient) {
    println("Supported Locales:")
    polygonClient.referenceClient.getSupportedLocalesBlocking().pp()
}

fun tickerNewsSample(polygonClient: PolygonRestClient) {
    println("Redfin news:")
    val params = TickerNewsParametersV2(ticker = ComparisonQueryFilterParameters.equal("RDFN"), limit = 2)
    polygonClient.referenceClient.getTickerNewsBlockingV2(params).pp()
}

fun splitsSample(polygonClient: PolygonRestClient) {
    println("Apple splits:")
    polygonClient.referenceClient
        .getSplitsBlocking(SplitsParameters(ticker = ComparisonQueryFilterParameters.equal("AAPL")))
        .pp()
}


fun dividendsSample(polygonClient: PolygonRestClient) {
    println("GE dividends:")
    polygonClient.referenceClient.getDividendsBlocking(
        DividendsParameters(
            ticker = ComparisonQueryFilterParameters.equal("GE"),
            limit = 1,
        )
    ).pp()

    println("dividends w/ cash amounts between $1 and $10")
    polygonClient.referenceClient.getDividendsBlocking(
        DividendsParameters(
            cashAmount = ComparisonQueryFilterParameters(greaterThanOrEqual = 1.0, lessThanOrEqual = 10.0),
            limit = 1,
        )
    ).pp()
}

fun stockFinancialsSample(polygonClient: PolygonRestClient) {
    println("RDFN financials")
    polygonClient.referenceClient.getStockFinancialsBlocking(StockFinancialsParameters(symbol = "RDFN", limit = 1)).pp()
}

fun marketStatusesSample(polygonClient: PolygonRestClient) {
    println("Market status:")
    polygonClient.referenceClient.getMarketStatusBlocking().pp()
}

fun marketHolidaysSample(polygonClient: PolygonRestClient) {
    println("Market holidays:")
    polygonClient.referenceClient.getMarketHolidaysBlocking().pp()
}

fun exchangesSample(polygonClient: PolygonRestClient) {
    println("Supported stock exchanges: ")
    polygonClient.referenceClient.getExchangesBlocking(ExchangesParameters(assetClass = "stocks")).pp()
}

fun historicTradesSample(polygonClient: PolygonRestClient) {
    println("RDFN historic trades: ")
    polygonClient.stocksClient.getHistoricTradesBlocking(HistoricTradesParameters(ticker = "RDFN", date = "2020-02-26"))
        .pp()
}

fun historicQuotesSample(polygonClient: PolygonRestClient) {
    println("RDFN historic quotes: ")
    polygonClient.stocksClient.getHistoricQuotesBlocking(HistoricQuotesParameters(ticker = "RDFN", date = "2020-02-26"))
        .pp()
}

fun lastTradeSample(polygonClient: PolygonRestClient) {
    println("Last F Trade")
    polygonClient.stocksClient.getLastTradeBlockingV2("F").pp()
}

fun lastQuoteSample(polygonClient: PolygonRestClient) {
    println("F last quote: ")
    polygonClient.stocksClient.getLastQuoteBlockingV2("F").pp()
}

fun dailyOpenCloseSample(polygonClient: PolygonRestClient) {
    println("RDFN open/close on 2020-02-19")
    polygonClient.stocksClient.getDailyOpenCloseBlocking("RDFN", "2020-02-19", true).pp()
}

fun conditionsSample(polygonClient: PolygonRestClient) {
    println("Conditions:")
    polygonClient.referenceClient.getConditionsBlocking(ConditionsParameters()).pp()
}

fun snapshotAllTickersSample(polygonClient: PolygonRestClient) {
    println("All tickers snapshot: ")
    polygonClient.stocksClient.getSnapshotAllTickersBlocking().pp()
}

fun snapshotSingleTickerSample(polygonClient: PolygonRestClient) {
    println("RDFN snapshot:")
    polygonClient.stocksClient.getSnapshotBlocking("RDFN").pp()
}

fun snapshotGainersSample(polygonClient: PolygonRestClient) {
    println("Today's gainers:")
    polygonClient.stocksClient.getSnapshotGainersOrLosersBlocking(GainersOrLosersDirection.GAINERS).pp()
}

fun previousCloseSample(polygonClient: PolygonRestClient) {
    println("RDFN Prev close:")
    polygonClient.stocksClient.getPreviousCloseBlocking("RDFN", true).pp()
}

fun aggregatesSample(polygonClient: PolygonRestClient) {
    println("RDFN Aggs")
    val params = AggregatesParameters(
        ticker = "RDFN",
        timespan = "day",
        fromDate = "2020-02-17",
        toDate = "2020-02-20"
    )

    polygonClient.getAggregatesBlocking(params).pp()
}

fun groupedDailiesSample(polygonClient: PolygonRestClient) {
    println("Grouped dailies for 2020-02-20")
    val params = GroupedDailyParameters(
        locale = "us",
        market = "stocks",
        date = "2020-02-20"
    )

    polygonClient.getGroupedDailyAggregatesBlocking(params).pp()
}

fun historicForexSample(polygonClient: PolygonRestClient) {
    println("Historic ticks for USD/EUR on 2020-02-20")
    val params = HistoricTicksParameters(
        fromCurrency = "USD",
        toCurrency = "EUR",
        date = "2020-02-20",
        limit = 10
    )

    polygonClient.forexClient.getHistoricTicksBlocking(params).pp()
}

fun realTimeConversionSample(polygonClient: PolygonRestClient) {
    println("Converting $100 to EUR100")
    val params = RealTimeConversionParameters(
        fromCurrency = "USD",
        toCurrency = "EUR",
        amount = 100.0,
        precision = 3
    )

    polygonClient.forexClient.getRealTimeConversionBlocking(params).pp()
}

fun lastQuoteForexSample(polygonClient: PolygonRestClient) {
    println("Last quote for USD/EUR")
    polygonClient.forexClient.getLastQuoteBlocking("USD", "EUR").pp()
}

fun forexSnapshotSample(polygonClient: PolygonRestClient) {
    println("Forex snapshot:")
    polygonClient.forexClient.getSnapshotAllTickersBlocking().pp()
}

fun forexGainersOrLosersSample(polygonClient: PolygonRestClient) {
    println("Forex gainers:")
    polygonClient.forexClient.getSnapshotGainersOrLosersBlocking(GainersOrLosersDirection.GAINERS).pp()
}

fun cryptoLastTradeSample(polygonClient: PolygonRestClient) {
    println("Last BTC/USD trade")
    polygonClient.cryptoClient.getLastTradeBlocking("BTC", "USD").pp()
}

fun cryptoDailyOpenCloseSample(polygonClient: PolygonRestClient) {
    println("BTC open/close on 2020-02-20")
    val params = CryptoDailyOpenCloseParameters(
        from = "BTC", to = "USD", date = "2020-02-20"
    )

    polygonClient.cryptoClient.getDailyOpenCloseBlocking(params).pp()
}

fun historicCryptoTradesSample(polygonClient: PolygonRestClient) {
    println("10 BTC-USD trades on 2020-02-20")
    val params = HistoricCryptoTradesParameters(
        from = "BTC", to = "USD", date = "2020-02-20", limit = 10
    )

    polygonClient.cryptoClient.getHistoricTradesBlocking(params).pp()
}

fun cryptoAllTickersSample(polygonClient: PolygonRestClient) {
    println("All crypto snapshot:")
    polygonClient.cryptoClient.getSnapshotAllTickersBlocking().pp()
}

fun cryptoSingleTickerSnapshotSample(polygonClient: PolygonRestClient) {
    println("Snapshot for X:BTCUSD")
    polygonClient.cryptoClient.getSnapshotSingleTickerBlocking("X:BTCUSD").pp()
}

fun cryptoGainersOrLosersSample(polygonClient: PolygonRestClient) {
    println("Today's crypto losers: ")
    polygonClient.cryptoClient.getSnapshotGainersOrLosersBlocking(GainersOrLosersDirection.LOSERS).pp()
}

fun cryptoL2SnapshotSample(polygonClient: PolygonRestClient) {
    println("X:BTCUSD L2 data:")
    polygonClient.cryptoClient.getL2SnapshotSingleTickerBlocking("X:BTCUSD").pp()
}

fun getTradesSample(polygonClient: PolygonRestClient) {
    println("F Trades:")
    var params = TradesParameters(
        ticker = "F",
        limit = 2
    )
    polygonClient.getTradesBlocking(params).pp()
}

fun getQuotesSample(polygonClient: PolygonRestClient) {
    println("F Quotes")
    var params = QuotesParameters(
        ticker = "F",
        limit = 2
    )
    polygonClient.getQuotesBlocking(params).pp()

}
