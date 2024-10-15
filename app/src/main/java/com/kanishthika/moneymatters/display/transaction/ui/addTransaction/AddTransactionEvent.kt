package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionType

interface AddTransactionEvent {
    object AddTransaction: AddTransactionEvent
    data class SetDate(val date: String): AddTransactionEvent
    data class SetAccount(val account: Account): AddTransactionEvent
    data class SetTxnType(val txnType: TransactionType): AddTransactionEvent
    data class SetDescription(val description: String): AddTransactionEvent
    data class SetAmount(val amount: String): AddTransactionEvent
    data class SetFinancialType (val financialType: AccountingType): AddTransactionEvent
    data class SetFinancialItem(val financialItem: FinancialItem): AddTransactionEvent
    object ChangeOptionState: AddTransactionEvent
    data class SetReminderTitle(val reminderTitle: String): AddTransactionEvent
    data class SetReminderDescription(val reminderDescription: String): AddTransactionEvent
    data class SetReminderTime(val reminderTime: String): AddTransactionEvent
    data class SetReminderDate(val reminderDate: String): AddTransactionEvent
    object EmptyReminderState: AddTransactionEvent
    data class UpdateHasReminder(val hasReminder: Boolean): AddTransactionEvent
    data class SetLabel(val label: Label) : AddTransactionEvent
    data class UpdateTransaction(val transaction: Transaction) : AddTransactionEvent
    data class SetEditTransaction(val transaction: Transaction): AddTransactionEvent
}