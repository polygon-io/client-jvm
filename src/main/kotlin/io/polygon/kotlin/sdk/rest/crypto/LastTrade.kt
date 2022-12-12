package io.polygon.kotlin.sdk.rest.crypto

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonCryptoClient.getLastTradeBlocking] */
suspend fun PolygonCryptoClient.getLastTrade(
    from: String,
    to: String,
    vararg opts: PolygonRestOption
): CryptoLastTradeDTO = polygonClient.fetchResultWithOptions({ path("v1", "last", "crypto", from, to) }, *opts)

@Serializable
data class CryptoLastTradeDTO(
    val status: String? = null,
    val symbol: String? = null,
    val last: CryptoTradeDTO = CryptoTradeDTO(),
    val lastAverage: CryptoTradeAverageDTO = CryptoTradeAverageDTO()
)

@Serializable
data class CryptoTradeDTO(
    val price: Double? = null,
    val size: Double? = null,
    val exchange: Long? = null,
    val conditions: List<Int> = emptyList(),
    val timestamp: Long? = null
)

@Serializable
data class CryptoTradeAverageDTO(
    val avg: Double? = null,
    val tradesAveraged: Long? = null
)