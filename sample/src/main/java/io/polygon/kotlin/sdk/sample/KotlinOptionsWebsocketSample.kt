package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.websocket.*
import kotlinx.coroutines.delay

suspend fun optionsWebsocketSample(polygonKey: String) {
    val websocketClient = PolygonWebSocketClient(
        polygonKey,
        Feed.RealTime,
        Market.Options,
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
        PolygonWebSocketSubscription(PolygonWebSocketChannel.Options.Trades, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Options.Quotes, "O:GOOGL230915P00067500"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Options.AggPerSecond, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Options.AggPerMinute, "*")
    )

    websocketClient.connect()
    websocketClient.subscribe(subscriptions)
    delay(65_000)
    websocketClient.unsubscribe(subscriptions)
    websocketClient.disconnect()
}
