package io.polygon.kotlin.sdk.rest

import io.ktor.client.HttpClient
import io.ktor.http.URLBuilder
import io.polygon.kotlin.sdk.DefaultJvmHttpClientProvider
import io.polygon.kotlin.sdk.HttpClientProvider
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceRestClient

/**
 * A client for the Polygon.io RESTful API
 *
 * @param apiKey the API key to use with all API requests
 * @param httpClientProvider (Optional) A provider for the ktor [HttpClient] to use; defaults to [DefaultJvmHttpClientProvider]
 * @param polygonApiDomain (Optional) The domain to hit for all API requests; defaults to Polygon's API domain "api.polyhon.io". Useful for overriding in a testing environment
 */
class PolygonRestClient
@JvmOverloads
constructor(
    private val apiKey: String,
    private val httpClientProvider: HttpClientProvider = DefaultJvmHttpClientProvider(),
    private val polygonApiDomain: String = "api.polygon.io"
) {

    /**
     * A [PolygonReferenceRestClient] that can be used to access Polygon reference APIs
     */
    val referenceClient by lazy { PolygonReferenceRestClient(this) }

    /**
     * A [URLBuilder] pre-populated with default values like host and apiKey
     */
    internal val urlBuilder: URLBuilder
        get() = httpClientProvider.getDefaultRestURLBuilder().apply {
            host = polygonApiDomain
            parameters["apiKey"] = apiKey
        }

    internal inline fun <R> withHttpClient(codeBlock: (client: HttpClient) -> R) =
        httpClientProvider.buildClient().use(codeBlock)

}
