package com.kanishthika.moneymatters.display.accounting.type.borrower.ui

import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.BaseFinancialModel
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BorrowerModel @Inject constructor(
    repository: FinancialRepository<Borrower>,
    transactionRepository: TransactionRepository
) : BaseFinancialModel<Borrower>(repository, transactionRepository) {

    override fun getAccountingType(): AccountingType = AccountingType.INVESTMENT

    override fun createNewItem(): Borrower {
        return Borrower(
            id = 0,
            borrowerName = capitalizeWords(uiState.value.name),
            amount = uiState.value.amount.toDouble(),
            borrowerContactNumber = capitalizeWords(uiState.value.description)
        )
    }
}