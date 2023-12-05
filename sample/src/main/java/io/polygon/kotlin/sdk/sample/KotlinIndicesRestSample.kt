package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.rest.*
import io.polygon.kotlin.sdk.rest.reference.*
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.indices.SnapshotIndicesParameters
import com.tylerthrailkill.helpers.prettyprint.pp

// Indices Aggregates Bars
// https://polygon.io/docs/indices/get_v2_aggs_ticker__indicesticker__range__multiplier___timespan___from___to
fun indicesAggregatesBars(polygonClient: PolygonRestClient) {
    println("I:SPX Aggs")
    val idxParams = AggregatesParameters(
        ticker = "I:SPX",
        timespan = "day",
        fromDate = "2023-07-03",
        toDate = "2023-07-07",
        sort = "asc",
        limit = 50_000,
    )
    polygonClient.getAggregatesBlocking(idxParams).pp()
}

// Indices Daily Open Close
// https://polygon.io/docs/indices/get_v1_open-close__indicesticker___date
fun indicesDailyOpenClose(polygonClient: PolygonRestClient) {
    println("Indices Daily Open/Close")
    polygonClient.indicesClient.getDailyOpenCloseBlocking("I:NDX", "2023-03-10").pp()
}

// Indices Market Holidays
// https://polygon.io/docs/indices/get_v1_marketstatus_upcoming
fun indicesMarketHolidays(polygonClient: PolygonRestClient) {
	println("Market holidays:")
    polygonClient.referenceClient.getMarketHolidaysBlocking().pp()	
}

// Indices Market Status
// https://polygon.io/docs/indices/get_v1_marketstatus_now
fun indicesMarketStatus(polygonClient: PolygonRestClient) {
    println("Market status:")
    polygonClient.referenceClient.getMarketStatusBlocking().pp()
}

// Indices Previous Close
// https://polygon.io/docs/indices/get_v2_aggs_ticker__indicesticker__prev
fun indicesPreviousClose(polygonClient: PolygonRestClient) {
    println("Indices Previous Close")
    polygonClient.indicesClient.getPreviousCloseBlocking("I:SPX", false).pp()
}

// Indices Snapshots
// https://polygon.io/docs/indices/get_v3_snapshot_indices
fun indicesSnapshots(polygonClient: PolygonRestClient) {
    println("Indices Snapshots")
    polygonClient.indicesClient.getSnapshotBlocking(SnapshotIndicesParameters(tickers = listOf("I:SPX", "I:NDX", "I:DJI"))).pp()
}

// Indices Technical Indicators EMA
// https://polygon.io/docs/indices/get_v1_indicators_ema__indicesticker
fun indicesTechnicalIndicatorsEMA(polygonClient: PolygonRestClient) {
    println("I:NDX EMA:")
    polygonClient.getTechnicalIndicatorEMABlocking("I:NDX", EMAParameters()).pp()
}

// Indices Technical Indicators MACD
// https://polygon.io/docs/indices/get_v1_indicators_macd__indicesticker
fun indicesTechnicalIndicatorsMACD(polygonClient: PolygonRestClient) {
    println("I:NDX MACD:")
    polygonClient.getTechnicalIndicatorMACDBlocking("I:NDX", MACDParameters()).pp()
}

// Indices Technical Indicators RSI
// https://polygon.io/docs/indices/get_v1_indicators_rsi__indicesticker
fun indicesTechnicalIndicatorsRSI(polygonClient: PolygonRestClient) {
    println("I:NDX RSI:")
    polygonClient.getTechnicalIndicatorRSIBlocking("I:NDX", RSIParameters()).pp()
}

// Indices Technical Indicators SMA
// https://polygon.io/docs/indices/get_v1_indicators_sma__indicesticker
fun indicesTechnicalIndicatorsSMA(polygonClient: PolygonRestClient) {
    println("I:NDX SMA:")
    polygonClient.getTechnicalIndicatorSMABlocking("I:NDX", SMAParameters()).pp()
}

// Indices Ticker Types
// https://polygon.io/docs/indices/get_v3_reference_tickers_types
fun indicesTickerTypes(polygonClient: PolygonRestClient) {
	println("Ticker Types: ")
    polygonClient.referenceClient.getTickerTypesBlocking(TickerTypesParameters()).pp()
}

// Indices Tickers
// https://polygon.io/docs/indices/get_v3_reference_tickers
fun indicesTickers(polygonClient: PolygonRestClient) {
    println("Tickers:")
    val params = SupportedTickersParameters(
        sortBy = "ticker",
        sortDescending = false,
        market = "indices",
        limit = 100
    )

    polygonClient.referenceClient.getSupportedTickersBlocking(params).pp()
}
