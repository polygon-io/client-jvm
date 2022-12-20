package io.polygon.kotlin.sdk.rest.crypto

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonCryptoClient.getSupportedExchangesBlocking] */
@SafeVarargs
suspend fun PolygonCryptoClient.getSupportedExchanges(vararg opts: PolygonRestOption): List<CryptoExchangeDTO> =
    polygonClient.fetchResult({ path("v1", "meta", "crypto-exchanges") }, *opts)

@Serializable
data class CryptoExchangeDTO(
    val id: Long? = null,
    val type: String? = null,
    val market: String? = null,
    val name: String? = null,
    val url: String? = null
)