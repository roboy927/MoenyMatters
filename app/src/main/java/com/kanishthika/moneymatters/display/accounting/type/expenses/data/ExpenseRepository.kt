package com.kanishthika.moneymatters.display.accounting.type.expenses.data

import androidx.lifecycle.asLiveData
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao){

    val getAllExpenses = expenseDao.getAllExpenses().asLiveData()
    val getListOfExpenseName = expenseDao.getListOfExpenseName().asLiveData()

    suspend fun insertExpense(expense: Expense) : Long {
        return expenseDao.insertExpense(expense)
    }

    suspend fun updateExpense (expense: Expense) {
        return expenseDao.updateExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense){
        expenseDao.deleteExpense(expense)
    }
}