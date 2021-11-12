package io.polygon.kotlin.sdk

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.websocket.WebSockets
import io.ktor.http.DEFAULT_PORT
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import okhttp3.Interceptor

interface HttpClientProvider {
    fun buildClient(): HttpClient

    fun getDefaultRestURLBuilder(): URLBuilder
}

/**
 * A default [HttpClientProvider] which provides an [HttpClient] powered by the [OkHttp] engine.
 *
 * For more details on the interceptors and the difference between an application interceptor and a network interceptor,
 * see OkHttp's documentation: https://square.github.io/okhttp/interceptors/
 */
open class DefaultOkHttpClientProvider
@JvmOverloads
constructor(
    private val applicationInterceptors: List<Interceptor> = emptyList(),
    private val networkInterceptors: List<Interceptor> = emptyList()
) : DefaultJvmHttpClientProvider() {

    override fun buildEngine() =
        OkHttp.create {
            applicationInterceptors.forEach(::addInterceptor)
            networkInterceptors.forEach(::addNetworkInterceptor)
        }
}

/**
 * A default [HttpClientProvider] which provides an [HttpClient] powered by a configurable [HttpClientEngine] engine.
 * For a list of possible engines, see https://ktor.io/clients/http-client/engines.html
 *
 * Engine defaults to [OkHttp]
 *
 * @see DefaultOkHttpClientProvider
 */
open class DefaultJvmHttpClientProvider
@JvmOverloads
constructor(
    private val engine: HttpClientEngine = OkHttp.create()
) : HttpClientProvider {

    open fun buildEngine(): HttpClientEngine = engine

    override fun buildClient() =
        HttpClient(buildEngine()) {
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
0            }
        }

    override fun getDefaultRestURLBuilder() =
        URLBuilder(
            protocol = URLProtocol.HTTPS,
            port = DEFAULT_PORT
        )
}