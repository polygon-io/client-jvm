package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getLastTradeBlocking] */
suspend fun PolygonStocksClient.getLastTrade(symbol: String, vararg opts: PolygonRestOption): LastTradeResultDTO =
    polygonClient.fetchResultWithOptions({ path("v1", "last", "stocks", symbol) }, *opts)

@Serializable
data class LastTradeResultDTO(
    val status: String? = null,
    val symbol: String? = null,
    @SerialName("last") val lastTrade: LastTradeDTO? = LastTradeDTO()
)

@Serializable
data class LastTradeDTO(
    val price: Double? = null,
    val size: Long? = null,
    val exchange: Long? = null,
    val cond1: Long? = null,
    val cond2: Long? = null,
    val cond3: Long? = null,
    val cond4: Long? = null,
    val timestamp: Long? = null
)