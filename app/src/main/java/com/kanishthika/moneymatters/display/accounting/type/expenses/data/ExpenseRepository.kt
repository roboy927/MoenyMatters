package com.kanishthika.moneymatters.display.accounting.type.expenses.data

import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
): FinancialRepository<Expense> {


    override fun getAllItems(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses()
    }

    override suspend fun getItemByName(name: String): Expense {
        return expenseDao.getItemByName(name.uppercase()) ?: Expense(0,"", 0.0, "")
    }

    override suspend fun deleteItem(item: Expense) {
        expenseDao.deleteExpense(item)
    }

    override suspend fun updateItem(item: Expense): Int {
        return expenseDao.updateExpense(item)
    }

    override suspend fun insertItem(item: Expense): Long {
        return expenseDao.insertExpense(item)
    }
}