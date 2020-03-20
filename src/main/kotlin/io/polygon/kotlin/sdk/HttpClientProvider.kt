package io.polygon.kotlin.sdk

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.websocket.WebSockets
import io.ktor.http.DEFAULT_PORT
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json

interface HttpClientProvider {
    fun buildClient(): HttpClient

    fun getDefaultRestURLBuilder(): URLBuilder
}

open class DefaultJvmHttpClientProvider : HttpClientProvider {
    override fun buildClient() =
        HttpClient(OkHttp) {
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json.nonstrict)
            }
        }

    override fun getDefaultRestURLBuilder() =
        URLBuilder(
            protocol = URLProtocol.HTTPS,
            port = DEFAULT_PORT
        )
}