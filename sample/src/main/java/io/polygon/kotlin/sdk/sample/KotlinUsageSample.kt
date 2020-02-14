package io.polygon.kotlin.sdk.sample

import com.tylerthrailkill.helpers.prettyprint.pp
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParameters
import io.polygon.kotlin.sdk.rest.reference.TickerNewsParameters
import io.polygon.kotlin.sdk.rest.reference.getSupportedMarkets
import io.polygon.kotlin.sdk.rest.reference.getTickerNews
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
    tickerNewsSample(polygonClient)
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