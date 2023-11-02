package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SafeVarargs

suspend fun PolygonRestClient.getSnapshots(
    params: SnapshotsParameters,
    vararg opts: PolygonRestOption
): SnapshotsResponse =
    fetchResult({
        path(
            "v3",
            "snapshot",
        )
        params.tickers?.let{ parameters["tickers.any_of"] = it.joinToString(",") }
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }
    }, *opts)

@Builder
data class SnapshotsParameters(
    /**
     * List of tickers of any asset class.
     * Maximum of 250.
     * If no tickers are passed then all results will be returned in a paginated manner.
     */
    val tickers: List<String>? = null,

    /**
     * Order results based on the sort field.
     * Can be "asc" or "desc"
     */
    val order: String? = null,

    /**
     * Limit the number of results returned per page
     * Default is 10 and max is 250.
     */
    val limit: Int? = null,

    /**
     * Field used for ordering. See docs for valid fields
     */
    val sort: String? = null
)

@Serializable
data class SnapshotsResponse(
    val status: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    @SerialName("request_id") val requestID: String? = null,
    override val results: List<Snapshot> = emptyList()
) : Paginatable<Snapshot>

@Serializable
data class Snapshot(
    val todo: String? = null,
)