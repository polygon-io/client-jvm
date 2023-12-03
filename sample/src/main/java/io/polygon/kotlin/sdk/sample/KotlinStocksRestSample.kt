package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.rest.*
import io.polygon.kotlin.sdk.rest.reference.*
import io.polygon.kotlin.sdk.rest.stocks.GainersOrLosersDirection
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import com.tylerthrailkill.helpers.prettyprint.pp

// Stocks Aggregates Bars
// https://polygon.io/docs/stocks/get_v2_aggs_ticker__stocksticker__range__multiplier___timespan___from___to
fun stocksAggregatesBars(polygonClient: PolygonRestClient) {
    println("RDFN Aggs")
    val params = AggregatesParameters(
        ticker = "RDFN",
        timespan = "day",
        fromDate = "2023-07-03",
        toDate = "2023-07-07",
        sort = "asc",
        limit = 50_000,
    )
    polygonClient.getAggregatesBlocking(params).pp()
}

// Stocks Conditions
// https://polygon.io/docs/stocks/get_v3_reference_conditions
fun stocksConditions(polygonClient: PolygonRestClient) {
    println("Conditions:")
    polygonClient.referenceClient.getConditionsBlocking(ConditionsParameters()).pp()
    polygonClient.referenceClient.listConditions(ConditionsParameters(limit = 5))
        .asSequence().take(15).forEach { println("got condition #${it.id}") }
}

// Stocks Daily Open Close
// https://polygon.io/docs/stocks/get_v1_open-close__stocksticker___date
fun stocksDailyOpenClose(polygonClient: PolygonRestClient) {
    println("AAPL open/close on 2023-01-09")
    polygonClient.stocksClient.getDailyOpenCloseBlocking("AAPL", "2023-01-09", true).pp()
}

// Stocks Dividends
// https://polygon.io/docs/stocks/get_v3_reference_dividends
fun stocksDividends(polygonClient: PolygonRestClient) {

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

    println("15 most recent dividends")
    polygonClient.referenceClient.listDividends(DividendsParameters(limit = 5))
        .asSequence().take(15).forEach { println("got a dividend from ${it.exDividendDate}") }


}

// Stocks Exchanges
// https://polygon.io/docs/stocks/get_v3_reference_exchanges
fun stocksExchanges(polygonClient: PolygonRestClient) {
    println("Supported stock exchanges: ")
    polygonClient.referenceClient.getExchangesBlocking(ExchangesParameters(assetClass = "stocks")).pp()
}

// Stocks Grouped Daily Bars
// https://polygon.io/docs/stocks/get_v2_aggs_grouped_locale_us_market_stocks__date
fun stocksGroupedDailyBars(polygonClient: PolygonRestClient) {
    println("Grouped dailies for 2023-02-16")
    val params = GroupedDailyParameters(
        locale = "us",
        market = "stocks",
        date = "2023-02-16"
    )

    polygonClient.getGroupedDailyAggregatesBlocking(params).pp()
}

// Stocks Last Quote
// https://polygon.io/docs/stocks/get_v2_last_nbbo__stocksticker
fun stocksLastQuote(polygonClient: PolygonRestClient) {
    println("Last AAPL quote: ")
    polygonClient.stocksClient.getLastQuoteBlockingV2("AAPL").pp()
}

// Stocks Last Trade
// https://polygon.io/docs/stocks/get_v2_last_trade__stocksticker
fun stocksLastTrade(polygonClient: PolygonRestClient) {
    println("Last AAPL trade: ")
    polygonClient.stocksClient.getLastTradeBlockingV2("AAPL").pp()
}

// Stocks Market Holidays
// https://polygon.io/docs/stocks/get_v1_marketstatus_upcoming
fun stocksMarketHolidays(polygonClient: PolygonRestClient) {
	println("Market holidays:")
    polygonClient.referenceClient.getMarketHolidaysBlocking().pp()
}

// Stocks Market Status
// https://polygon.io/docs/stocks/get_v1_marketstatus_now
fun stocksMarketStatus(polygonClient: PolygonRestClient) {
    println("Market status:")
    polygonClient.referenceClient.getMarketStatusBlocking().pp()
}

// Stocks Previous Close
// https://polygon.io/docs/stocks/get_v2_aggs_ticker__stocksticker__prev
fun stocksPreviousClose(polygonClient: PolygonRestClient) {
	println("AAPL Previous Close:")
    polygonClient.stocksClient.getPreviousCloseBlocking("AAPL", true).pp()
}

// Stocks Quotes
// https://polygon.io/docs/stocks/get_v3_quotes__stockticker
fun stocksQuotes(polygonClient: PolygonRestClient) {
    println("IBIO Quotes")
    val params = QuotesParameters(timestamp = ComparisonQueryFilterParameters.equal("2023-02-01"), limit = 2)
    polygonClient.getQuotesBlocking("IBIO", params).pp()
}

// Stocks Snapshots All
// https://polygon.io/docs/stocks/get_v2_snapshot_locale_us_markets_stocks_tickers
fun stocksSnapshotsAll(polygonClient: PolygonRestClient) {
    println("All tickers snapshot: ")
    polygonClient.stocksClient.getSnapshotAllTickersBlocking().pp()
}

// Stocks Snapshots Gainers Losers
// https://polygon.io/docs/stocks/get_v2_snapshot_locale_us_markets_stocks__direction
fun stocksSnapshotsGainersLosers(polygonClient: PolygonRestClient) {
    println("Today's gainers:")
    polygonClient.stocksClient.getSnapshotGainersOrLosersBlocking(GainersOrLosersDirection.GAINERS).pp()

    println("Today's losers:")
    polygonClient.stocksClient.getSnapshotGainersOrLosersBlocking(GainersOrLosersDirection.LOSERS).pp()
}

// Stocks Snapshots Ticker
// https://polygon.io/docs/stocks/get_v2_snapshot_locale_us_markets_stocks_tickers__stocksticker
fun stocksSnapshotsTicker(polygonClient: PolygonRestClient) {
    println("AAPL snapshot:")
    polygonClient.stocksClient.getSnapshotBlocking("AAPL").pp()
}

// Stocks Stock Financials Function
fun stocksStockFinancials(polygonClient: PolygonRestClient) {
}

// Stocks Stock Splits
// https://polygon.io/docs/stocks/get_v3_reference_splits
fun stocksStockSplits(polygonClient: PolygonRestClient) {

    println("Apple splits:")
    polygonClient.referenceClient
        .getSplitsBlocking(SplitsParameters(ticker = ComparisonQueryFilterParameters.equal("AAPL")))
        .pp()

    println("list recent splits:")
    polygonClient.referenceClient.listSplits(SplitsParameters(limit = 5))
        .asSequence()
        .take(15).forEach { println("split from ${it.executionDate}") }

}

// Stocks Technical Indicators EMA
// https://polygon.io/docs/stocks/get_v1_indicators_ema__stockticker
fun stocksTechnicalIndicatorsEMA(polygonClient: PolygonRestClient) {
    println("F EMA:")
    polygonClient.getTechnicalIndicatorEMABlocking("F", EMAParameters()).pp()
}

// Stocks Technical Indicators MACD
// https://polygon.io/docs/stocks/get_v1_indicators_macd__stockticker
fun stocksTechnicalIndicatorsMACD(polygonClient: PolygonRestClient) {
    println("F MACD:")
    polygonClient.getTechnicalIndicatorMACDBlocking("F", MACDParameters()).pp()
}

// Stocks Technical Indicators RSI
// https://polygon.io/docs/stocks/get_v1_indicators_rsi__stockticker
fun stocksTechnicalIndicatorsRSI(polygonClient: PolygonRestClient) {
    println("F RSI:")
    polygonClient.getTechnicalIndicatorRSIBlocking("F", RSIParameters()).pp()
}

// Stocks Technical Indicators SMA
// https://polygon.io/docs/stocks/get_v1_indicators_sma__stockticker
fun stocksTechnicalIndicatorsSMA(polygonClient: PolygonRestClient) {
    println("F SMA:")
    polygonClient.getTechnicalIndicatorSMABlocking("F", SMAParameters()).pp()
}

// Stocks Ticker Details
// https://polygon.io/docs/stocks/get_v3_reference_tickers__ticker
fun stocksTickerDetails(polygonClient: PolygonRestClient) {
	println("Ticker Details: ")
    polygonClient.referenceClient.getTickerDetailsV3Blocking("AAPL", TickerDetailsParameters()).pp()
}

// Stocks Ticker Events (experimental)
// https://polygon.io/docs/stocks/get_vx_reference_tickers__id__events
//fun stocksTickerEvents(polygonClient: PolygonRestClient) {
//    println("META events:")
//    polygonClient.experimentalClient.getTickerEventsBlocking("META").pp()
//}

// Stocks Ticker News
// https://polygon.io/docs/stocks/get_v2_reference_news
fun stocksTickerNews(polygonClient: PolygonRestClient) {
    println("BBBY news:")
    val params = TickerNewsParametersV2(ticker = ComparisonQueryFilterParameters.equal("BBBY"), limit = 2)
    polygonClient.referenceClient.getTickerNewsBlockingV2(params).pp()

    println("List news:")
    polygonClient.referenceClient.listTickerNewsV2(params)
        .asSequence().take(10).forEach { println("news article: ${it.articleUrl}") }
}

// Stocks Ticker Types
// https://polygon.io/docs/stocks/get_v3_reference_tickers_types
fun stocksTickerTypes(polygonClient: PolygonRestClient) {
	println("Ticker Types: ")
    polygonClient.referenceClient.getTickerTypesBlocking(TickerTypesParameters()).pp()
}

// Stocks Tickers
// https://polygon.io/docs/stocks/get_v3_reference_tickers
fun stocksTickers(polygonClient: PolygonRestClient) {
    println("Tickers:")
    val params = SupportedTickersParameters(
        sortBy = "ticker",
        sortDescending = false,
        market = "stocks",
        limit = 1000
    )

    polygonClient.referenceClient.getSupportedTickersBlocking(params).pp()
}

// Stocks Trades
// https://polygon.io/docs/stocks/get_v3_trades__stockticker
fun stocksTrades(polygonClient: PolygonRestClient) {
    println("IBIO Trades:")
    val params = TradesParameters(timestamp = ComparisonQueryFilterParameters.equal("2023-02-01"), limit = 2)
    polygonClient.getTradesBlocking("IBIO", params).pp()
}
