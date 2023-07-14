package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.websocket.*
import kotlinx.coroutines.delay

suspend fun indicesWebsocketSample(polygonKey: String) {
    val websocketClient = PolygonWebSocketClient(
        polygonKey,
        PolygonWebSocketCluster.Indices,
        object : PolygonWebSocketListener {
            override fun onAuthenticated(client: PolygonWebSocketClient) {
                println("Connected!")
            }

            override fun onReceive(
                client: PolygonWebSocketClient,
                message: PolygonWebSocketMessage
            ) {
                when (message) {
                    is PolygonWebSocketMessage.RawMessage -> println(String(message.data))
                    else -> println("Receieved Message: $message")
                }
            }

            override fun onDisconnect(client: PolygonWebSocketClient) {
                println("Disconnected!")
            }

            override fun onError(client: PolygonWebSocketClient, error: Throwable) {
                println("Error: ")
                error.printStackTrace()
            }

        })

    val subscriptions = listOf(
        PolygonWebSocketSubscription(PolygonWebSocketChannel.Indices.Value, "I:NDX"),
        // Likely you will need to increase the delay call below to see Indices.Aggregates messages
        PolygonWebSocketSubscription(PolygonWebSocketChannel.Indices.Aggregates, "I:SPX")
    )

    websocketClient.connect()
    websocketClient.subscribe(subscriptions)
    delay(5000)
    websocketClient.unsubscribe(subscriptions)
    websocketClient.disconnect()
}