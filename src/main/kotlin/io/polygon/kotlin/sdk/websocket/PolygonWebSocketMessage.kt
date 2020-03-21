package io.polygon.kotlin.sdk.websocket

import kotlinx.serialization.Serializable

sealed class PolygonWebSocketMessage {

    @Serializable
    data class StatusMessage(
        val ev: String? = null,
        val status: String? = null,
        val message: String? = null
    ) : PolygonWebSocketMessage()

    class RawMessage(val data: ByteArray) : PolygonWebSocketMessage()

}