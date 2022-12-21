# Polygon JVM Client SDK written in Kotlin

A client SDK for Polygon.io's API usable by any JVM language (including in Android SDK version 21+)

Supports Polygon's [REST](https://polygon.io/docs/#getting-started) and [WebSocket](https://polygon.io/sockets) APIs

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

(See GitHub releases for the current release version)

## Features
Everything you'd expect from a client SDK plus...
- Configurable HTTP client via [HttpClientProvider](src/main/kotlin/io/polygon/kotlin/sdk/HttpClientProvider.kt)
- Configurable [REST request options](#rest-request-options)
- Iterators to handle [request pagination](#pagination) automatically
- Asynchronous APIs built on top of Kotlin co-routines
- Idiomatic interoperability with Java
  - Synchronous and callback based APIs
  - Generated builder classes for API parameter data classes

## REST Request Options

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

## Sample code
See the sample module in this repo; There's a short [Kotlin sample](sample/src/main/java/io/polygon/kotlin/sdk/sample/KotlinUsageSample.kt) 
and [Java sample](sample/src/main/java/io/polygon/kotlin/sdk/sample/JavaUsageSample.java)


You can run the samples by cloning the repo and running the following commands from the repo's root directory:

Kotlin: `./gradlew kotlinSample`

Java: `./gradlew javaSample`

NOTE: If you run the samples makes sure to set the `POLYGON_API_KEY` environment variable for the sample code to use

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

### Note for Maintainers

Before cutting a new release, be sure to update [Version](./src/main/kotlin/io/polygon/kotlin/sdk/Version.kt)!
