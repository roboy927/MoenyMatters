package com.kanishthika.moneymatters.accounting.investments.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestmentDao {

    @Query("SELECT * FROM investments_list")
    fun getAllInvestments(): Flow<List<Investment>>

    @Insert
    suspend fun insertInvestment(investment: Investment) : Long

    @Update
    suspend fun updateInvestment(investment: Investment)


    @Delete
    suspend fun deleteInvestment (investment: Investment)
}