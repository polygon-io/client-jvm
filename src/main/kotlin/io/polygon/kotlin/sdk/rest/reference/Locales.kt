package io.polygon.kotlin.sdk.rest.reference

import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getSupportedLocalesBlocking] */
@SafeVarargs
@Deprecated("This API is no longer supported and will be sunset some time in the future")
suspend fun PolygonReferenceClient.getSupportedLocales(vararg opts: PolygonRestOption): LocalesDTO =
    polygonClient.fetchResult({
        path("v2", "reference", "locales")
    }, *opts)

@Serializable
@Deprecated("used in deprecated getSupportedLocales")
data class LocalesDTO(
    val status: String? = null,
    val results: List<LocaleDTO> = emptyList()
)

@Serializable
@Deprecated("used in deprecated getSupportedLocales")
data class LocaleDTO(
    val locale: String? = null,
    val name: String? = null
)