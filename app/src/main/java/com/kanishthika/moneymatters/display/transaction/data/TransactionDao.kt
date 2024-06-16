package com.kanishthika.moneymatters.display.transaction.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transaction_list")
    fun getAllTransaction(): Flow<List<Transaction>>

    @Insert
    suspend fun addTransaction(transaction: Transaction) : Long
}