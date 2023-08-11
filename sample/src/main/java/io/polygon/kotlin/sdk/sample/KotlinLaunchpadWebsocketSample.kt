package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.websocket.*
import kotlinx.coroutines.delay

suspend fun launchpadWebsocketSample(polygonKey: String) {
    val websocketClient = PolygonWebSocketClient(
        polygonKey,
        Feed.LaunchpadFeed,
        Market.LaunchpadStocks, // replace with LaunchpadCrypto, LaunchpadForex, LaunchpadForex, etc
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
        PolygonWebSocketSubscription(PolygonWebSocketChannel.LaunchpadStocks.LaunchpadValue, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.LaunchpadStocks.AggPerMinute, "*")
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.c.LaunchpadValue, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.LaunchpadOptions.AggPerMinute, "*")
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.LaunchpadForex.LaunchpadValue, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.LaunchpadForex.AggPerMinute, "*")
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.LaunchpadCrypto.LaunchpadValue, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.c.AggPerMinute, "*")
    )

    websocketClient.connect()
    websocketClient.subscribe(subscriptions)
    delay(65_000)
    websocketClient.unsubscribe(subscriptions)
    websocketClient.disconnect()
}
