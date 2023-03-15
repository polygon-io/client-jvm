package io.polygon.kotlin.sdk.rest.indices

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/** See [PolygonIndicesClient.getSnapshotBlocking] */
suspend fun PolygonIndicesClient.getSnapshot(
    params: SnapshotIndicesParameters,
    vararg opts: PolygonRestOption
): SnapshotIndicesDTO =
    polygonClient.fetchResult({
        path("v3", "snapshot", "indices")

        params.tickers.let{ parameters["ticker.any_of"]= it.joinToString(",") }
    }, *opts)

@Builder
data class SnapshotIndicesParameters(

    /**
     * Provide 1 to N tickers representing indices. "I:" should lead each ticker e.g. "I:SPX"
     */
    val tickers: List<String> = emptyList()
)

@Serializable
data class SnapshotIndicesDTO(
    @SerialName("request_id") val requestID: String? = null,
    val status: String? = null,
    val results: List<SnapshotIndexDTO> = emptyList()
)

@Serializable
data class SnapshotIndexDTO(
    val error: String? = null,
    @SerialName("market_status") val marketStatus: String? = null,
    val message: String? = null,
    val name: String? = null,
    val session: SnapshotIndexSessionDTO? = null,
    val ticker: String? = null,
    val type: String? = null,
    val value: Double? = null
)

@Serializable
data class SnapshotIndexSessionDTO(
    val change: Double? = null,
    @SerialName("change_percent") val changePercent: Double? = null,
    val close: Double? = null,
    val high: Double? = null,
    val low: Double? = null,
    val open: Double? = null,
    @SerialName("previous_close") val previousClose: Double? = null
)