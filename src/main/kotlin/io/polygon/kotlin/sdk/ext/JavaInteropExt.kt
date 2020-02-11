package io.polygon.kotlin.sdk.ext

import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceRestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Takes in a suspend function and exposes it as an async function with callbacks
 */
internal inline fun <T> coroutineToRestCallback(
    callback: PolygonRestApiCallback<T>,
    crossinline suspendingFunc: suspend () -> T,
    coroutineScope: CoroutineScope = GlobalScope
) {
    coroutineScope.launch {
        try {
            callback.onSuccess(suspendingFunc())
        } catch (error: Throwable) {
            callback.onError(error)
        }
    }
}