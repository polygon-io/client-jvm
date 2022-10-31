package io.polygon.kotlin.sdk.rest.crypto

import io.ktor.http.*
import kotlinx.serialization.Serializable

/** See [PolygonCryptoClient.getSupportedExchangesBlocking] */
suspend fun PolygonCryptoClient.getSupportedExchanges(): List<CryptoExchangeDTO> =
    polygonClient.fetchResult { path("v1", "meta", "crypto-exchanges") }

@Serializable
data class CryptoExchangeDTO(
    val id: Long? = null,
    val type: String? = null,
    val market: String? = null,
    val name: String? = null,
    val url: String? = null
)