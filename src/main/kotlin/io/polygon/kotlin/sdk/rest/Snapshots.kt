package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SafeVarargs

suspend fun PolygonRestClient.getSnapshots(
    tickers: List<String>? = emptyList(),
    params: SnapshotsParameters,
    vararg opts: PolygonRestOption
): SnapshotsResponse =
    fetchResult({
        path(
            "v3",
            "snapshot",
            // TODO parse tickers
        )
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }
    }, *opts)

@Builder
data class SnapshotsParameters(
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
    @SerialName("request_id") val requestID: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    override val results: List<Snapshot> = emptyList()
) : Paginatable<Snapshot>

@Serializable
data class Snapshot(
    val todo: String? = null,
)