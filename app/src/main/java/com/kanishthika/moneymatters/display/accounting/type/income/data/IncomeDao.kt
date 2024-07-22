package com.kanishthika.moneymatters.display.accounting.type.income.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {

    @Query("SELECT * FROM income_list")
    fun getAllIncomes(): Flow<List<Income>>

    @Query("SELECT name FROM income_list")
    fun getListOfIncomeName() : Flow<List<String>>

    @Insert
    suspend fun insertIncome(income: Income) : Long

    @Update
    suspend fun updateIncome(income: Income) : Int

    @Delete
    suspend fun deleteIncome (income: Income)

}