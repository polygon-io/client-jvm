package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.ComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.applyComparisonQueryFilterParameters
import io.polygon.kotlin.sdk.rest.Paginatable
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@SafeVarargs
suspend fun PolygonReferenceClient.getTickerNewsV2(params: TickerNewsParametersV2, vararg opts: PolygonRestOption): TickerNewsResponse =
    polygonClient.fetchResult({
        path("v2", "reference", "news")

        applyComparisonQueryFilterParameters("ticker", params.ticker)
        applyComparisonQueryFilterParameters("published_utc", params.publishedUtc)
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }
    })

/**
 * Parameters for the v2 ticker news API.
 * Note: This will be renamed to `TickerNewsParameters` in a future release.
 */
@Builder
data class TickerNewsParametersV2(

    /**
     * Query by ticker.
     */
    val ticker: ComparisonQueryFilterParameters<String>? = null,

    /**
     * Return results published on, before, or after this date in the format YYYY-MM-DD.
     */
    val publishedUtc: ComparisonQueryFilterParameters<String>? = null,

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
data class TickerNewsResponse(
    val status: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    override val results: List<TickerNews>? = null,
) : Paginatable<TickerNews>

@Serializable
data class TickerNews(
    @SerialName("article_url") val articleUrl: String = "",
    val author: String = "",
    val id: String = "",
    @SerialName("published_utc") val publishedUtc: String = "",
    val title: String = "",

    @SerialName("amp_url") val ampUrl: String? = null,
    val description: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    val keywords: List<String>? = null,
    val publisher: NewsPublisher? = null,
    val tickers: List<String>? = null,
)

@Serializable
data class NewsPublisher(
    @SerialName("favicon_url") val faviconUrl: String = "",
    @SerialName("homepage_url") val homepageUrl: String = "",
    @SerialName("logo_url") val logoUrl: String = "",
    @SerialName("name") val name: String = "",
)

/** See [PolygonReferenceClient.getTickerNewsBlocking] */
suspend fun PolygonReferenceClient.getTickerNews(
    params: TickerNewsParameters,
    vararg opts: PolygonRestOption
): List<TickerNewsDTO> =
    polygonClient.fetchResult({
        path("v1", "meta", "symbols", params.symbol, "news")

        parameters["perpage"] = params.resultsPerPage.toString()
        parameters["page"] = params.page.toString()
    }, *opts)

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
    val title: String? = null,
    val url: String? = null,
    val source: String? = null,
    val summary: String? = null,
    val timestamp: String? = null,
    val image: String? = null,
    val keywords: List<String> = emptyList()
)