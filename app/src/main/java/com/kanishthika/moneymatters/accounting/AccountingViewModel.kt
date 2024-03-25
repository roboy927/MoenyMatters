package com.kanishthika.moneymatters.accounting

import androidx.lifecycle.ViewModel
import com.kanishthika.moneymatters.accounting.expense.data.ExpenseRepository
import com.kanishthika.moneymatters.accounting.investments.data.InvestmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AccountingViewModel @Inject constructor(
investmentRepository: InvestmentRepository,
    expenseRepository: ExpenseRepository
) : ViewModel() {

val getAllInvestment = investmentRepository.getAllInvestments
    val getAllExpense = expenseRepository.getAllExpenses
}