package com.kanishthika.moneymatters.display.accounting.type.lenders.ui

import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.Lender
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.BaseFinancialModel
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LenderModel @Inject constructor(
    repository: FinancialRepository<Lender>,
    transactionRepository: TransactionRepository
) : BaseFinancialModel<Lender>(repository, transactionRepository) {

    override fun getAccountingType(): AccountingType = AccountingType.INVESTMENT

    override fun createNewItem(): Lender {
        return Lender(
            id = 0,
            lenderName = capitalizeWords(uiState.value.name),
            amount = uiState.value.amount.toDouble(),
            lenderContactNumber = capitalizeWords(uiState.value.description)
        )
    }
}