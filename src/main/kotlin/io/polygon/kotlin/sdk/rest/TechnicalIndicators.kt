package io.polygon.kotlin.sdk.rest

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonRestClient.getTechnicalIndicatorSMABlocking] */
suspend fun PolygonRestClient.getTechnicalIndicatorSMA(
    ticker: String,
    params: SMAParameters,
    vararg opts: PolygonRestOption
): TechnicalIndicatorsResponse =
    fetchResult({
        path("v1", "indicators", "sma", ticker)

        applyBaseTechnicalIndicatorParams(params.baseParameters)
        params.windowSize?.let { parameters["window_size"] = it.toString() }
    }, *opts)

/** See [PolygonRestClient.getTechnicalIndicatorEMABlocking] */
suspend fun PolygonRestClient.getTechnicalIndicatorEMA(
    ticker: String,
    params: EMAParameters,
    vararg opts: PolygonRestOption
): TechnicalIndicatorsResponse =
    fetchResult({
        path("v1", "indicators", "ema", ticker)

        applyBaseTechnicalIndicatorParams(params.baseParameters)
        params.windowSize?.let { parameters["window_size"] = it.toString() }
    }, *opts)

/** See [PolygonRestClient.getTechnicalIndicatorMACDBlocking] */
suspend fun PolygonRestClient.getTechnicalIndicatorMACD(
    ticker: String,
    params: MACDParameters,
    vararg opts: PolygonRestOption
): MACDTechnicalIndicatorsResponse =
    fetchResult({
        path("v1", "indicators", "macd", ticker)

        applyBaseTechnicalIndicatorParams(params.baseParameters)
        params.shortWindow?.let { parameters["short_window"] = it.toString() }
        params.longWindow?.let { parameters["long_window"] = it.toString() }
        params.signalWindow?.let { parameters["signal_window"] = it.toString() }
    }, *opts)

/** See [PolygonRestClient.getTechnicalIndicatorRSIBlocking] */
suspend fun PolygonRestClient.getTechnicalIndicatorRSI(
    ticker: String,
    params: RSIParameters,
    vararg opts: PolygonRestOption
): TechnicalIndicatorsResponse =
    fetchResult({
        path("v1", "indicators", "rsi", ticker)

        applyBaseTechnicalIndicatorParams(params.baseParameters)
        params.windowSize?.let { parameters["window_size"] = it.toString() }
    }, *opts)

@Builder
data class BaseTechnicalIndicatorsParameters(

    /**
     * Query by timestamp. Either a date with the format YYYY-MM-DD or a millisecond timestamp.
     */
    val timestamp: ComparisonQueryFilterParameters<String>? = null,

    /**
     * The size of the aggregate time window. Ex: "day", "minute", "quarter", etc
     */
    val timespan: String? = null,

    /**
     * Whether or not the aggregates used to calculate the simple moving average are adjusted for splits.
     * By default, aggregates are adjusted.
     * Set this to false to get results that are NOT adjusted for splits.
     */
    val adjusted: Boolean? = null,

    /**
     * The price in the aggregate which will be used to calculate the technical indicator.
     * i.e. 'close' will result in using close prices to calculate the technical indicator.
     *
     * Can be 'open', 'close', 'high' or 'low'
     * Default is 'close'.
     */
    val seriesType: String? = null,

    /**
     * Whether or not to include the aggregates used to calculate this indicator in the response.
     */
    val expandUnderlying: Boolean? = null,

    /**
     * Order results based on the sort field.
     * Can be "asc" or "desc"
     */
    val order: String? = null,

    /**
     * Limit the number of results returned, default is 10 and max is 1000.
     */
    val limit: Int? = null,

    /**
     * Field used for ordering. See API docs for valid fields
     */
    val sort: String? = null

)

internal fun URLBuilder.applyBaseTechnicalIndicatorParams(params: BaseTechnicalIndicatorsParameters?) = apply {
    applyComparisonQueryFilterParameters("timestamp", params?.timestamp)
    params?.timespan?.let { parameters["timespan"] = it }
    params?.adjusted?.let { parameters["adjusted"] = it.toString() }
    params?.seriesType?.let { parameters["series_type"] = it }
    params?.expandUnderlying?.let { parameters["expand_underlying"] = it.toString() }
    params?.order?.let { parameters["order"] = it }
    params?.limit?.let { parameters["limit"] = it.toString() }
    params?.sort?.let { parameters["sort"] = it }
}

@Builder
data class SMAParameters(
    /**
     * The window size used to calculate the technical indicator.
     */
    val windowSize: Int? = null,

    /**
     * Query parameters common to all technical indicators
     */
    val baseParameters: BaseTechnicalIndicatorsParameters? = null,
)

@Builder
data class EMAParameters(
    /**
     * The window size used to calculate the technical indicator.
     */
    val windowSize: Int? = null,

    /**
     * Query parameters common to all technical indicators
     */
    val baseParameters: BaseTechnicalIndicatorsParameters? = null,
)

@Builder
data class MACDParameters(
    /**
     * The short window size used to calculate MACD data.
     */
    val shortWindow: Int? = null,

    /**
     * The long window size used to calculate MACD data.
     */
    val longWindow: Int? = null,

    /**
     * The signal window size used to calculate MACD data.
     */
    val signalWindow: Int? = null,

    /**
     * Query parameters common to all technical indicators
     */
    val baseParameters: BaseTechnicalIndicatorsParameters? = null,
)

@Builder
data class RSIParameters(
    /**
     * The window size used to calculate the technical indicator.
     */
    val windowSize: Int? = null,

    /**
     * Query parameters common to all technical indicators
     */
    val baseParameters: BaseTechnicalIndicatorsParameters? = null,
)

@Serializable
data class TechnicalIndicatorsResponse(
    val status: String? = null,
    @SerialName("next_url") val nextUrl: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    val results: TechnicalIndicators? = null,
)

@Serializable
data class MACDTechnicalIndicatorsResponse(
    val status: String? = null,
    @SerialName("next_url") val nextUrl: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    val results: MACDTechnicalIndicators? = null,
)

@Serializable
data class TechnicalIndicators(
    val underlying: TechnicalIndicatorUnderlying? = null,
    val values: List<TechnicalIndicator>? = null,
)

@Serializable
data class MACDTechnicalIndicators(
    val underlying: TechnicalIndicatorUnderlying? = null,
    val values: List<MACDTechnicalIndicator>? = null,
)

@Serializable
data class TechnicalIndicatorUnderlying(
    val aggregates: List<AggregateDTO>? = null,
    val url: String? = null,
)

@Serializable
open class TechnicalIndicator(
    val timestamp: Long? = null,
    val value: Double? = null,
)

@Serializable
data class MACDTechnicalIndicator(
    val histogram: Double? = null,
    val signal: Double? = null,
    val timestamp: Long? = null,
    val value: Double? = null,
)
