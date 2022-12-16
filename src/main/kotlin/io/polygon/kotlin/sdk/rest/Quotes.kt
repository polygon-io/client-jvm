package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun PolygonRestClient.getQuotes(
    params: QuotesParameters,
    vararg opts: PolygonRestOption
): QuotesResponse = fetchResult({
    path(
        "v3",
        "quotes",
        params.ticker
    )
    params.timestamp?.let { parameters["timestamp"] = it.toString() }
    params.timestampLT?.let { parameters["timestamp.lt"] = it.toString() }
    params.timestampLTE?.let { parameters["timestamp.lte"] = it.toString() }
    params.timestampGT?.let { parameters["timestamp.gt"] = it.toString() }
    params.timestampGTE?.let { parameters["timestamp.gte"] = it.toString() }
    params.limit?.let { parameters["limit"] = it.toString() }
    params.sort?.let{ parameters["sort"] = it }
}, *opts)


@Builder
data class QuotesParameters(
    /**
     * The ticker symbol to get quotes for.
     */
    val ticker: String,

    /**
     *  Query by query using nanosecond Unix epoch time.
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
data class QuotesResponse(
    val status: String? = null,
    @SerialName("request_id") val requestID: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    override val results: List<QuoteResult>? = null
) : Paginatable<QuoteResult>

@Serializable
data class QuoteResult(
    @SerialName("ask_exchange") val askExchange: Int? = null,
    @SerialName("ask_price") val askPrice: Double? = null,
    @SerialName("bid_exchange") val bidExchange: Int? = null,
    @SerialName("bid_price") val bidPrice: Double? = null,
    @SerialName("participant_timestamp") val participantTimestamp: Long? = null,
)
