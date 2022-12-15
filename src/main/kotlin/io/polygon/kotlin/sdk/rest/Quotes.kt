package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
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
    val requestID: String? = null,
    override val nextUrl: String? = null,
    override val results: List<QuoteResult>? = null
) : Paginatable<QuoteResult>

@Serializable
data class QuoteResult(
    val askExchange: Int? = null,
    val askPrice: Double? = null,
    val bidExchange: Int? = null,
    val bidPrice: Double? = null,
    val participantTimestamp: Long? = null,
)
