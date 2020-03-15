package io.polygon.kotlin.sdk.rest.forex

import com.thinkinglogic.builder.annotation.Builder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonForexClient.getHistoricTicksBlocking] */
suspend fun PolygonForexClient.getHistoricTicks(params: HistoricTicksParameters): HistoricTicksDTO =
    polygonClient.fetchResult {
        path("v1", "historic", "forex", params.fromCurrency, params.toCurrency, params.date)

        params.offset?.let { parameters["offset"] = it.toString() }
        parameters["limit"] = params.limit.toString()
    }

@Builder
data class HistoricTicksParameters(
    val fromCurrency: String,
    val toCurrency: String,

    /** The date of the historic ticks to retrieve (formatted YYYY-MM-DD) */
    val date: String,

    /**
     * (Optional) Timestamp offset, used for pagination.
     * This is the offset at which to start the results.
     * Using the timestamp of the last result as the offset will give you the next page of results.
     */
    val offset: Long? = null,

    /** Limit the size of response, Max 10000 */
    val limit: Int
)

@Serializable
data class HistoricTicksDTO(
    val status: String? = null,
    val day: String? = null,
    val pair: String? = null,
    val msLatency: Long? = null,
    val ticks: List<ForexTickDTO> = emptyList()
)

@Serializable
data class ForexTickDTO(
    @SerialName("a") val askPrice: Double? = null,
    @SerialName("b") val bidPrice: Double? = null,
    @SerialName("t") val timestamp: Long? = null
)