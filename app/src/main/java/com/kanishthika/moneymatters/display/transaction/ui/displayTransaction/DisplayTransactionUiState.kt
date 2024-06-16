package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction

import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet.FirstLevelFilter

data class DisplayTransactionUiState(
    val selectedFirstLevelFilter: FirstLevelFilter = FirstLevelFilter.Account,
    val filterByAccount: List<String> = emptyList(),
    val filterByMonth: List<String> = emptyList(),
    val filterByTransactionType: List<String> = emptyList(),
    val filterByAccountingType: List<String> = emptyList(),
    val checkBoxStates: Map<FirstLevelFilter, Set<Int>> = mapOf(
        FirstLevelFilter.Account to emptySet(),
        FirstLevelFilter.Month to emptySet(),
        FirstLevelFilter.TransactionType to emptySet(),
        FirstLevelFilter.AccountingType to emptySet(),
    ),
    val checkboxCountState: Map<FirstLevelFilter, Int> = mapOf(
        FirstLevelFilter.Account to 0,
        FirstLevelFilter.Account to 0,
        FirstLevelFilter.Month to 0,
        FirstLevelFilter.TransactionType to 0,
        FirstLevelFilter.AccountingType to 0
    ),
) {
    fun makeEmpty(): DisplayTransactionUiState {
        return DisplayTransactionUiState(
            selectedFirstLevelFilter = FirstLevelFilter.Account,
            filterByAccount = emptyList(),
            filterByMonth = emptyList(),
            checkBoxStates = mapOf(
                FirstLevelFilter.Account to emptySet(),
                FirstLevelFilter.Month to emptySet(),
                FirstLevelFilter.TransactionType to emptySet(),
                FirstLevelFilter.AccountingType to emptySet()
            ),
            checkboxCountState = mapOf(
                FirstLevelFilter.Account to 0,
                FirstLevelFilter.Month to 0,
                FirstLevelFilter.TransactionType to 0,
                FirstLevelFilter.AccountingType to 0
            )
        )
    }
}
