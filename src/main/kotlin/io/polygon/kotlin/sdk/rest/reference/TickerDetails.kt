package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getTickerDetailsBlocking] */
suspend fun PolygonReferenceClient.getTickerDetails(symbol: String, vararg opts: PolygonRestOption): TickerDetailsDTO =
    polygonClient.fetchResultWithOptions({
        path("v1", "meta", "symbols", symbol, "company")
    }, *opts)

@Serializable
data class TickerDetailsDTO(
    @SerialName("logo") val logoUrl: String? = null,
    val exchange: String? = null,
    val exchangeSymbol: String? = null,
    val name: String? = null,
    val symbol: String? = null,
    val description: String? = null,
    @SerialName("updated") val lastUpdated: String? = null,
    @SerialName("listdate") val listDate: String? = null,
    val type: String? = null,
    val cik: String? = null,
    val bloomberg: String? = null,
    val figi: String? = null,
    val lei: String? = null,
    val sic: Long? = null,
    val country: String? = null,
    val industry: String? = null,
    val sector: String? = null,
    @SerialName("marketcap") val marketCap: Double? = null,
    val employees: Long? = null,
    val phone: String? = null,
    val ceo: String? = null,
    @SerialName("hq_address") val headquartersAddress: String? = null,
    @SerialName("hq_state") val headquartersState: String? = null,
    @SerialName("hq_country") val headquartersCountry: String? = null,
    val url: String? = null,
    val similar: List<String> = emptyList(),
    val tags: List<String> = emptyList()
)