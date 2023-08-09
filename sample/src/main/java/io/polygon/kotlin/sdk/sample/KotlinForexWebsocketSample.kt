package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.websocket.*
import kotlinx.coroutines.delay

suspend fun forexWebsocketSample(polygonKey: String) {
    val websocketClient = PolygonWebSocketClient(
        polygonKey,
        Feed.RealTime,
        Market.Forex,
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
    	//PolygonWebSocketSubscription(PolygonWebSocketChannel.Forex.Quotes, "*"),
    	PolygonWebSocketSubscription(PolygonWebSocketChannel.Forex.AggPerSecond, "*"),
        PolygonWebSocketSubscription(PolygonWebSocketChannel.Forex.AggPerMinute, "*"),
    )

    websocketClient.connect()
    websocketClient.subscribe(subscriptions)
    delay(65_000) // make sure we get a agg minute
    websocketClient.unsubscribe(subscriptions)
    websocketClient.disconnect()
}
