package io.polygon.kotlin.sdk.rest.stocks

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonStocksClient.getLastQuoteBlocking] */
suspend fun PolygonStocksClient.getLastQuote(symbol: String, vararg opts: PolygonRestOption): LastQuoteResultDTO =
    polygonClient.fetchResult({ path("v1", "last_quote", "stocks", symbol) }, *opts)

@Serializable
data class LastQuoteResultDTO(
    val status: String? = null,
    val symbol: String? = null,
    @SerialName("last") val lastQuote: LastQuoteDTO = LastQuoteDTO()
)

@Serializable
data class LastQuoteDTO(
    @SerialName("askprice") val askPrice: Double? = null,
    @SerialName("asksize") val askSize: Long? = null,
    @SerialName("askexchange") val askExchangeId: Long? = null,
    @SerialName("bidprice") val bidPrice: Double? = null,
    @SerialName("bidsize") val bidSize: Long? = null,
    @SerialName("bidexchange") val bidExchangeId: Long? = null,
    val timestamp: Long? = null
)