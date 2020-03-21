package io.polygon.kotlin.sdk.websocket

interface PolygonWebSocketListener {
    fun onAuthenticated(client: PolygonWebSocketClient)
    fun onReceive(client: PolygonWebSocketClient, message: PolygonWebSocketMessage)
    fun onDisconnect(client: PolygonWebSocketClient)
    fun onError(client: PolygonWebSocketClient, error: Throwable)
}

/**
 * A default implementation of [PolygonWebSocketListener] with stubbed implementations for each method.
 *
 * Extend this class to implement only the callbacks you care about
 */
open class DefaultPolygonWebSocketListener : PolygonWebSocketListener {
    override fun onAuthenticated(client: PolygonWebSocketClient) { }
    override fun onReceive(client: PolygonWebSocketClient, message: PolygonWebSocketMessage) { }
    override fun onDisconnect(client: PolygonWebSocketClient) { }
    override fun onError(client: PolygonWebSocketClient, error: Throwable) { }
}