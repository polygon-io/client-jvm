package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.QuotesParameters
import io.polygon.kotlin.sdk.rest.TradesParameters
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParameters

fun iteratorExample(polygonClient: PolygonRestClient) {
    println("10 Supported Tickers:")
    val params = SupportedTickersParameters(
        sortBy = "ticker",
        sortDescending = false,
        market = "stocks",
        limit = 3
    )

    // Take only the first 10 so this sample ends quickly.
    // Note that since the limit = 3 in the params,
    // this iterator will make 4 requests under the hood
    polygonClient.referenceClient.listSupportedTickers(params).asSequence()
        .take(10)
        .forEachIndexed { index, tickerDTO -> println("${index}: ${tickerDTO.ticker}") }
}

fun tradesIteratorExample(polygonClient: PolygonRestClient) {
    println("Running trade iterator:")
    val params = TradesParameters(limit = 1)

    polygonClient.listTrades("F", params).asSequence()
        .take(2)
        .forEachIndexed { index, tradeRes -> println("${index}: ${tradeRes.price}") }
}

fun quotesIteratorExample(polygonClient: PolygonRestClient) {
    println("Running quote iterator:")
    val params = QuotesParameters(limit = 1)

    polygonClient.listQuotes("F", params).asSequence()
        .take(2)
        .forEachIndexed { index, quoteRes -> println("${index}: (${quoteRes.participantTimestamp}) | ${quoteRes.bidPrice} / ${quoteRes.askPrice}") }
}