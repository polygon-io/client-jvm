package io.polygon.kotlin.sdk.rest.reference

import com.thinkinglogic.builder.annotation.Builder
import io.ktor.http.*
import io.polygon.kotlin.sdk.rest.PolygonRestOption
import kotlinx.serialization.Serializable

/** See [PolygonReferenceClient.getStockFinancialsBlocking] */
suspend fun PolygonReferenceClient.getStockFinancials(
    params: StockFinancialsParameters,
    vararg opts: PolygonRestOption
): StockFinancialsResultsDTO =
    polygonClient.fetchResult({
        path("v2", "reference", "financials", params.symbol)

        params.limit?.let { parameters["limit"] = it.toString() }
        params.type?.let { parameters["type"] = it }
        params.sort?.let { parameters["sort"] = it }
    }, *opts)

@Builder
data class StockFinancialsParameters(
    val symbol: String,
    val limit: Int? = null,
    val type: String? = null,
    val sort: String? = null
)

@Serializable
data class StockFinancialsResultsDTO(
    val status: String? = null,
    val count: Int? = null,
    val results: List<StockFinancialsDTO> = emptyList()
)

@Serializable
data class StockFinancialsDTO(
    val ticker: String? = null,
    val period: String? = null,
    val calendarDate: String? = null,
    val reportPeriod: String? = null,
    val updated: String? = null,
    val accumulatedOtherComprehensiveIncome: Double? = null,
    val assets: Double? = null,
    val assetsAverage: Double? = null,
    val assetsCurrent: Double? = null,
    val assetTurnover: Double? = null,
    val assetsNonCurrent: Double? = null,
    val bookValuePerShare: Double? = null,
    val capitalExpenditure: Double? = null,
    val cashAndEquivalents: Double? = null,
    val cashAndEquivalentsUSD: Double? = null,
    val costOfRevenue: Double? = null,
    val consolidatedIncome: Double? = null,
    val currentRatio: Double? = null,
    val debtToEquityRatio: Double? = null,
    val debt: Double? = null,
    val debtCurrent: Double? = null,
    val debtNonCurrent: Double? = null,
    val debtUSD: Double? = null,
    val deferredRevenue: Double? = null,
    val depreciationAmortizationAndAccretion: Double? = null,
    val deposits: Double? = null,
    val dividendYield: Double? = null,
    val dividendsPerBasicCommonShare: Double? = null,
    val earningBeforeInterestTaxes: Double? = null,
    val earningsBeforeInterestTaxesDepreciationAmortization: Double? = null,
    val EBITDAMargin: Double? = null,
    val earningsBeforeInterestTaxesDepreciationAmortizationUSD: Double? = null,
    val earningBeforeInterestTaxesUSD: Double? = null,
    val earningsBeforeTax: Double? = null,
    val earningsPerBasicShare: Double? = null,
    val earningsPerDilutedShare: Double? = null,
    val earningsPerBasicShareUSD: Double? = null,
    val shareholdersEquity: Double? = null,
    val averageEquity: Double? = null,
    val shareholdersEquityUSD: Double? = null,
    val enterpriseValue: Double? = null,
    val enterpriseValueOverEBIT: Double? = null,
    val enterpriseValueOverEBITDA: Double? = null,
    val freeCashFlow: Double? = null,
    val freeCashFlowPerShare: Double? = null,
    val foreignCurrencyUSDExchangeRate: Double? = null,
    val grossProfit: Double? = null,
    val grossMargin: Double? = null,
    val goodwillAndIntangibleAssets: Double? = null,
    val interestExpense: Double? = null,
    val investedCapital: Double? = null,
    val investedCapitalAverage: Double? = null,
    val inventory: Double? = null,
    val investments: Double? = null,
    val investmentsCurrent: Double? = null,
    val investmentsNonCurrent: Double? = null,
    val totalLiabilities: Double? = null,
    val currentLiabilities: Double? = null,
    val liabilitiesNonCurrent: Double? = null,
    val marketCapitalization: Double? = null,
    val netCashFlow: Double? = null,
    val netCashFlowBusinessAcquisitionsDisposals: Double? = null,
    val issuanceEquityShares: Double? = null,
    val issuanceDebtSecurities: Double? = null,
    val paymentDividendsOtherCashDistributions: Double? = null,
    val netCashFlowFromFinancing: Double? = null,
    val netCashFlowFromInvesting: Double? = null,
    val netCashFlowInvestmentAcquisitionsDisposals: Double? = null,
    val netCashFlowFromOperations: Double? = null,
    val effectOfExchangeRateChangesOnCash: Double? = null,
    val netIncome: Double? = null,
    val netIncomeCommonStock: Double? = null,
    val netIncomeCommonStockUSD: Double? = null,
    val netLossIncomeFromDiscontinuedOperations: Double? = null,
    val netIncomeToNonControllingInterests: Double? = null,
    val profitMargin: Double? = null,
    val operatingExpenses: Double? = null,
    val operatingIncome: Double? = null,
    val tradeAndNonTradePayables: Double? = null,
    val payoutRatio: Double? = null,
    val priceToBookValue: Double? = null,
    val priceEarnings: Double? = null,
    val priceToEarningsRatio: Double? = null,
    val propertyPlantEquipmentNet: Double? = null,
    val preferredDividendsIncomeStatementImpact: Double? = null,
    val sharePriceAdjustedClose: Double? = null,
    val priceSales: Double? = null,
    val priceToSalesRatio: Double? = null,
    val tradeAndNonTradeReceivables: Double? = null,
    val accumulatedRetainedEarningsDeficit: Double? = null,
    val revenues: Double? = null,
    val revenuesUSD: Double? = null,
    val researchAndDevelopmentExpense: Double? = null,
    val returnOnAverageAssets: Double? = null,
    val returnOnAverageEquity: Double? = null,
    val returnOnInvestedCapital: Double? = null,
    val returnOnSales: Double? = null,
    val shareBasedCompensation: Double? = null,
    val sellingGeneralAndAdministrativeExpense: Double? = null,
    val shareFactor: Double? = null,
    val shares: Double? = null,
    val weightedAverageShares: Double? = null,
    val weightedAverageSharesDiluted: Double? = null,
    val salesPerShare: Double? = null,
    val tangibleAssetValue: Double? = null,
    val taxAssets: Double? = null,
    val incomeTaxExpense: Double? = null,
    val taxLiabilities: Double? = null,
    val tangibleAssetsBookValuePerShare: Double? = null,
    val workingCapital: Double? = null
)