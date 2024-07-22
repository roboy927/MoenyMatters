package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import com.kanishthika.moneymatters.config.utils.formatTo2Decimal
import com.kanishthika.moneymatters.config.utils.toUIString
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.DivideOptions
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.SplitOptions
import java.time.LocalDate

data class TransactionUiState (
    val date: String = LocalDate.now().toUIString(),  // dd MMMM yyyy
    val account: Account = Account(1,"", "", 0.0),
    val transactionType: TransactionType = TransactionType.DEBIT,
    val description: String = "Hello There, Good to see you",
    val splitOptions: SplitOptions = SplitOptions.None,
    val divideOptions: DivideOptions = DivideOptions.CUSTOM,
    val amount: String = "",

    val checkSum: Boolean = true

    ){
    companion object {
        fun empty() = TransactionUiState()
    }
}

data class TransactionBodyUiState(
    val splitAmount: String = formatTo2Decimal(""),
    val description: String = "",
    val accountingType: AccountingType = AccountingType.EXPENSE,
    val accountingName: String = "",
)

