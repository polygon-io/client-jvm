package io.polygon.kotlin.sdk.rest.experimental

import io.polygon.kotlin.sdk.ext.coroutineToRestCallback
import io.polygon.kotlin.sdk.rest.*
import kotlinx.coroutines.runBlocking

/**
 * Client for Polygon.io's experimental RESTful APIs.
 * This is a place for any experimental APIs that we may be supporting.
 * The API contracts in this class should be considered unstable;
 * breaking changes may appear outside of major version updates to this library.
 *
 * You should access this client through [PolygonRestClient]
 */
@ExperimentalAPI
class PolygonExperimentalClient
internal constructor(internal val polygonClient: PolygonRestClient) {

    /**
     * Get historical financial data for a stock ticker.
     * The financials data is extracted from XBRL from company SEC filings.
     *
     * This API is experimental. The contract may change without a major version update.
     */
    @SafeVarargs
    @ExperimentalAPI
    fun getFinancialsBlocking(params: FinancialsParameters, vararg opts: PolygonRestOption): FinancialsResponse =
        runBlocking { getFinancials(params, *opts) }

    /** See [getFinancialsBlocking] */
    @SafeVarargs
    @ExperimentalAPI
    fun getFinancials(
        params: FinancialsParameters,
        callback: PolygonRestApiCallback<FinancialsResponse>,
        vararg opts: PolygonRestOption
    ) =
        coroutineToRestCallback(callback, { getFinancials(params, *opts) })

    /**
     * Get an iterator to iterate through all pages of results for the given parameters.
     *
     * See [getFinancialsBlocking] if you instead need to get exactly one page of results.
     * See section "Pagination" in the README for more details on iterators.
     */
    @SafeVarargs
    @ExperimentalAPI
    fun listFinancials(params: FinancialsParameters, vararg opts: PolygonRestOption): RequestIterator<Financials> =
        RequestIterator(
            { getFinancialsBlocking(params, *opts) },
            polygonClient.requestIteratorFetch<FinancialsResponse>(*opts)
        )

    /**
     * Get a timeline of events for the entity associated with the given ticker,
     * CUSIP, or Composite FIGI.
     *
     * This API is experimental. The contract may change without a major version update.
     */
	@SafeVarargs
	@ExperimentalAPI
	fun getTickerEventsBlocking(id: String, types: String? = null, vararg opts: PolygonRestOption): TickerEventsResponse =
	    runBlocking { getTickerEvents(id, types, *opts) }

	/** See [getTickerEventsBlocking] */
	@SafeVarargs
	@ExperimentalAPI
	fun getTickerEvents(
	    id: String,
	    types: String? = null,
	    callback: PolygonRestApiCallback<TickerEventsResponse>,
	    vararg opts: PolygonRestOption
	) =
	    coroutineToRestCallback(callback, { getTickerEvents(id, types, *opts) })

}
