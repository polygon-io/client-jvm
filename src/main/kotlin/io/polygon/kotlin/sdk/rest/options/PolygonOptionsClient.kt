package io.polygon.kotlin.sdk.rest.options

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import io.polygon.kotlin.sdk.rest.reference.PolygonReferenceClient
import kotlinx.coroutines.runBlocking

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

}
