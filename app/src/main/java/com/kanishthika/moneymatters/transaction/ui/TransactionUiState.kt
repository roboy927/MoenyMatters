package com.kanishthika.moneymatters.transaction.ui

import com.kanishthika.moneymatters.toUIString
import com.kanishthika.moneymatters.transaction.TransactionType
import java.time.LocalDate

data class TransactionUiState (
    val date: String = LocalDate.now().toUIString(),
    val account: String = "",
    val description: String = "",
    val amount: String = "",
    val transactionType: TransactionType = TransactionType.DEBIT,
    val accountingType: String = ""
)