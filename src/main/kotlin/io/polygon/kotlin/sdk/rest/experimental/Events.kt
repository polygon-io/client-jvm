package io.polygon.kotlin.sdk.rest.experimental

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.ComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.applyComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.Paginatable
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SafeVarargs
@ExperimentalAPI
suspend fun PolygonExperimentalClient.getTickerEvents(id: String, types: String? = null, vararg opts: PolygonRestOption): TickerEventsResponse =
    polygonClient.fetchResult({
        path("vX", "reference", "tickers", id, "events")
        types?.let { parameters["types"] = it }
    }, *opts)

@Builder
@ExperimentalAPI
data class TickerEventsParameters(

    /**
     * Identifier of an asset. This can currently be a Ticker, CUSIP, or Composite FIGI.
     */
    val id: String,

    /**
     * A comma-separated list of the types of event to include. Currently ticker_change is the only supported event_type. Leave blank to return all supported event_types.
     */
    val types: String? = null
)

@Serializable
@ExperimentalAPI
data class TickerEventsResponse(
    val status: String? = null,
    @SerialName("request_id") val requestID: String? = null,
    val results: TickerEvents? = null
)

@Serializable
@ExperimentalAPI
data class TickerEvents(
    val name: String = "",
    @SerialName("composite_figi") val compositeFigi: String = "",
    val cik: String = "",
    val events: List<TickerEvent>? = null
)

@Serializable
@ExperimentalAPI
data class TickerEvent(
    @SerialName("ticker_change") val tickerChange: TickerChange? = null,
    val type: String? = null,
    val date: String? = null
)

@Serializable
@ExperimentalAPI
data class TickerChange(
    val ticker: String? = null
)
