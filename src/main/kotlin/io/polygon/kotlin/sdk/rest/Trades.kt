package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonRestClient.getTradesBlocking] */
@SafeVarargs
suspend fun PolygonRestClient.getTrades(
    ticker: String,
    params: TradesParameters,
    vararg opts: PolygonRestOption
): TradesResponse =
    fetchResult({
        path(
            "v3",
            "trades",
            ticker,
        )

        applyComparisonQueryFilterParameters("timestamp", params.timestamp)
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }
    }, *opts)

@Builder
data class TradesParameters(

    /**
     *  Query by trade timestamp. Either a date with the format YYYY-MM-DD or a nanosecond timestamp.
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
     * Field used for ordering. See docs for valid fields
     */
    val sort: String? = null
)

@Serializable
data class TradesResponse(
    val status: String? = null,
    @SerialName("request_id") val requestID: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    override val results: List<Trade> = emptyList()
) : Paginatable<Trade>


@Serializable
data class Trade(
    val conditions: List<Int>? = null,
    val correction: Int? = null,
    val exchange: Int? = null,
    @SerialName("participant_timestamp") val participantTimestamp: Long? = null,
    val price: Double? = null,
    @SerialName("sip_timestamp") val sipTimestamp: Long? = null,
    val size: Double? = null,
)
