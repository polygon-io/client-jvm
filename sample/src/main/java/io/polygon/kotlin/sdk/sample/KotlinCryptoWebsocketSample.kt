package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.websocket.*
import kotlinx.coroutines.delay

suspend fun cryptoWebsocketSample(polygonKey: String) {
    val websocketClient = PolygonWebSocketClient(
        polygonKey,
        Feed.RealTime,
        Market.Crypto,
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
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.Trades, "ETH-USD"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.Trades, "BTC-USD")
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.Trades, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.Quotes, "*"),
        PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.AggPerSecond, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.AggPerMinute, "*")
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.Level2Books, "*")
    )

    websocketClient.connect()
    websocketClient.subscribe(subscriptions)
    delay(65_000)
    websocketClient.unsubscribe(subscriptions)
    websocketClient.disconnect()
}
