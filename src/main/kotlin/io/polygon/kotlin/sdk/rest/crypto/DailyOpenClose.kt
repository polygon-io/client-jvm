package io.polygon.kotlin.sdk.rest.crypto

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonCryptoClient.getDailyOpenCloseBlocking] */
@SafeVarargs
suspend fun PolygonCryptoClient.getDailyOpenClose(
    params: CryptoDailyOpenCloseParameters,
    vararg opts: PolygonRestOption
): CryptoDailyOpenCloseDTO =
    polygonClient.fetchResult({
            path("v1", "open-close", "crypto", params.from, params.to, params.date)
        }, *opts)

@Builder
data class CryptoDailyOpenCloseParameters(
    val from: String,
    val to: String,

    /** Date of the requested open/close. Formatted YYYY-MM-DD */
    val date: String
)

@Serializable
data class CryptoDailyOpenCloseDTO(
    val symbol: String? = null,
    val isUTC: Boolean? = null,
    val day: String? = null,
    val open: Double? = null,
    val close: Double? = null,
    val openTrades: List<CryptoTickDTO> = emptyList(),
    val closingTrades: List<CryptoTickDTO> = emptyList()
)

@Serializable
data class CryptoTickDTO(
    @SerialName("p") val price: Double? = null,
    @SerialName("s") val size: Double? = null,
    @SerialName("x") val exchange: Long? = null,
    @SerialName("c") val conditions: List<Int> = emptyList(),
    @SerialName("t") val timestamp: Long? = null
)