# Polygon Kotlin Client SDK

A client SDK for Polygon.io's API usable by any JVM language (including in Android SDK version 21+)

Supports Polygon's [REST](https://polygon.io/docs/#getting-started) and [WebSocket](https://polygon.io/sockets) APIs

To use the SDK in a Gradle project:
```groovy
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.mmoghaddam385:polygon-kotlin-client:1.0' 
}
```

## Features
Everything you'd expect from a client SDK plus...
- Configurable HTTP client via [HttpClientProvider](src/main/kotlin/io/polygon/kotlin/sdk/HttpClientProvider.kt)
- Asynchronous APIs built on top of Kotlin co-routines
- Idiomatic interoperability with Java
  - Synchronous and callback based APIs
  - Generated builder classes for API parameter data classes

## Sample code
See the sample module in this repo; There's a short [Kotlin sample](sample/src/main/java/io/polygon/kotlin/sdk/sample/KotlinUsageSample.kt) 
and [Java sample](sample/src/main/java/io/polygon/kotlin/sdk/sample/JavaUsageSample.java)


You can run the samples by cloning the repo and running the following commands from the repo's root directory:

Kotlin: `./gradlew kotlinSample`

Java: `./gradlew javaSample`

NOTE: If you run the samples makes sure to set the `POLYGON_API_KEY` environment variable for the sample code to use
