package com.kanishthika.moneymatters.display.accounting.data

sealed class AccountingType {
    object INVESTMENT : AccountingType()
    object INSURANCE: AccountingType()
    object EXPENSE : AccountingType()
    object BORROWER : AccountingType()
    object INCOME : AccountingType()
    object LENDER : AccountingType()
    object LOAN: AccountingType()
    object RETURNFROMBORROWER: AccountingType()
    object RETURNTOLENDER: AccountingType()
    object LOANEMI: AccountingType()
    object OTHER: AccountingType()
    object TTS: AccountingType()
}

fun AccountingType.getName(): String {
    return when (this) {
        is AccountingType.INVESTMENT -> "Investment"
        is AccountingType.EXPENSE -> "Expense"
        is AccountingType.BORROWER -> "Borrower"
        is AccountingType.INCOME -> "Income"
        is AccountingType.LENDER -> "Lender"
        AccountingType.LOANEMI -> "Loan EMI"
        AccountingType.RETURNFROMBORROWER -> "Return From Borrower"
        AccountingType.RETURNTOLENDER -> "Return To Lender"
        AccountingType.INSURANCE -> "Insurance"
        AccountingType.LOAN -> "Loan"
        AccountingType.OTHER -> "Other"
        AccountingType.TTS -> "Tts"
    }
}

fun toAccountingType(type: String): AccountingType {
    return when (type) {
        "Investment" -> AccountingType.INVESTMENT
        "Expense" -> AccountingType.EXPENSE
        "Borrower" -> AccountingType.BORROWER
        "Income" -> AccountingType.INCOME
        "Lender" -> AccountingType.LENDER
        "Loan EMI" -> AccountingType.LOANEMI
        "Return From Borrower" -> AccountingType.RETURNFROMBORROWER
        "Return To Lender" -> AccountingType.RETURNTOLENDER
        "Loan" -> AccountingType.LOAN
        "Insurance" -> AccountingType.INSURANCE
        "Other" -> AccountingType.OTHER
        "Tts" -> AccountingType.TTS
        else -> AccountingType.INCOME
    }
}

fun getAllAccountingTypes(): List<AccountingType> {
    return listOf(
        AccountingType.INVESTMENT,
        AccountingType.EXPENSE,
        AccountingType.BORROWER,
        AccountingType.INCOME,
        AccountingType.LENDER,
        AccountingType.LOANEMI,
        AccountingType.RETURNTOLENDER,
        AccountingType.RETURNFROMBORROWER,
        AccountingType.LOAN,
        AccountingType.INSURANCE,
        AccountingType.OTHER
    )
}