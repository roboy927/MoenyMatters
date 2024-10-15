package com.kanishthika.moneymatters.display.accounting.type.expenses.data

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

    @Query("SELECT name FROM expenses_list")
    fun getListOfExpenseName() : Flow<List<String>>

    @Insert
    suspend fun insertExpense(expense: Expense) : Long

    @Update
    suspend fun updateExpense(expense: Expense) : Int

    @Delete
    suspend fun deleteExpense (expense: Expense)

    @Query("SELECT * FROM expenses_list WHERE name = :itemName LIMIT 1")
    suspend fun getItemByName(itemName: String): Expense?

}