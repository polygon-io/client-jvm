package io.polygon.kotlin.sdk.rest

import io.ktor.client.plugins.*
import io.ktor.client.request.*

typealias PolygonRestOption = HttpRequestBuilder.() -> Unit

/**
 * PolygonRestOptions encapsulates a few helper functions for common request options.
 *
 * Option functionality is not limited to these helper functions.
 * You can always create your own [PolygonRestOption] to manipulate anything within an [HttpRequestBuilder].
 */
object PolygonRestOptions {

    /**
     * Sets an arbitrary header on an out-going request.
     */
    @JvmStatic
    fun withHeader(header: String, value: String): PolygonRestOption = {
        headers[header] = value
    }

    /**
     * Sets an arbitrary query parameter on an out-going request.
     *
     * This may be useful if a new query parameter is introduced to an API
     * but the SDK hasn't yet been updated to include it,
     * or if something is preventing you from updating to the latest version of the SDK.
     */
    @JvmStatic
    fun withQueryParam(name: String, value: String): PolygonRestOption = {
        url.parameters[name] = value
    }

    /**
     * Overrides the default timeout for an out-going request.
     */
    @JvmStatic
    fun withTimeout(timeoutMillis: Long): PolygonRestOption = {
        timeout { requestTimeoutMillis = timeoutMillis }
    }

    /**
     * Sets edge headers for users with the launchpad product.
     *
     * @param edgeID The ID associated with the edge user making the request.
     * This should be between 1 and 80 characters long.
     *
     * @param edgeIPAddress The IP address that the edge user made the request from.
     * This should be a string in IPv4 dotted decimal ("192.0.2.1"), IPv6 ("2001:db8::68"),
     * or IPv4-mapped IPv6 ("::ffff:192.0.2.1") form.
     *
     * @param edgeUserAgent The user agent that the edge user made the request from.
     * This param is optional. If provided, it should be a string between 1 and 80 characters long.
     */
    fun withEdgeHeaders(edgeID: String, edgeIPAddress: String, edgeUserAgent: String? = null): PolygonRestOption = {
        headers["X-Polygon-Edge-ID"] = edgeID
        headers["X-Polygon-Edge-IP-Address"] = edgeIPAddress
        edgeUserAgent?.let { headers["X-Polygon-Edge-User-Agent"] = it }
    }
}
