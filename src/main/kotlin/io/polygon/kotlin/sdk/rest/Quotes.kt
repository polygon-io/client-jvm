package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.ComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.applyComparisonQueryFilterParameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonRestClient.getQuotesBlocking] */
suspend fun PolygonRestClient.getQuotes(
    ticker: String,
    params: QuotesParameters,
    vararg opts: PolygonRestOption
): QuotesResponse = fetchResult({
    path(
        "v3",
        "quotes",
        ticker
    )

    applyComparisonQueryFilterParameters("timestamp", params.timestamp)
    params.order?.let { parameters["order"] = it }
    params.limit?.let { parameters["limit"] = it.toString() }
    params.sort?.let{ parameters["sort"] = it }
}, *opts)


@Builder
data class QuotesParameters(

    /**
     *  Query by timestamp. Either a date with the format YYYY-MM-DD or a nanosecond timestamp.
     */
    val timestamp: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Order results based on the sort field.
     * Can be "asc" or "desc"
     */
    val order: String? = null,

    /**
     * Limit the number of results returned, default is 10 and max is 50000.
     * The API will default this to 10
     */
    val limit: Int? = null,

    /**
     * Field used for ordering. See API docs for valid fields
     */
    val sort: String? = null
)

@Serializable
data class QuotesResponse(
    val status: String? = null,
    @SerialName("request_id") val requestID: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    override val results: List<Quote>? = null
) : Paginatable<Quote>

@Serializable
data class Quote(
    @SerialName("ask_exchange") val askExchange: Int? = null,
    @SerialName("ask_price") val askPrice: Double? = null,
    @SerialName("bid_exchange") val bidExchange: Int? = null,
    @SerialName("bid_price") val bidPrice: Double? = null,
    @SerialName("participant_timestamp") val participantTimestamp: Long? = null,
)
