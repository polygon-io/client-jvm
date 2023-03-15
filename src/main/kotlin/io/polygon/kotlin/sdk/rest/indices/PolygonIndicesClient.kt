package io.polygon.kotlin.sdk.rest.indices

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import io.polygon.kotlin.sdk.rest.options.SnapshotResponse
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Indices" API
 * For common pricing APIs shared across asset classes, see [PolygonRestClient].
 * For reference data, see [PolygonReferenceClient].
 *
 * This client should be accessed through [PolygonRestClient]
 */
class PolygonIndicesClient
internal constructor(internal val polygonClient: PolygonRestClient){

    /*
     * Get a Snapshot of indices data for said tickers
     *
     * API Doc: https://polygon.io/docs/indices/get_v3_snapshot_indices
     */
    @SafeVarargs
    fun getSnapshotBlocking(
        params: SnapshotIndicesParameters,
        vararg opts: PolygonRestOption
    ): SnapshotIndicesDTO =
        runBlocking { getSnapshot(params, *opts) }

    /** See [getSnapshotBlocking] */
    @SafeVarargs
    fun getSnapshot(
        params: SnapshotIndicesParameters,
        callback: PolygonRestApiCallback<SnapshotIndicesDTO>,
        vararg opts: PolygonRestOption
    ) = coroutineToRestCallback(callback, {getSnapshot(params, *opts)})
}