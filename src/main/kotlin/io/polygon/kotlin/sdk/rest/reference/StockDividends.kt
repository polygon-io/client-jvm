package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getStockDividendsBlocking] */
@SafeVarargs
@Deprecated("use getDividends or listDividends", ReplaceWith("getDividends(params, *opts)"))
suspend fun PolygonReferenceClient.getStockDividends(
    symbol: String,
    vararg opts: PolygonRestOption
): StockDividendsDTO =
    polygonClient.fetchResult({
        path("v2", "reference", "dividends", symbol)
    }, *opts)

@Serializable
@Deprecated("used in deprecated getStockDividends")
data class StockDividendsDTO(
    val status: String? = null,
    val count: Int? = null,
    val results: List<StockDividendDTO> = emptyList()
)

@Serializable
@Deprecated("used in deprecated getStockDividends")
data class StockDividendDTO(
    val symbol: String? = null,
    val type: String? = null,
    @SerialName("exDate") val executionDate: String? = null,
    val paymentDate: String? = null,
    val recordDate: String? = null,
    val declaredDate: String? = null,
    val amount: Double? = null,
    val qualified: String? = null,
    val flag: String? = null
)