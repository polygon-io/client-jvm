package io.polygon.kotlin.sdk.rest.stocks

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getHistoricTradesBlocking] */
suspend fun PolygonStocksClient.getHistoricTrades(
    params: HistoricTradesParameters,
    vararg opts: PolygonRestOption
): HistoricTradesDTO =
    polygonClient.fetchResult({
        path("v2", "ticks", "stocks", "trades", params.ticker, params.date)

        parameters["limit"] = params.limit.toString()
        params.timestamp?.let { parameters["timestamp"] = it.toString() }
        params.timestampLimit?.let { parameters["timestampLimit"] = it.toString() }
        params.reverse?.let { parameters["reverse"] = it.toString() }
    }, *opts)

@Builder
data class HistoricTradesParameters(
    val ticker: String,

    /** Date/Day of the historic ticks to retreive ( YYYY-MM-DD ) */
    val date: String,

    /**
     * (Optional) Timestamp offset, used for pagination. This is the offset at which to start the results.
     * Using the timestamp of the last result as the offset will give you the next page of results.
     */
    val timestamp: Long? = null,

    /** (Optional) Maximum timestamp allowed in the results. */
    val timestampLimit: Long? = null,

    /** (Optional) Reverse the order of the results */
    val reverse: Boolean? = null,

    /** Limit the size of response, Max 50000, Default 10 */
    @DefaultValue("10") val limit: Int = 10
)

@Serializable
data class HistoricTradesDTO(
    @SerialName("results_count") val resultsCount: Long? = null,
    @SerialName("db_latency") val dbLatency: Long? = null,
    val success: Boolean = false,
    val ticker: String? = null,
    val results: List<HistoricTradeDTO> = emptyList()
)

@Serializable
data class HistoricTradeDTO(
    @SerialName("T") val ticker: String? = null,
    @SerialName("t") val sipTimestampNanos: Long? = null,
    @SerialName("y") val exchangeTimestampNanos: Long? = null,
    @SerialName("f") val trfTimestampNanos: Long? = null,
    @SerialName("q") val sequenceNumber: Long? = null,
    @SerialName("i") val tradeId: String? = null,
    @SerialName("x") val exchangeId: Long? = null,
    @SerialName("s") val size: Long? = null,
    @SerialName("c") val conditions: List<Int> = emptyList(),
    @SerialName("p") val price: Double? = null,
    @SerialName("z") val tape: Long? = null
)