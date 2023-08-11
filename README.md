# Polygon JVM Client SDK written in Kotlin - WebSocket & RESTful APIs

Welcome to the official JVM client library SDK, written in Kotlin, for the [Polygon](https://polygon.io/) REST and WebSocket API. This client SDK is usable by any JVM language (including in Android SDK version 21+). To get started, please see the [Getting Started](https://polygon.io/docs/stocks/getting-started) section in our documentation, and the [sample](./sample/src/main/java/io/polygon/kotlin/sdk/sample/) directory for code snippets.

## Install

To use the SDK in a Gradle project:
```groovy
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.polygon-io:client-jvm:vX.Y.Z' 
}
```

Please see the GitHub releases for the current release version.

## Getting started

To access real-time and historical market data with Polygon.io, you will first need to create an account and obtain an API key to authenticate your requests. If you run the samples makes sure to set the `POLYGON_API_KEY` environment variable for the sample code to use. To persist the environment variable you need to add the above command to the shell startup script (e.g. .bashrc or .bash_profile.

```bash
setx POLYGON_API_KEY "<your_api_key>"   # windows
export POLYGON_API_KEY="<your_api_key>" # mac/linux
```

After setup, you can start using our SDK, which includes a rich set of features:
- Configurable HTTP client via [HttpClientProvider](src/main/kotlin/io/polygon/kotlin/sdk/HttpClientProvider.kt)
- Configurable [REST request options](#rest-request-options)
- Iterators to handle [request pagination](#pagination) automatically
- Asynchronous APIs built on top of Kotlin co-routines
- Idiomatic interoperability with Java
  - Synchronous and callback based APIs
  - Generated builder classes for API parameter data classes

Please see the [sample](./sample/src/main/java/io/polygon/kotlin/sdk/sample/) module in this repo for examples. There are [Kotlin samples](sample/src/main/java/io/polygon/kotlin/sdk/sample/KotlinUsageSample.kt) and [Java sample](./sample/src/main/java/io/polygon/kotlin/sdk/sample/JavaUsageSample.java). You can run the samples by cloning the repo and running the following commands from the repo's root directory:

Kotlin: `./gradlew kotlinSample`

Java: `./gradlew javaSample`



## REST API Client

The [REST API](https://polygon.io/docs/stocks/getting-started) provides endpoints that let you query the latest stock, options, indices, forex, and crypto market data market data. You can request data using client methods.

Create the client using your API key.

```kotlin
    val polygonKey = System.getenv("POLYGON_API_KEY")

    val polygonClient = PolygonRestClient(
        polygonKey, 
        httpClientProvider = okHttpClientProvider
    )
```

Get [aggregate bars](https://polygon.io/docs/stocks/get_v2_aggs_ticker__stocksticker__range__multiplier___timespan___from___to) for a stock over a given date range in custom time window sizes.

```kotlin
    println("AAPL Aggs:")
    val params = AggregatesParameters(
        ticker = "AAPL",
        timespan = "day",
        fromDate = "2023-07-03",
        toDate = "2023-07-07",
        limit = 50_000,
    )
    polygonClient.getAggregatesBlocking(params)
```

Get [trades](https://polygon.io/docs/stocks/get_v3_trades__stockticker) for a ticker symbol in a given time range.

```kotlin
    println("AAPL Trades:")
    val params = TradesParameters(timestamp = ComparisonQueryFilterParameters.equal("2023-02-01"), limit = 2)
    polygonClient.getTradesBlocking("AAPL", params)
```

Get the [last trade](https://polygon.io/docs/stocks/get_v2_last_trade__stocksticker) for a given stock.

```kotlin
    println("Last AAPL trade:")
    polygonClient.stocksClient.getLastTradeBlockingV2("AAPL")
```

Get the NBBO [quotes](https://polygon.io/docs/stocks/get_v3_quotes__stockticker) for a ticker symbol in a given time range.

```kotlin
    println("AAPL Quotes:")
    val params = QuotesParameters(timestamp = ComparisonQueryFilterParameters.equal("2023-02-01"), limit = 2)
    polygonClient.getQuotesBlocking("AAPL", params)
```

Get the last NBBO [quote](https://polygon.io/docs/stocks/get_v2_last_nbbo__stocksticker) for a given stock.

```kotlin
    println("Last AAPL quote:")
    polygonClient.stocksClient.getLastQuoteBlockingV2("AAPL")
```

Please see more detailed code in the [sample](./sample/src/main/java/io/polygon/kotlin/sdk/sample/) directory.

## WebSocket Client

The [WebSocket API](https://polygon.io/docs/stocks/ws_getting-started) provides streaming access to the latest stock, options, indices, forex, and crypto market data. You can specify which channels you want to consume by sending instructions in the form of actions. Our WebSockets emit events to notify you when an event has occurred in a channel you've subscribed to.

Our WebSocket APIs are based on entitlements that control which WebSocket Clusters you can connect to and which kinds of data you can access. You can [login](https://polygon.io/) to see examples that include your API key and are personalized to your entitlements.

```kotlin
package io.polygon.kotlin.sdk.sample

import io.polygon.kotlin.sdk.websocket.*
import kotlinx.coroutines.delay

suspend fun stocksWebsocketSample(polygonKey: String) {
    val websocketClient = PolygonWebSocketClient(
        polygonKey,
        Feed.RealTime,
        Market.Stocks,
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
        PolygonWebSocketSubscription(PolygonWebSocketChannel.Stocks.Trades, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Stocks.Quotes, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Stocks.AggPerSecond, "*"),
        //PolygonWebSocketSubscription(PolygonWebSocketChannel.Stocks.AggPerMinute, "*")
    )

    websocketClient.connect()
    websocketClient.subscribe(subscriptions)
    delay(65_000)
    websocketClient.unsubscribe(subscriptions)
    websocketClient.disconnect()
}
```

Please see more detailed code in the [sample](./sample/src/main/java/io/polygon/kotlin/sdk/sample/) directory.

## REST Request Options

_Added in v4.1.0_

For most use-cases, the default request options will do the trick, 
but if you need some more flexibility on a per-request basis, 
you can configure individual REST API requests with `PolygonRestOption`s.

For more info on what you can configure with request options, see docs in 
[PolygonRequestOptions.kt](src/main/kotlin/io/polygon/kotlin/sdk/rest/PolygonRestOptions.kt).

In particular if you're an enterprise Launchpad user, see the helper function 
`withEdgeHeaders` in [PolygonRequestOptions.kt](src/main/kotlin/io/polygon/kotlin/sdk/rest/PolygonRestOptions.kt).

## Pagination

The Polygon REST API supports pagination for APIs that return a list of results.
For more information on how pagination works at the API level, see [this blog post](https://polygon.io/blog/api-pagination-patterns/),
but note that as a user of this SDK, you won't need to worry about the details at the API level.

This SDK offers iterators that support automatically fetching subsequent pages of results.
Functions that begin with `list` (such as [`PolygonReferenceClient.listSupportedTickers`](src/main/kotlin/io/polygon/kotlin/sdk/rest/reference/PolygonReferenceClient.kt))
will return a [`RequestIterator`](src/main/kotlin/io/polygon/kotlin/sdk/rest/RequestIterator.kt)
which implements `Iterator` so you can use it to iterate over all results in a dataset even if it means making additional requests for extra pages of results.

Note that `RequestIterator`s are lazy and will only make API requests when necessary.
That means that calling a `list*` function does not block and does not make any API requests until the first call to 
`hasNext` on the returned iterator.

See [KotlinIteratorSample.kt](sample/src/main/java/io/polygon/kotlin/sdk/sample/KotlinIteratorSample.kt)
and [JavaIteratorSample.java](sample/src/main/java/io/polygon/kotlin/sdk/sample/JavaIteratorSample.java)
for example usages of iterators.

### How does the `limit` param affect request iterators?
`limit` effectively sets the page size for your request iterator.
For example setting `limit=10` in the params would mean the iterator 
receives 10 results from the Polygon API at time.

### How many API requests will my request iterator make?
In most cases, it's not possible to know ahead of time how many requests an iterator will
make to receive everything in a dataset.
This is because we usually won't know how many total results to expect.

In general, an iterator will make `num_total_results / page_size` requests to the Polygon API.
Where `page_size` is determined by the `limit` request parameter (discussed above).

## Release planning
This client will attempt to follow the release cadence of our API. 
When endpoints are deprecated and newer versions are added, 
the client will maintain two methods in a backwards compatible way 
(e.g. listTrades and listTradesV4(...)). 
When deprecated endpoints are removed from the API, 
we'll rename the versioned method (e.g. listTradesV4(...) -> listTrades(...)), 
remove the old method, and release a new major version of the client. 
The goal is to give users ample time to upgrade to newer versions of our API 
before we bump the major version of the client, and in general, 
we'll try to bundle breaking changes like this to avoid frequent major version bumps.

There are a couple exceptions to this. 
When we find small breaking issues with this client library (e.g. incorrect response types), 
we may decide to release them under the same major version. 
These changes will be clearly outlined in the release notes. 
Also, methods that fall under the Experimental client (and annotated with `@ExperimentalAPI`) 
are considered experimental and may be modified or deprecated as needed. 
We'll call out any breaking changes to VX endpoints in our release notes to make using them easier.

## Contributing
If you found a bug or have an idea for a new feature, 
please first discuss it with us by submitting a new issue. 
We will respond to issues within at most 3 weeks. 
We're also open to volunteers if you want to submit a PR for any open issues
but please discuss it with us beforehand. 
PRs that aren't linked to an existing issue or discussed with us ahead of time will generally be declined. 
If you have more general feedback or want to discuss using this client with other users, 
feel free to reach out on our Slack channel.

### Notes for Maintainers

Before cutting a new release, be sure to update [Version](./src/main/kotlin/io/polygon/kotlin/sdk/Version.kt)!

Inside the `.polygon` directory there are openapi specs for REST and websocket APIs.
Keep these specs up to date with what the library currently supports. 
We use these specs to automatically check if a new API or feature has been released that this client doesn't yet support.

To manually update the specs to the latest version, run `./gradlew restSpec websocketSpec`
