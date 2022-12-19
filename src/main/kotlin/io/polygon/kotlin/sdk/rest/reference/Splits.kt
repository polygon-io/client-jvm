package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.ComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.applyComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.Paginatable
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSplitsBlocking] */
@SafeVarargs
suspend fun PolygonReferenceClient.getSplits(params: SplitsParameters, vararg opts: PolygonRestOption): SplitsResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "splits")

        applyComparisonQueryFilterParameters("ticker", params.ticker)
        applyComparisonQueryFilterParameters("execution_date", params.executionDate)
        params.reverseSplit?.let { parameters["reverse_split"] = it.toString() }
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }

    }, *opts)

@Builder
data class SplitsParameters(
    /**
     * Query by ticker.
     */
    val ticker: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query by execution date with the format YYYY-MM-DD.
     */
    val executionDate: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Query for reverse stock splits.
     * A split ratio where split_from is greater than split_to represents a reverse split.
     *
     * By default, this filter is not used.
     */
    val reverseSplit: Boolean? = null,

    /**
     * Order results based on the sort field.
     * Can be "asc" or "desc"
     */
    val order: String? = null,

    /**
     * Limit the number of results returned, default is 10 and max is 1000.
     */
    val limit: Int? = null,

    /**
     * Field used for ordering. See API docs for valid fields
     */
    val sort: String? = null
)

@Serializable
data class SplitsResponse(
    val status: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    override val results: List<Split>? = null,
) : Paginatable<Split>

@Serializable
data class Split(
    @SerialName("execution_date") val executionDate: String? = null,
    @SerialName("split_from") val splitFrom: Double? = null,
    @SerialName("split_to") val splitTo: Double? = null,
    val ticker: String? = null
)