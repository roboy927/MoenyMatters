package com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LenderDao {

    @Query("SELECT * FROM lenders_list")
    fun getAllLenders(): Flow<List<Lender>>

    @Query("SELECT lenderName FROM lenders_list")
    fun getListOfLenderName() : Flow<List<String>>

    @Insert
    suspend fun insertLender(lender: Lender) : Long

    @Update
    suspend fun updateLender(lender: Lender)


    @Delete
    suspend fun deleteLender (lender: Lender)

    @Query("SELECT * FROM lenders_list WHERE lenderName LIKE '%' || :query || '%'")
    fun searchLenders(query: String): Flow<List<Lender>>
}