package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import com.kanishthika.moneymatters.config.utils.toUIString
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import java.time.LocalDate

data class AddTransactionScreenState(
    val date: String = LocalDate.now().toUIString(),  // dd MMMM yyyy
    val account: Account = Account(1,"", "", 0.0),
    val transactionType: TransactionType = TransactionType.DEBIT,
    val description: String = "",
    val amount: String = "",
    val accountingType: AccountingType = AccountingType.BORROWER,
    val financialItem: String = "",
    val options: Boolean = false,
    val hasReminder: Boolean = false,
    val accountingTypeList: List<AccountingType> = emptyList(),
    val accountList: List<Account> = emptyList(),
    val financialItemList: List<FinancialItem> = emptyList(),
    val labelList: List<Label> = emptyList(),
    val selectedLabel: Label = Label(0,"","", 0.0, ""),
    val editTransaction: Transaction? = null
)

data class TransactionReminderUiState(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = ""
) {
    companion object {
        fun empty() = TransactionReminderUiState()
    }
}