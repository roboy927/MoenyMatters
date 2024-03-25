package com.kanishthika.moneymatters.accounting.expense.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses_list")
    fun getAllExpenses(): Flow<List<Expense>>

    @Insert
    suspend fun insertExpense(expense: Expense) : Long

    @Update
    suspend fun updateExpense(expense: Expense)


    @Delete
    suspend fun deleteExpense (expense: Expense)
}