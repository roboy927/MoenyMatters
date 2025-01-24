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
    val monthlyAmounts: Map<String, Double> = emptyMap(),

    val amountViewType: AmountViewType = AmountViewType.MONTH,
    val monthText: String = "",
    val yearText: String = "",
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
