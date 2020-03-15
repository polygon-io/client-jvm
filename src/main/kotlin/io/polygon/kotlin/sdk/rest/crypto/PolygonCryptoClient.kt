package io.polygon.kotlin.sdk.rest.crypto

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback
import io.polygon.kotlin.sdk.rest.PolygonRestClient
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's "Crypto" RESTful APIs
 *
 * You should access this client through [PolygonRestClient]
 */
class PolygonCryptoClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * List of crypto currency exchanges which are supported by Polygon.io
     *
     * API Doc: https://polygon.io/docs/#get_v1_meta_crypto-exchanges_anchor
     */
    fun getSupportedExchangesBlocking(): List<CryptoExchangeDTO> =
        runBlocking { getSupportedExchanges() }
    
    /** See [getSupportedExchangesBlocking] */
    fun getSupportedExchanges(callback: PolygonRestApiCallback<List<CryptoExchangeDTO>>) =
        coroutineToRestCallback(callback, { getSupportedExchanges() })

    /**
     * Get Last Trade Tick for a Currency Pair.
     *
     * API Doc: https://polygon.io/docs/#get_v1_last_crypto__from___to__anchor
     */
    fun getLastTradeBlocking(from: String, to: String): CryptoLastTradeDTO =
        runBlocking { getLastTrade(from, to) }
    
    /** See [getLastTradeBlocking] */
    fun getLastTrade(from: String, to: String, callback: PolygonRestApiCallback<CryptoLastTradeDTO>) =
        coroutineToRestCallback(callback, { getLastTrade(from, to) })
    
    /**
     * Get the open, close prices of a symbol on a certain day.
     *
     * API Doc: https://polygon.io/docs/#get_v1_open-close_crypto__from___to___date__anchor
     */
    fun getDailyOpenCloseBlocking(params: CryptoDailyOpenCloseParameters): CryptoDailyOpenCloseDTO =
        runBlocking { getDailyOpenClose(params) }
    
    /** See [getDailyOpenCloseBlocking] */
    fun getDailyOpenClose(params: CryptoDailyOpenCloseParameters, callback: PolygonRestApiCallback<CryptoDailyOpenCloseDTO>) =
        coroutineToRestCallback(callback, { getDailyOpenClose(params) })
    
}