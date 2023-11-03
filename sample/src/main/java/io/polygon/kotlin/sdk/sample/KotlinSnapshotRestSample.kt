package io.polygon.kotlin.sdk.sample

import com.tylerthrailkill.helpers.prettyprint.pp
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import io.polygon.kotlin.sdk.rest.SnapshotsParameters

/**
 * Universal Snapshot Endpoint
 *     https://polygon.io/docs/stocks/get_v3_snapshot
 *     https://polygon.io/docs/options/get_v3_snapshot
 *     https://polygon.io/docs/indices/get_v3_snapshot
 *     https://polygon.io/docs/forex/get_v3_snapshot
 *     https://polygon.io/docs/crypto/get_v3_snapshot
 **/
fun universalSnapshot(polygonClient: PolygonRestClient) {
    val tickers = listOf("NCLH", "O:SPY250321C00380000", "C:EURUSD", "X:BTCUSD", "I:SPX")
    println("Snapshots: $tickers")
    val params = SnapshotsParameters(tickers = tickers)
    polygonClient.getSnapshotsBlocking(params).pp()
}
