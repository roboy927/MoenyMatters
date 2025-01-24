package com.kanishthika.moneymatters.display.accounting.type.income.ui

import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import com.kanishthika.moneymatters.display.accounting.type.income.data.Income
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.BaseFinancialModel
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IncomeModel @Inject constructor(
    repository: FinancialRepository<Income>,
    transactionRepository: TransactionRepository
) : BaseFinancialModel<Income>(repository, transactionRepository) {

    override fun getAccountingType(): AccountingType = AccountingType.INCOME

    override fun createNewItem(): Income {
        return Income(
            id = 0,
            name = capitalizeWords(uiState.value.name),
            amount = uiState.value.amount.toDouble(),
            description = capitalizeWords(uiState.value.description)
        )
    }
}