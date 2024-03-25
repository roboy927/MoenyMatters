package com.kanishthika.moneymatters.accounting.expense.data

import androidx.lifecycle.asLiveData
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao){

    val getAllExpenses = expenseDao.getAllExpenses().asLiveData()

    suspend fun insertExpense(expense: Expense) : Long {
        return expenseDao.insertExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense){
        expenseDao.deleteExpense(expense)
    }
}