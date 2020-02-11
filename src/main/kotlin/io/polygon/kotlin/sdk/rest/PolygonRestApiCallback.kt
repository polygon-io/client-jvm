package io.polygon.kotlin.sdk.rest

interface PolygonRestApiCallback<T> {
    fun onSuccess(result: T)
    fun onError(error: Throwable)
}