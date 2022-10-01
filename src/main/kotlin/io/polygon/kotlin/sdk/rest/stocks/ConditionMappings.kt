package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*

/** See [PolygonStocksClient.getConditionMappingsBlocking] */
suspend fun PolygonStocksClient.getConditionMappings(type: ConditionMappingTickerType): Map<String, String> =
    polygonClient.fetchResult { path("v1", "meta", "conditions", type.urlParamName) }

enum class ConditionMappingTickerType(internal val urlParamName: String) {
    TRADES("trades"),
    QUOTES("quotes")
}