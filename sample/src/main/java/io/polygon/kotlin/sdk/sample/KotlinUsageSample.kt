package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.reference.getSupportedMarkets
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.system.exitProcess

suspend fun main() {
    val polygonKey = System.getenv("POLYGON_API_KEY")

    if (polygonKey.isNullOrEmpty()) {
        println("Make sure you set your polygon API key in the POLYGON_API_KEY environment variable!")
        exitProcess(1)
    }

    val polygonClient = PolygonRestClient(polygonKey)

    println("Blocking for markets...")
    val markets = polygonClient.referenceClient.getSupportedMarketsBlocking()
    println("Got markets synchronously: $markets")

    println("Getting markets asynchronously...")
    val deferred = GlobalScope.async {
        val asyncMarkets = polygonClient.referenceClient.getSupportedMarkets()
        println("Got markets asynchronously: $markets")
    }

    deferred.await()
    println("Done getting markets asynchronously!")
}