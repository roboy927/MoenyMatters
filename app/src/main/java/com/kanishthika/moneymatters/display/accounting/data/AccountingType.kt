package com.kanishthika.moneymatters.display.accounting.data

sealed class AccountingType {
    object INVESTMENT : AccountingType()
    object EXPENSE : AccountingType()
    object BORROWERS : AccountingType()
    object INCOME : AccountingType()
    object LENDERS : AccountingType()
}

fun AccountingType.getName(): String {
    return when (this) {
        is AccountingType.INVESTMENT -> "Investment"
        is AccountingType.EXPENSE -> "Expense"
        is AccountingType.BORROWERS -> "Borrowers"
        is AccountingType.INCOME -> "Income"
        is AccountingType.LENDERS -> "Lenders"
    }
}

fun toAccountingType(type: String): AccountingType {
    return when (type) {
        "Investment" -> AccountingType.INVESTMENT
        "Expense" -> AccountingType.EXPENSE
        "Borrowers" -> AccountingType.BORROWERS
        "Income" -> AccountingType.INCOME
        "Lenders" -> AccountingType.LENDERS
        else -> AccountingType.INCOME
    }
}

fun getAllAccountingTypes(): List<AccountingType> {
    return listOf(
        AccountingType.INVESTMENT,
        AccountingType.EXPENSE,
        AccountingType.BORROWERS,
        AccountingType.INCOME,
        AccountingType.LENDERS
    )
}