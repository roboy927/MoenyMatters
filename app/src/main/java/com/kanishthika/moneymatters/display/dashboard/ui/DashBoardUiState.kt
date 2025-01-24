package com.kanishthika.moneymatters.display.dashboard.ui

import com.kanishthika.moneymatters.config.utils.convertDateToYearString
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.accounting.data.AmountViewType
import java.time.LocalDate

data class DashBoardUiState(
    val monthYear: String = convertDateToYearString(LocalDate.now().toString(), "yyyy-MM-dd"),
    val incomeAmount: Double = 0.0,
    val expenseAmount: Double = 0.0,
    val investmentAmount: Double = 0.0,
    val loanEmiAmount: Double = 0.0,
    val insuranceAmount: Double = 0.0,
    val otherAmount: Double = 0.0,
    val lenderAmount: Double = 0.0,
    val borrowerAmount: Double = 0.0,
    val accountList: List<Account> = emptyList(),
    val amountViewType: AmountViewType = AmountViewType.YEAR,

    val backPressedOnce: Boolean = false
)