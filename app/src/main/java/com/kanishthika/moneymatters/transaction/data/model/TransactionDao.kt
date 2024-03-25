package com.kanishthika.moneymatters.transaction.data.model

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TransactionDao {

    @Insert
    suspend fun addTransaction(transaction: Transaction)
}