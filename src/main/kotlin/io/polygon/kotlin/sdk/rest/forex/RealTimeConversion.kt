package io.polygon.kotlin.sdk.rest.forex

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonForexClient.getRealTimeConversionBlocking] */
suspend fun PolygonForexClient.getRealTimeConversion(params: RealTimeConversionParameters, vararg opts: PolygonRestOption): RealTimeConversionDTO =
    polygonClient.fetchResultWithOptions({
        path("v1", "conversion", params.fromCurrency, params.toCurrency)

        parameters["amount"] = params.amount.toString()
        parameters["precision"] = params.precision.toString()
    }, *opts)

@Builder
data class RealTimeConversionParameters(
    val fromCurrency: String,
    val toCurrency: String,

    /** The amount we want to convert. With decimal */
    val amount: Double,

    /** Decimal precision of the conversion. Defaults to 2 which is 2 decimal places accuracy. */
    @DefaultValue("2")
    val precision: Int = 2
)

@Serializable
data class RealTimeConversionDTO(
    val status: String? = null,
    val from: String? = null,
    val to: String? = null,
    val symbol: String? = null,
    val initialAmount: Double? = null,
    val converted: Double? = null,
    val last: ForexQuoteDTO = ForexQuoteDTO()
)