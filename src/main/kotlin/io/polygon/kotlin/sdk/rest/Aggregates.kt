package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonRestClient.getAggregatesBlocking] */
@SafeVarargs
suspend fun PolygonRestClient.getAggregates(
    params: AggregatesParameters,
    vararg opts: PolygonRestOption
): AggregatesDTO =
    fetchResult({
        path(
            "v2",
            "aggs",
            "ticker",
            params.ticker,
            "range",
            params.multiplier.toString(),
            params.timespan,
            params.fromDate,
            params.toDate
        )

        parameters["unadjusted"] = params.unadjusted.toString()
        parameters["limit"] = params.limit.toString()
        parameters["sort"] = params.sort
    }, *opts)

@Builder
data class AggregatesParameters(
    /**
     * The symbol of the request
     *
     * Some tickers require a prefix, see examples below:
     * Stocks: AAPL
     * Currencies: C:EURUSD
     * Crypto: X:BTCUSD
     * Indices: I:SPX
     */
    val ticker: String,

    /** Size of the timespan multiplier */
    @DefaultValue("1")
    val multiplier: Long = 1,

    /** Size of the time window. Ex: "day", "minute", "quarter", etc*/
    val timespan: String,

    /** From date (formatted YYYY-MM-DD) */
    val fromDate: String,

    /** To date (formatted YYYY-MM-DD) */
    val toDate: String,

    /** Set to true if the results should NOT be adjusted for splits. Default: false */
    @DefaultValue("false")
    val unadjusted: Boolean = false,

    /** Limits the number of base aggregates */
    @DefaultValue("5000")
    val limit: Long = 5000,

    /**
     * Sort the results by timestamp. asc will return results in ascending order (oldest at the top),
     * desc will return results in descending order (newest at the top).
     * */
    @DefaultValue("asc")
    val sort: String
)

@Serializable
data class AggregatesDTO(
    val status: String? = null,
    val ticker: String? = null,
    val queryCount: Long? = null,
    val resultsCount: Long? = null,
    val adjusted: Boolean? = null,
    val results: List<AggregateDTO> = emptyList()
)

@Serializable
data class AggregateDTO(
    @SerialName("T") val ticker: String? = null,
    @SerialName("v") val volume: Double? = null,
    @SerialName("vw") val volumeWeightedAveragePrice: Double? = null,
    @SerialName("o") val open: Double? = null,
    @SerialName("c") val close: Double? = null,
    @SerialName("l") val low: Double? = null,
    @SerialName("h") val high: Double? = null,
    @SerialName("t") val timestampMillis: Long? = null,
    @SerialName("n") val numItemsInWindow: Long? = null
)