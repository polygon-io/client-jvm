package io.polygon.kotlin.sdk.rest.reference

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Reference" RESTful APIs
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonReferenceRestClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    fun getSupportedMarkets(callback: PolygonRestApiCallback<MarketsDTO>) {
        coroutineToRestCallback(callback, { getSupportedMarkets() })
    }

    fun getSupportedMarketsBlocking(): MarketsDTO =
        runBlocking { getSupportedMarkets() }

}