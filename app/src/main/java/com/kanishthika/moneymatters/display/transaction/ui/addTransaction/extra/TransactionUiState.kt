package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.extra

import com.kanishthika.moneymatters.config.utils.formatTo2Decimal
import com.kanishthika.moneymatters.config.utils.toUIString
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.DivideOptions
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.SplitOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.time.LocalDate

data class TransactionUiState (
    val loadingScreen: Boolean = true,
    val date: String = LocalDate.now().toUIString(),  // dd MMMM yyyy
    val account: Account = Account(1,"", "", 0.0),
    val transactionType: TransactionType = TransactionType.DEBIT,
    val description: String = "",
    val splitOptions: SplitOptions = SplitOptions.None,
    val divideOptions: DivideOptions = DivideOptions.CUSTOM,
    val amount: String = "",
    val checkSum: Boolean = true,
    val options: Boolean = false,
    val hasReminder: Boolean = false,

    //data state
    val accountingTypeList: List<AccountingType> = emptyList(),
    val accountList: List<Account> = emptyList(),
    ){
    companion object {
        fun empty() = TransactionUiState()
    }
}

data class TransactionBodyUiState(
    val splitAmount: String = formatTo2Decimal(""),
    val splitDescription: String = "",
    val accountingType: AccountingType = AccountingType.EXPENSE,
    val accountingName: String = "",
    val financialItemList : Flow<List<FinancialItem>> = emptyFlow()
)

/*data class TransactionReminderUiState(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = ""
) {
    companion object {
        fun empty() = TransactionReminderUiState()
    }
}*/

