package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import io.ktor.http.*
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun PolygonRestClient.getTrades(
    params: TradesParameters,
    vararg opts: PolygonRestOption
): TradesResponse =
    fetchResult({
        path(
            "v3",
            "trades",
            params.ticker,
        )
        params.timestamp?.let { parameters["timestamp"] = it.toString() }
        params.timestampLT?.let { parameters["timestamp.lt"] = it.toString() }
        params.timestampLTE?.let { parameters["timestamp.lte"] = it.toString() }
        params.timestampGT?.let { parameters["timestamp.gt"] = it.toString() }
        params.timestampGTE?.let { parameters["timestamp.gte"] = it.toString() }
        params.sort?.let{ parameters["sort"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
    }, *opts)

@Builder
data class TradesParameters(
    /**
     * The ticker symbol to get trades for.
     */
    val ticker: String,

    /**
     *  Query by trade using nanosecond Unix epoch time.
     *  Use timestampLT/LTE/GT/GTE for additional filtering
     */
    val timestamp: Long? = null,

    /**
     * Return results where this field is less than the value.
     */
    val timestampLT: Long? = null,

    /**
     * Return results where this field is less than or equal to the value.
     */
    val timestampLTE: Long? = null,

    /**
     * Return results where this field is greater than the value.
     */
    val timestampGT: Long? = null,

    /**
     * Return results where this field is greater than or equal to the value.
     */
    val timestampGTE: Long? = null,

    /**
     * Limit the number of results returned, default is 10 and max is 50000.
     * The API will default this to 10
     */
    val limit: Int? = null,

    /**
     * Field used for ordering. See docs for valid fields
     */
    val sort: String? = null
)

@Serializable
data class TradesResponse(
    val status: String? = null,
    val requestID: String? = null,
    override val nextUrl: String? = null,
    override val results: List<TradeResult>? = null
) : Paginatable<TradeResult>


@Serializable
data class TradeResult(
    val conditions: List<Int>? = null,
    val correction: Int? = null,
    val exchange: Int? = null,
    val participantTimestamp: Long? = null,
    val price: Double? = null,
    val sipTimestamp: Long? = null,
    val size: Double? = null,
)
