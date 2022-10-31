package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getStockDividendsBlocking] */
suspend fun PolygonReferenceClient.getStockDividends(symbol: String): StockDividendsDTO =
    polygonClient.fetchResult {
        path("v2", "reference", "dividends", symbol)
    }

@Serializable
data class StockDividendsDTO(
    val status: String? = null,
    val count: Int? = null,
    val results: List<StockDividendDTO> = emptyList()
)

@Serializable
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