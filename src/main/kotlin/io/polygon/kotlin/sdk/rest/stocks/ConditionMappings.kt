package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption

/** See [PolygonStocksClient.getConditionMappingsBlocking] */
@Deprecated("use getConditions in PolygonReferenceClient instead")
suspend fun PolygonStocksClient.getConditionMappings(
    type: ConditionMappingTickerType,
    vararg opts: PolygonRestOption
): Map<String, String> =
    polygonClient.fetchResult({ path("v1", "meta", "conditions", type.urlParamName) }, *opts)

@Deprecated("used in deprecated getConditionMappings")
enum class ConditionMappingTickerType(internal val urlParamName: String) {
    TRADES("trades"),
    QUOTES("quotes")
}