package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.rest.*
import io.polygon.kotlin.sdk.rest.reference.*
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.options.SnapshotChainParameters
import com.tylerthrailkill.helpers.prettyprint.pp

// Options Aggregates Bars
// https://polygon.io/docs/options/get_v2_aggs_ticker__optionsticker__range__multiplier___timespan___from___to
fun optionsAggregatesBars(polygonClient: PolygonRestClient) {
    println("O:SPY251219C00650000 Aggs")
    val params = AggregatesParameters(
        ticker = "O:SPY251219C00650000",
        timespan = "day",
        fromDate = "2023-01-30",
        toDate = "2023-02-03",
        sort = "asc",
        limit = 50_000
    )

    polygonClient.getAggregatesBlocking(params).pp()
}

// Options Conditions
// https://polygon.io/docs/options/get_v3_reference_conditions
fun optionsConditions(polygonClient: PolygonRestClient) {
    println("Conditions:")
    polygonClient.referenceClient.getConditionsBlocking(ConditionsParameters(assetClass = "options")).pp()
    polygonClient.referenceClient.listConditions(ConditionsParameters(assetClass = "options", limit = 5))
        .asSequence().take(15).forEach { println("got condition #${it.id}") }
}

// Options Contract
// https://polygon.io/docs/options/get_v3_reference_options_contracts__options_ticker
fun optionsContract(polygonClient: PolygonRestClient) {
    println("O:EVRI240119C00002500 contract details:")
    polygonClient.referenceClient.getOptionsContractDetailsBlocking(
        "O:EVRI240119C00002500",
        OptionsContractDetailsParameters()
    ).pp()
}

// Options Contracts
// https://polygon.io/docs/options/get_v3_reference_options_contracts
fun optionsContracts(polygonClient: PolygonRestClient) {

    println("list contracts: ")
    polygonClient.referenceClient.listOptionsContracts(OptionsContractsParameters(limit = 5))
        .asSequence().take(5).forEach { println("got an options contract: ${it.ticker}") }

    println("AAPL contracts:")
    polygonClient.referenceClient
        .getOptionsContractsBlocking(
            OptionsContractsParameters(
                underlyingTicker = ComparisonQueryFilterParameters.equal(
                    "AAPL"
                )
            )
        )
        .pp()

}

// Options Daily Open Close
// https://polygon.io/docs/options/get_v1_open-close__optionsticker___date
fun optionsDailyOpenClose(polygonClient: PolygonRestClient) {
    println("O:SPY251219C00650000 open/close on 2023-01-09")
    polygonClient.stocksClient.getDailyOpenCloseBlocking("O:SPY251219C00650000", "2023-01-09", true).pp()
}

// Options Exchanges
// https://polygon.io/docs/options/get_v3_reference_exchanges
fun optionsExchanges(polygonClient: PolygonRestClient) {
    println("Supported stock exchanges: ")
    polygonClient.referenceClient.getExchangesBlocking(ExchangesParameters(assetClass = "options")).pp()
}

// Options Last Trade
// https://polygon.io/docs/options/get_v2_last_trade__optionsticker
fun optionsLastTrade(polygonClient: PolygonRestClient) {
    println("Last O:TSLA210903C00700000 trade: ")
    polygonClient.stocksClient.getLastTradeBlockingV2("O:TSLA210903C00700000").pp()
}

// Options Market Holidays
// https://polygon.io/docs/options/get_v1_marketstatus_upcoming
fun optionsMarketHolidays(polygonClient: PolygonRestClient) {
	println("Market holidays:")
    polygonClient.referenceClient.getMarketHolidaysBlocking().pp()
}

// Options Market Status
// https://polygon.io/docs/options/get_v1_marketstatus_now
fun optionsMarketStatus(polygonClient: PolygonRestClient) {
    println("Market status:")
    polygonClient.referenceClient.getMarketStatusBlocking().pp()
}

// Options Previous Close
// https://polygon.io/docs/options/get_v2_aggs_ticker__optionsticker__prev
fun optionsPreviousClose(polygonClient: PolygonRestClient) {
    println("O:SPY251219C00650000 Previous Close:")
    polygonClient.stocksClient.getPreviousCloseBlocking("O:SPY251219C00650000", true).pp()
}

// Options Quotes
// https://polygon.io/docs/options/get_v3_quotes__optionsticker
fun optionsQuotes(polygonClient: PolygonRestClient) {
    println("O:SPY241220P00720000 Quotes")
    val params = QuotesParameters(limit = 2)
    polygonClient.getQuotesBlocking("O:SPY241220P00720000", params).pp()
}

// Options Snapshots Option Contract
// https://polygon.io/docs/options/get_v3_snapshot_options__underlyingasset___optioncontract
fun optionsSnapshotsOptionContract(polygonClient: PolygonRestClient) {
    println("O:EVRI240119C00002500 snapshot:")
    polygonClient.optionsClient.getSnapshotBlocking("EVRI", "O:EVRI240119C00002500").pp()
}

// Options Snapshots Options Chain
// https://polygon.io/docs/options/get_v3_snapshot_options__underlyingasset
fun optionsSnapshotsOptionsChain(polygonClient: PolygonRestClient) {
    print("HCP options snapshots:")
    polygonClient.optionsClient.getSnapshotChainBlocking("HCP", SnapshotChainParameters()).pp()
    polygonClient.optionsClient.listSnapshotChain("HCP", SnapshotChainParameters(limit = 5))
        .asSequence()
        .take(15)
        .forEach { println(it.details?.ticker ?: "UNKNOWN!") }
}

// Options Technical Indicators EMA
// https://polygon.io/docs/options/get_v1_indicators_ema__optionsticker
fun optionsTechnicalIndicatorsEMA(polygonClient: PolygonRestClient) {
    println("O:SPY241220P00720000 EMA:")
    polygonClient.getTechnicalIndicatorEMABlocking("O:SPY241220P00720000", EMAParameters()).pp()
}

// Options Technical Indicators MACD
// https://polygon.io/docs/options/get_v1_indicators_macd__optionsticker
fun optionsTechnicalIndicatorsMACD(polygonClient: PolygonRestClient) {
    println("O:SPY241220P00720000 MACD:")
    polygonClient.getTechnicalIndicatorMACDBlocking("O:SPY241220P00720000", MACDParameters()).pp()
}

// Options Technical Indicators RSI
// https://polygon.io/docs/options/get_v1_indicators_rsi__optionsticker
fun optionsTechnicalIndicatorsRSI(polygonClient: PolygonRestClient) {
    println("O:SPY241220P00720000 RSI:")
    polygonClient.getTechnicalIndicatorRSIBlocking("O:SPY241220P00720000", RSIParameters()).pp()
}

// Options Technical Indicators SMA
// https://polygon.io/docs/options/get_v1_indicators_sma__optionsticker
fun optionsTechnicalIndicatorsSMA(polygonClient: PolygonRestClient) {
    println("O:SPY241220P00720000 SMA:")
    polygonClient.getTechnicalIndicatorSMABlocking("O:SPY241220P00720000", SMAParameters()).pp()
}

// Options Ticker Details
// https://polygon.io/docs/options/get_v3_reference_tickers__ticker
fun optionsTickerDetails(polygonClient: PolygonRestClient) {
	println("Ticker Details: ")
    polygonClient.referenceClient.getTickerDetailsV3Blocking("AAPL", TickerDetailsParameters()).pp()
}

// Options Ticker News
// https://polygon.io/docs/options/get_v2_reference_news
fun optionsTickerNews(polygonClient: PolygonRestClient) {
    println("AAPL news:")
    val params = TickerNewsParametersV2(ticker = ComparisonQueryFilterParameters.equal("AAPL"), limit = 2)
    polygonClient.referenceClient.getTickerNewsBlockingV2(params).pp()

    println("List news:")
    polygonClient.referenceClient.listTickerNewsV2(params)
        .asSequence().take(10).forEach { println("news article: ${it.articleUrl}") }
}

// Options Tickers
// https://polygon.io/docs/options/get_v3_reference_tickers
fun optionsTickers(polygonClient: PolygonRestClient) {
    println("Tickers:")
    val params = SupportedTickersParameters(
        sortBy = "ticker",
        sortDescending = false,
        market = "stocks",
        limit = 100
    )

    polygonClient.referenceClient.getSupportedTickersBlocking(params).pp()
}

// Options Trades
// https://polygon.io/docs/options/get_v3_trades__optionsticker
fun optionsTrades(polygonClient: PolygonRestClient) {
    println("O:TSLA210903C00700000 Trades:")
    val params = TradesParameters(limit = 2)
    polygonClient.getTradesBlocking("O:TSLA210903C00700000", params).pp()
}
