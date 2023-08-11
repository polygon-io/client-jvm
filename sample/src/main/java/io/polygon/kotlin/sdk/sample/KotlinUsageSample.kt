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

    /**
     * Stocks REST - access to stocks data depends on your entitlements
     */
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

    /**
     * Stocks WebSocket - access to streaming stocks data depends on your entitlements
     */
	//stocksWebsocketSample(polygonKey)

    /**
     * Options REST - access to options data depends on your entitlements
     */
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

    /**
     * Options WebSocket - access to streaming options data depends on your entitlements
     */
	//optionsWebsocketSample(polygonKey)

    /**
     * Indices REST - access to indices data depends on your entitlements
     */
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

    /**
     * Indices WebSocket - access to streaming indices data depends on your entitlements
     */
	//indicesWebsocketSample(polygonKey)

    /**
     * Forex REST - access to forex data depends on your entitlements
     */
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

    /**
     * Forex WebSocket - access to streaming forex data depends on your entitlements
     */
	//forexWebsocketSample(polygonKey)

    /**
     * Crypto REST - access to crypto data depends on your entitlements
     */
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

    /**
     * Crypto WebSocket - access to streaming crypto data depends on your entitlements
     */
	//cryptoWebsocketSample(polygonKey)

    /**
     * Launchpad WebSocket - access to streaming launchpad data depends on your entitlements
     */
	//launchpadWebsocketSample(polygonKey)

}
