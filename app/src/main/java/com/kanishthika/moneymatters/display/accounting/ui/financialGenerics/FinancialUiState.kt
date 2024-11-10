package com.kanishthika.moneymatters.display.accounting.ui.financialGenerics

import com.kanishthika.moneymatters.display.accounting.data.AmountViewType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.accounting.data.PremiumMode
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceType

data class FinancialUiState<T : FinancialItem>(
    val isAmountLoading: Boolean = false,
    val isDisplayLoading: Boolean = true,
    val name: String = "",
    val amount: String = "",
    val description: String = "",
    val selectedItem: T? = null,
    val totalAmount: Map<String, Double> = emptyMap(),
    val monthlyAmounts: Map<String, Double> = emptyMap(),
    val yearlyAmount: Double = 0.0,
    val amountViewType: AmountViewType = AmountViewType.TOTAL,
    val monthText: String = "Current Month",
    val yearText: String = "Current Year",
    var isEditEnable: Boolean = true,

    //UiState for Insurance and Loan
    val type: InsuranceType = InsuranceType(0, ""),
    val providerName: String = "",
    val identicalNo:  String  = "",
    val premiumAmount: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val sumAssured: String = "",
    val premiumMode: PremiumMode = PremiumMode.YEARLY
)
