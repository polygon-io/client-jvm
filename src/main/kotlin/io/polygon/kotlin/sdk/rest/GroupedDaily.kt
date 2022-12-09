package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceClient

/** See [PolygonRestClient.getGroupedDailyAggregatesBlocking] */
suspend fun PolygonRestClient.getGroupedDailyAggregates(
    params: GroupedDailyParameters,
    vararg opts: PolygonRestOption
): AggregatesDTO =
    fetchResultWithOptions({
        path(
            "v2",
            "aggs",
            "grouped",
            "locale",
            params.locale,
            "market",
            params.market,
            params.date
        )

        parameters["unadjusted"] = params.unadjusted.toString()
    }, *opts)

@Builder
data class GroupedDailyParameters(
    /** Locale of the aggregates. See [PolygonReferenceClient.getSupportedLocalesBlocking] */
    val locale: String,

    /** Market of the aggregates. See [PolygonReferenceClient.getSupportedMarketsBlocking] */
    val market: String,

    /** The date to group by (formatted YYYY-MM-DD) */
    val date: String,

    /** Set this to true if the results should NOT be adjusted for splits. Default: false */
    @DefaultValue("false")
    val unadjusted: Boolean = false
)