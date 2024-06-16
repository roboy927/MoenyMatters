package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import com.kanishthika.moneymatters.config.utils.toUIString
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import java.time.LocalDate

data class TransactionUiState (
    val date: String = LocalDate.now().toUIString(),
    val account: Account = Account(1,"", 0.0),
    val description: String = "Hello There, Good to see you",
    val amount: String = "200",
    val transactionType: TransactionType = TransactionType.DEBIT,
    val accountingType: AccountingType = AccountingType.EXPENSE,
    val accountingName: String = ""
){
    companion object {
        fun empty() = TransactionUiState()
    }
}

