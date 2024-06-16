package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet

data class TransactionFilter(
    val month: List<String> = emptyList(),
    val accounts: List<String> = emptyList(),
    val types: List<String> = emptyList(),
    val accountingTypes: List<String> = emptyList(),
    val accountingNames: List<String> = emptyList()
)

