package io.polygon.kotlin.sdk.rest.options

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import io.polygon.kotlin.sdk.rest.RequestIterator
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceClient
import kotlinx.coroutines.runBlocking
import javax.management.monitor.StringMonitor

/**
 * Client for Polygon.io's Options pricing data RESTful APIs.
 * For reference data, see [PolygonReferenceClient]
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonOptionsClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * Get the snapshot of an option contract for a stock equity.
     *
     * API Doc: https://polygon.io/docs/options/get_v3_snapshot_options__underlyingasset___optioncontract
     */
    @SafeVarargs
    fun getSnapshotBlocking(
        underlyingAsset: String,
        contract: String,
        vararg opts: PolygonRestOption
    ): SnapshotResponse =
        runBlocking { getSnapshot(underlyingAsset, contract, *opts) }

    /** See [getSnapshotBlocking] */
    @SafeVarargs
    fun getSnapshot(
        underlyingAsset: String,
        contract: String,
        callback: PolygonRestApiCallback<SnapshotResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getSnapshot(underlyingAsset, contract, *opts) })

    /**
     * Get the snapshot of all options contracts for an underlying ticker.
     *
     * API Doc: https://polygon.io/docs/options/get_v3_snapshot_options__underlyingasset___optioncontract
     */
    @SafeVarargs
    fun getSnapshotChainBlocking(
        underlyingAsset: String,
        params: SnapshotChainParameters,
        vararg opts: PolygonRestOption
    ): SnapshotChainResponse =
        runBlocking { getSnapshotChain(underlyingAsset, params, *opts) }

    /** See [getSnapshotChainBlocking] */
    @SafeVarargs
    fun getSnapshotChain(
        underlyingAsset: String,
        params: SnapshotChainParameters,
        callback: PolygonRestApiCallback<SnapshotChainResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getSnapshotChain(underlyingAsset, params, *opts) })

    /**
     * Get an iterator to iterate through all pages of results for the given parameters.
     *
     * See [getSnapshotChainBlocking] if you instead need to get exactly one page of results.
     * See section "Pagination" in the README for more details on iterators.
     */
    @SafeVarargs
    fun listSnapshotChain(
        underlyingAsset: String,
        params: SnapshotChainParameters,
        vararg opts: PolygonRestOption
    ): RequestIterator<Snapshot> =
        RequestIterator(
            { getSnapshotChainBlocking(underlyingAsset, params, *opts) },
            polygonClient.requestIteratorFetch<SnapshotChainResponse>(*opts)
        )
}
