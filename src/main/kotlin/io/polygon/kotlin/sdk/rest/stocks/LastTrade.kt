package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getLastTradeBlocking] */
@Deprecated("superseded by getLastTradeV2 and will be replaced in a future version")
suspend fun PolygonStocksClient.getLastTrade(symbol: String, vararg opts: PolygonRestOption): LastTradeResultDTO =
    polygonClient.fetchResult({ path("v1", "last", "stocks", symbol) }, *opts)

@Serializable
@Deprecated("superseded by LastTradeResultV2")
data class LastTradeResultDTO(
    val status: String? = null,
    val symbol: String? = null,
    @SerialName("last") val lastTrade: LastTradeDTO? = LastTradeDTO()
)

@Serializable
@Deprecated("superseded by LastTradeV2")
data class LastTradeDTO(
    val price: Double? = null,
    val size: Long? = null,
    val exchange: Long? = null,
    val cond1: Long? = null,
    val cond2: Long? = null,
    val cond3: Long? = null,
    val cond4: Long? = null,
    val timestamp: Long? = null
)

suspend fun PolygonStocksClient.getLastTradeV2(ticker: String, vararg opts: PolygonRestOption): LastTradeResultV2 =
    polygonClient.fetchResult({ path("v2", "last", "trade", ticker) }, *opts)

@Serializable
data class LastTradeResultV2(
    @SerialName("request_id") val requestID: String? = null,
    val status: String? = null,
    val results: LastTradeV2
)

@Serializable
data class LastTradeV2(
    @SerialName("T") val ticker: String? = null,
    @SerialName("c") val conditions: List<Int> = emptyList(),
    @SerialName("e") val correction: Int? = null,
    @SerialName("f") val trfTimestampNanos: Long? = null,
    @SerialName("i") val tradeId: String? = null,
    @SerialName("p") val price: Double? = null,
    @SerialName("q") val sequenceNumber: Long? = null,
    @SerialName("r") val tradeFacility: Int? = null,
    @SerialName("s") val size: Int? = null,
    @SerialName("t") val sipTimestampNanos: Long? = null,
    @SerialName("x") val exchangeId: Long? = null,
    @SerialName("y") val exchangeTimestampNanos: Long? = null,
    @SerialName("z") val tape: String? = null,
)