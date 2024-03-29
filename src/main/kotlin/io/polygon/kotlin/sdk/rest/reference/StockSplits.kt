package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getStockSplitsBlocking] */
@SafeVarargs
@Deprecated("use getSplits or listSplits instead", ReplaceWith("getSplits(params, *opts)"))
suspend fun PolygonReferenceClient.getStockSplits(symbol: String, vararg opts: PolygonRestOption): StockSplitsDTO =
    polygonClient.fetchResult({
        path("v2", "reference", "splits", symbol)
    }, *opts)

@Serializable
@Deprecated("used in deprecated getStocksSplits")
data class StockSplitsDTO(
    val status: String? = null,
    val count: Int? = null,
    val results: List<StockSplitDTO> = emptyList()
)

@Serializable
@Deprecated("used in deprecated getStocksSplits")
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