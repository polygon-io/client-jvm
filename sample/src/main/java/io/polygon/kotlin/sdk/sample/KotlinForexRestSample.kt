package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.rest.*
import io.polygon.kotlin.sdk.rest.reference.*
import io.polygon.kotlin.sdk.rest.stocks.GainersOrLosersDirection
import io.polygon.kotlin.sdk.rest.forex.RealTimeConversionParameters
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import com.tylerthrailkill.helpers.prettyprint.pp

// Forex Aggregates Bars
// https://polygon.io/docs/forex/get_v2_aggs_ticker__forexticker__range__multiplier___timespan___from___to
fun forexAggregatesBars(polygonClient: PolygonRestClient) {
    println("C:EURUSD Aggs")
    val params = AggregatesParameters(
        ticker = "C:EURUSD",
        timespan = "day",
        fromDate = "2023-01-30",
        toDate = "2023-02-03",
        sort = "asc",
        limit = 50_000
    )

    polygonClient.getAggregatesBlocking(params).pp()
}

// Forex Conditions
// https://polygon.io/docs/forex/get_v3_reference_conditions
fun forexConditions(polygonClient: PolygonRestClient) {
    println("Conditions:")
    polygonClient.referenceClient.getConditionsBlocking(ConditionsParameters(assetClass = "fx")).pp()
    polygonClient.referenceClient.listConditions(ConditionsParameters(assetClass = "fx", limit = 5))
        .asSequence().take(15).forEach { println("got condition #${it.id}") }
}

// Forex Exchanges
// https://polygon.io/docs/forex/get_v3_reference_exchanges
fun forexExchanges(polygonClient: PolygonRestClient) {
    println("Supported stock exchanges: ")
    polygonClient.referenceClient.getExchangesBlocking(ExchangesParameters(assetClass = "fx")).pp()
}

// Forex Grouped Daily Bars
// https://polygon.io/docs/forex/get_v2_aggs_grouped_locale_global_market_fx__date
fun forexGroupedDailyBars(polygonClient: PolygonRestClient) {
    println("Grouped dailies for 2023-03-27")
    val params = GroupedDailyParameters(
        locale = "global",
        market = "fx",
        date = "2023-03-27"
    )

    polygonClient.getGroupedDailyAggregatesBlocking(params).pp()
}

// Forex Last Quote For A Currency Pair
// https://polygon.io/docs/forex/get_v1_last_quote_currencies__from___to
fun forexLastQuoteForCurrencyPair(polygonClient: PolygonRestClient) {
    println("Last quote for USD/EUR")
    polygonClient.forexClient.getLastQuoteBlocking("USD", "EUR").pp()
}

// Forex Market Holidays
// https://polygon.io/docs/forex/get_v1_marketstatus_upcoming
fun forexMarketHolidays(polygonClient: PolygonRestClient) {
	println("Market holidays:")
    polygonClient.referenceClient.getMarketHolidaysBlocking().pp()	
}

// Forex Market Status
// https://polygon.io/docs/forex/get_v1_marketstatus_now
fun forexMarketStatus(polygonClient: PolygonRestClient) {
    println("Market status:")
    polygonClient.referenceClient.getMarketStatusBlocking().pp()
}

// Forex Previous Close
// https://polygon.io/docs/forex/get_v2_aggs_ticker__forexticker__prev
fun forexPreviousClose(polygonClient: PolygonRestClient) {
    println("C:EURUSD Previous Close:")
    polygonClient.stocksClient.getPreviousCloseBlocking("C:EURUSD", true).pp()
}

// Forex Quotes
// https://polygon.io/docs/forex/get_v3_quotes__fxticker
fun forexQuotes(polygonClient: PolygonRestClient) {
    println("C:EUR-USD Quotes")
    val params = QuotesParameters(timestamp = ComparisonQueryFilterParameters.equal("2023-02-01"), limit = 2)
    polygonClient.getQuotesBlocking("C:EUR-USD", params).pp()
}

// Forex Real Time Currency Conversion
// https://polygon.io/docs/forex/get_v1_conversion__from___to
fun forexRealTimeCurrencyConversion(polygonClient: PolygonRestClient) {
    println("Converting $100 USD to EUR")
    val params = RealTimeConversionParameters(
        fromCurrency = "USD",
        toCurrency = "EUR",
        amount = 100.0,
        precision = 3
    )

    polygonClient.forexClient.getRealTimeConversionBlocking(params).pp()
}

// Forex Snapshots All Tickers
// https://polygon.io/docs/forex/get_v2_snapshot_locale_global_markets_forex_tickers
fun forexSnapshotsAllTickers(polygonClient: PolygonRestClient) {
    println("Forex snapshot:")
    polygonClient.forexClient.getSnapshotAllTickersBlocking().pp()
}

// Forex Snapshots Gainers Losers
// https://polygon.io/docs/forex/get_v2_snapshot_locale_global_markets_forex__direction
fun forexSnapshotsGainersLosers(polygonClient: PolygonRestClient) {
    println("Forex gainers:")
    polygonClient.forexClient.getSnapshotGainersOrLosersBlocking(GainersOrLosersDirection.GAINERS).pp()

    println("Forex losers:")
    polygonClient.forexClient.getSnapshotGainersOrLosersBlocking(GainersOrLosersDirection.LOSERS).pp()
}

// Forex Snapshots Ticker
// https://polygon.io/docs/forex/get_v2_snapshot_locale_global_markets_forex_tickers__ticker
fun forexSnapshotsTicker(polygonClient: PolygonRestClient) {
    println("C:EURUSD snapshot:")
    polygonClient.stocksClient.getSnapshotBlocking("C:EURUSD").pp()
}

// Forex Technical Indicators EMA
// https://polygon.io/docs/forex/get_v1_indicators_ema__fxticker
fun forexTechnicalIndicatorsEMA(polygonClient: PolygonRestClient) {
    println("C:EURUSD EMA:")
    polygonClient.getTechnicalIndicatorEMABlocking("C:EURUSD", EMAParameters()).pp()
}

// Forex Technical Indicators MACD
// https://polygon.io/docs/forex/get_v1_indicators_macd__fxticker
fun forexTechnicalIndicatorsMACD(polygonClient: PolygonRestClient) {
    println("C:EURUSD MACD:")
    polygonClient.getTechnicalIndicatorMACDBlocking("C:EURUSD", MACDParameters()).pp()
}

// Forex Technical Indicators RSI
// https://polygon.io/docs/forex/get_v1_indicators_rsi__fxticker
fun forexTechnicalIndicatorsRSI(polygonClient: PolygonRestClient) {
    println("C:EURUSD RSI:")
    polygonClient.getTechnicalIndicatorRSIBlocking("C:EURUSD", RSIParameters()).pp()
}

// Forex Technical Indicators SMA
// https://polygon.io/docs/forex/get_v1_indicators_sma__fxticker
fun forexTechnicalIndicatorsSMA(polygonClient: PolygonRestClient) {
    println("C:EURUSD SMA:")
    polygonClient.getTechnicalIndicatorSMABlocking("C:EURUSD", SMAParameters()).pp()
}

// Forex Tickers
// https://polygon.io/docs/forex/get_v3_reference_tickers
fun forexTickers(polygonClient: PolygonRestClient) {
    println("Tickers:")
    val params = SupportedTickersParameters(
        sortBy = "ticker",
        sortDescending = false,
        market = "fx",
        limit = 100
    )

    polygonClient.referenceClient.getSupportedTickersBlocking(params).pp()
}
