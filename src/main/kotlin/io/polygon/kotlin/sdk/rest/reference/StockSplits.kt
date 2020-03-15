package io.polygon.kotlin.sdk.rest.reference

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getStockSplitsBlocking] */
suspend fun PolygonReferenceClient.getStockSplits(symbol: String): StockSplitsDTO =
    polygonClient.fetchResult {
        path("v2", "reference", "splits", symbol)
    }

@Serializable
data class StockSplitsDTO(
    val status: String? = null,
    val count: Int? = null,
    val results: List<StockSplitDTO> = emptyList()
)

@Serializable
data class StockSplitDTO(
    val ticker: String? = null,
    @SerialName("exDate") val executionDate: String? = null,
    val paymentDate: String? = null,
    val recordDate: String? = null,
    val declaredDate: String? = null,
    val ratio: Double? = null,
    @SerialName("tofactor") val toFactor: Long? = null,
    @SerialName("fromfactor") val fromFactor: Long? = null
)