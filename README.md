# Polygon Kotlin Client SDK (WIP)

A client SDK for Polygon.io's API usable by any JVM language

## Features
Everything you'd expect from a client SDK plus...
- Configurable HTTP client via [HttpClientProvider](src/main/kotlin/io/polygon/kotlin/sdk/HttpClientProvider.kt)
- Asynchronous APIs built on top of Kotlin co-routines
- Idiomatic interoperability with Java via synchronous and callback based APIs

## Sample code
See the sample module in this repo; There's a short [Kotlin sample](sample/src/main/java/io/polygon/kotlin/sdk/sample/KotlinUsageSample.kt) 
and [Java sample](sample/src/main/java/io/polygon/kotlin/sdk/sample/JavaUsageSample.java)


You can run the samples by cloning the repo and running the following commands from the repo's root directory:

Kotlin: `./gradlew kotlinSample`

Java: `./gradlew javaSample`

NOTE: If you run the samples makes sure to set the `POLYGON_API_KEY` environment variable for the sample code to use
