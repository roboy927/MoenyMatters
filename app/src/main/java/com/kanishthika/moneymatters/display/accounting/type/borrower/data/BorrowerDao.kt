package com.kanishthika.moneymatters.display.accounting.type.borrower.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BorrowerDao {

    @Query("SELECT * FROM borrowers_list")
    fun getAllBorrowers(): Flow<List<Borrower>>

    @Query("SELECT borrowerName FROM borrowers_list")
    fun getListOfBorrowerName() : Flow<List<String>>

    @Insert
    suspend fun insertBorrower(borrower: Borrower) : Long

    @Update
    suspend fun updateBorrower(borrower: Borrower) : Int


    @Delete
    suspend fun deleteBorrower (borrower: Borrower)

    @Query("SELECT * FROM borrowers_list WHERE borrowerName LIKE '%' || :query || '%'")
    fun searchBorrowers(query: String): Flow<List<Borrower>>

    @Query("SELECT * FROM borrowers_list WHERE borrowerName = :itemName LIMIT 1")
    suspend fun getItemByName(itemName: String): Borrower?
}