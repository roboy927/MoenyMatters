package com.kanishthika.moneymatters.accounting


sealed class AccountingType {
    object INVESTMENT : AccountingType()
    object EXPENSE : AccountingType()
    object BORROWERS : AccountingType()
    object INCOME : AccountingType()
    object LENDERS : AccountingType()
}