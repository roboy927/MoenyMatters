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
        AccountingType.RETURNTOLENDER -> "Return to Lender"
        AccountingType.INSURANCE -> "Insurance"
        AccountingType.LOAN -> "Loan"
        AccountingType.OTHER -> "Other"
    }
}

fun toAccountingType(type: String): AccountingType {
    return when (type) {
        "Investment" -> AccountingType.INVESTMENT
        "Expense" -> AccountingType.EXPENSE
        "Borrowers" -> AccountingType.BORROWER
        "Income" -> AccountingType.INCOME
        "Lenders" -> AccountingType.LENDER
        "Loan EMI" -> AccountingType.LOANEMI
        "Return From Borrower" -> AccountingType.RETURNFROMBORROWER
        "Return to Lender" -> AccountingType.RETURNTOLENDER
        "Loan" -> AccountingType.LOAN
        "Insurance" -> AccountingType.INSURANCE
        "Other" -> AccountingType.OTHER
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