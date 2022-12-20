package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getSupportedExchangesBlocking] */
@SafeVarargs
suspend fun PolygonStocksClient.getSupportedExchanges(vararg opts: PolygonRestOption): List<ExchangeDTO> =
    polygonClient.fetchResult({ path("v1", "meta", "exchanges") }, *opts)

@Serializable
data class ExchangeDTO(
    val id: Long? = null,
    val type: String? = null,
    val market: String? = null,
    val name: String? = null,
    val mic: String? = null,
    val tape: String? = null
)