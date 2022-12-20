package io.polygon.kotlin.sdk.rest.forex

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonForexClient.getLastQuoteBlocking] */
@SafeVarargs
suspend fun PolygonForexClient.getLastQuote(
    fromCurrency: String,
    toCurrency: String,
    vararg opts: PolygonRestOption
): LastQuoteForexDTO =
    polygonClient.fetchResult({ path("v1", "last_quote", "currencies", fromCurrency, toCurrency) }, *opts)

@Serializable
data class LastQuoteForexDTO(
    val status: String? = null,
    val symbol: String? = null,
    val last: ForexQuoteDTO = ForexQuoteDTO()
)

@Serializable
data class ForexQuoteDTO(
    val ask: Double? = null,
    val bid: Double? = null,
    val exchange: Long? = null,
    val timestamp: Long? = null
)