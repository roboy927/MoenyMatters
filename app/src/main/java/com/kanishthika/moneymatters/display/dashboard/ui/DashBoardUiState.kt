package com.kanishthika.moneymatters.display.dashboard.ui

import com.kanishthika.moneymatters.config.utils.convertDateToMonthYearString
import java.time.LocalDate

data class DashBoardUiState(
    val monthYear: String = convertDateToMonthYearString(LocalDate.now().toString(), "yyyy-MM-dd"),
    val incomeAmount: Double = 0.0,
    val expenseAmount: Double = 0.0,
    val investmentAmount: Double = 0.0,
    val loanEmiAmount: Double = 0.0,
    val insuranceAmount: Double = 0.0,
    val otherAmount: Double = 0.0
)