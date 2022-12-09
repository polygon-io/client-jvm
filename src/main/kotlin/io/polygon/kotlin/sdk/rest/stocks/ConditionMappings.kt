package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption

/** See [PolygonStocksClient.getConditionMappingsBlocking] */
suspend fun PolygonStocksClient.getConditionMappings(
    type: ConditionMappingTickerType,
    vararg opts: PolygonRestOption
): Map<String, String> =
    polygonClient.fetchResultWithOptions({ path("v1", "meta", "conditions", type.urlParamName) }, *opts)

enum class ConditionMappingTickerType(internal val urlParamName: String) {
    TRADES("trades"),
    QUOTES("quotes")
}