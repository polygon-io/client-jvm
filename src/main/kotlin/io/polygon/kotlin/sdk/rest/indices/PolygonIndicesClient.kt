package io.polygon.kotlin.sdk.rest.indices

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Indices" API
 * For common pricing APIs shared across asset classes, see [PolygonRestClient].
 * For reference data, see [PolygonReferenceClient].
 *
 * This client should be accessed through [PolygonRestClient]
 */
class PolygonIndicesClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
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
    ) = coroutineToRestCallback(callback, { getSnapshot(params, *opts) })

    /**
     * Get the previous day close for the specified index
     *
     * @param unadjusted Set to true if the results should NOT be adjusted for splits.
     *
     * API Doc: https://polygon.io/docs/indices/get_v2_aggs_ticker__indicesticker__prev
     */
    @SafeVarargs
    fun getPreviousCloseBlocking(
        ticker: String,
        unadjusted: Boolean,
        vararg opts: PolygonRestOption
    ): PreviousCloseDTO =
        runBlocking { getPreviousClose(ticker, unadjusted, *opts) }

    /** See [getPreviousCloseBlocking] */
    @SafeVarargs
    fun getPreviousClose(
        ticker: String,
        unadjusted: Boolean,
        callback: PolygonRestApiCallback<PreviousCloseDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getPreviousClose(ticker, unadjusted, *opts) })

    /**
     * Get the open, close and afterhours prices of an index on a certain date.
     * @param date The date to get the open, close and after hours prices for (YYYY-MM-DD)
     *
     * API Doc: https://polygon.io/docs/indices/get_v1_open-close__indicesticker___date
     */
    @SafeVarargs
    fun getDailyOpenCloseBlocking(
        ticker: String,
        date: String,
        unadjusted: Boolean,
        vararg opts: PolygonRestOption
    ): DailyOpenCloseDTO =
        runBlocking { getDailyOpenClose(ticker, date, unadjusted, *opts) }

    /** See [getDailyOpenCloseBlocking] */
    @SafeVarargs
    fun getDailyOpenClose(
        ticker: String,
        date: String,
        unadjusted: Boolean,
        callback: PolygonRestApiCallback<DailyOpenCloseDTO>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getDailyOpenClose(ticker, date, unadjusted, *opts) })

}