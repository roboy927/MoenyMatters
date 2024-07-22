package com.kanishthika.moneymatters.display.accounting.type.investments.ui

import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.BaseFinancialModel
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InvestmentModel @Inject constructor(
    repository: FinancialRepository<Investment>,
    transactionRepository: TransactionRepository
) : BaseFinancialModel<Investment>(repository, transactionRepository) {

    override fun getAccountingType(): AccountingType = AccountingType.INVESTMENT

    override fun createNewItem(): Investment {
        return Investment(
            id = 0,
            name = capitalizeWords(uiState.value.name),
            amount = uiState.value.amount.toDouble(),
            description = capitalizeWords(uiState.value.description)
        )
    }
}