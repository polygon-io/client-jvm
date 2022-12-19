package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.Paginatable
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getConditionsBlocking] */
suspend fun PolygonReferenceClient.getConditions(
    params: ConditionsParameters,
    vararg opts: PolygonRestOption
): ConditionsResponse =
    polygonClient.fetchResult({
        path("v3", "reference", "conditions")

        params.assetClass?.let { parameters["asset_class"] = it }
        params.dataType?.let { parameters["data_type"] = it }
        params.id?.let { parameters["id"] = it.toString() }
        params.sip?.let { parameters["sip"] = it }
        params.order?.let { parameters["order"] = it }
        params.limit?.let { parameters["limit"] = it.toString() }
        params.sort?.let { parameters["sort"] = it }
    }, *opts)

@Builder
data class ConditionsParameters(

    /**
     * Filter for conditions within a given asset class.
     */
    val assetClass: String? = null,

    /**
     * Filter by data type.
     */
    val dataType: String? = null,

    /**
     * Filter for conditions with a given ID.
     */
    val id: Int? = null,

    /**
     * Filter bny SIP. If the condition contains a mapping for that SIP, the condition will be returned.
     */
    val sip: String? = null,

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
     * Field used for ordering. See docs for valid fields
     */
    val sort: String? = null
)

@Serializable
data class ConditionsResponse(
    val status: String? = null,
    @SerialName("next_url") override val nextUrl: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    override val results: List<Condition>? = null,
) : Paginatable<Condition>

@Serializable
data class Condition(
    val abbreviation: String? = null,
    @SerialName("asset_class") val assetClass: String = "",
    @SerialName("data_types") val dataTypes: List<String> = emptyList(),
    val description: String? = null,
    val exchange: Int? = null,
    val id: Int = 0,
    val legacy: Boolean = false,
    val name: String = "",
    @SerialName("sip_mapping") val sipMapping: Map<String, String> = emptyMap(),
    val type: String = "",
    @SerialName("update_rules") val updateRules: AggregateUpdateRuleSet? = null
)

@Serializable
data class AggregateUpdateRuleSet(
    @SerialName("consolidated") val consolidated: AggregateUpdateRules,
    @SerialName("market_center") val marketCenter: AggregateUpdateRules
)

@Serializable
data class AggregateUpdateRules(
    @SerialName("updates_high_low") val updatesHighLow: Boolean,
    @SerialName("updates_open_close") val updatesOpenClose: Boolean,
    @SerialName("updates_volume") val updatesVolume: Boolean
)