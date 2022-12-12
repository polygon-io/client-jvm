package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedLocalesBlocking] */
suspend fun PolygonReferenceClient.getSupportedLocales(vararg opts: PolygonRestOption): LocalesDTO =
    polygonClient.fetchResult({
        path("v2", "reference", "locales")
    }, *opts)

@Serializable
data class LocalesDTO(
    val status: String? = null,
    val results: List<LocaleDTO> = emptyList()
)

@Serializable
data class LocaleDTO(
    val locale: String? = null,
    val name: String? = null
)