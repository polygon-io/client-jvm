package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import kotlinx.serialization.Serializable

/** See [PolygonReferenceRestClient.getTickerNewsBlocking] */
suspend fun PolygonReferenceRestClient.getTickerNews(params: TickerNewsParameters): List<TickerNewsDTO> =
    polygonClient.fetchResult {
        path("v1", "meta", "symbols", params.symbol, "news")

        parameters["perpage"] = params.resultsPerPage.toString()
        parameters["page"] = params.page.toString()
    }

@Builder
data class TickerNewsParameters(
    /** The ticker we want news for */
    val symbol: String,

    /** How many items per page to return. Max: 50, default: 10 */
    @DefaultValue("10")
    val resultsPerPage: Int = 10,

    /** The page of results to return. Default: 1*/
    @DefaultValue("1")
    val page: Int = 1
)

@Serializable
data class TickerNewsDTO(
    val symbols: List<String> = emptyList(),
    val title: String,
    val url: String,
    val source: String,
    val summary: String,
    val timestamp: String,
    val image: String? = null,
    val keywords: List<String> = emptyList()
)