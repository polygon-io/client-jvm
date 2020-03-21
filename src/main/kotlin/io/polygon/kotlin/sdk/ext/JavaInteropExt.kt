package io.polygon.kotlin.sdk.ext

import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
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

/**
 * Take a suspend function with no return value and exposes it as an async function with callbacks
 */
internal inline fun coroutineToCompletionCallback(
    callback: PolygonCompletionCallback?,
    coroutineScope: CoroutineScope,
    crossinline suspendingFunc: suspend () -> Unit
) {
    coroutineScope.launch {
        try {
            suspendingFunc()
            callback?.onComplete()
        } catch (error: Throwable) {
            callback?.onError(error)
        }
    }
}

interface PolygonCompletionCallback {
    fun onComplete()
    fun onError(error: Throwable)
}