package io.polygon.kotlin.sdk.sample

import com.tylerthrailkill.helpers.prettyprint.pp
import io.polygon.kotlin.sdk.rest.AggregatesParameters
import io.polygon.kotlin.sdk.rest.GroupedDailyParameters
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.reference.*
import io.polygon.kotlin.sdk.rest.stocks.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.system.exitProcess

suspend fun main() {
    val polygonKey = System.getenv("POLYGON_API_KEY")

    if (polygonKey.isNullOrEmpty()) {
        println("Make sure you set your polygon API key in the POLYGON_API_KEY environment variable!")
        exitProcess(1)
    }

    val polygonClient = PolygonRestClient(polygonKey)

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

    println("\n\n")

    groupedDailiesSample(polygonClient)
}

fun supportedTickersSample(polygonClient: PolygonRestClient) {
    println("3 Supported Tickers:")
    val params = SupportedTickersParameters(
        sortBy = "ticker",
        sortDescending = false,
        market = "FX",
        tickersPerPage = 3
    )

    polygonClient.referenceClient.getSupportedTickersBlocking(params).pp()
}

fun supportedTickerTypes(polygonClient: PolygonRestClient) {
    println("Supported Ticker Types: ")
    polygonClient.referenceClient.getSupportedTickerTypesBlocking().pp()
}

fun supportedLocalesSample(polygonClient: PolygonRestClient) {
    println("Supported Locales:")
    polygonClient.referenceClient.getSupportedLocalesBlocking().pp()
}

fun tickerNewsSample(polygonClient: PolygonRestClient) {
    println("Redfin news:")
    val params = TickerNewsParameters(symbol = "RDFN", resultsPerPage = 2)
    polygonClient.referenceClient.getTickerNewsBlocking(params).pp()
}

fun stockSplitsSample(polygonClient: PolygonRestClient) {
    println("Apple splits:")
    polygonClient.referenceClient.getStockSplitsBlocking("AAPL").pp()
}


fun stockDividendsSample(polygonClient: PolygonRestClient) {
    println("GE dividends:")
    polygonClient.referenceClient.getStockDividendsBlocking("GE").pp()
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

fun supportedExchangesSample(polygonClient: PolygonRestClient) {
    println("Supported stock exchanges: ")
    polygonClient.stocksClient.getSupportedExchangesBlocking().pp()
}

fun historicTradesSample(polygonClient: PolygonRestClient) {
    println("RDFN historic trades: ")
    polygonClient.stocksClient.getHistoricTradesBlocking(HistoricTradesParameters(ticker = "RDFN", date = "2020-02-26")).pp()
}

fun historicQuotesSample(polygonClient: PolygonRestClient) {
    println("RDFN historic quotes: ")
    polygonClient.stocksClient.getHistoricQuotesBlocking(HistoricQuotesParameters(ticker = "RDFN", date = "2020-02-26")).pp()
}

fun lastTradeSample(polygonClient: PolygonRestClient) {
    println("RDFN last trade: ")
    polygonClient.stocksClient.getLastTradeBlocking("RDFN").pp()
}

fun lastQuoteSample(polygonClient: PolygonRestClient) {
    println("RDFN last quote: ")
    polygonClient.stocksClient.getLastQuoteBlocking("RDFN").pp()
}

fun dailyOpenCloseSample(polygonClient: PolygonRestClient) {
    println("RDFN open/close on 2020-02-19")
    polygonClient.stocksClient.getDailyOpenCloseBlocking("RDFN", "2020-02-19").pp()
}

fun conditionsMappingSample(polygonClient: PolygonRestClient) {
    println("Condition mapping:")
    polygonClient.stocksClient.getConditionMappingsBlocking(ConditionMappingTickerType.TRADES).pp()
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