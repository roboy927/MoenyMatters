package com.kanishthika.moneymatters.display.transaction.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transaction_list")
    fun getAllTransaction(): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_list ORDER BY id DESC LIMIT 6")
    fun getRecentTransaction(): Flow<List<Transaction>>

    @Insert
    suspend fun addTransaction(transaction: Transaction) : Long

    @Query("SELECT * FROM transaction_list WHERE description LIKE :searchQuery OR accountingName LIKE :searchQuery")
    suspend fun searchTransactionsByDescription(searchQuery: String): List<Transaction>

    @Query("SELECT EXISTS(SELECT 1 FROM transaction_list WHERE accountingName = :value)")
    suspend fun isAccountingNamePresent(value: String): Int
    //this return 1 if string found and return 0 if not

    @Query("SELECT accountingName, SUM(amount) as totalAmount FROM transaction_list WHERE date LIKE '%' || :monthYear || '%' AND accountingType = :accountingType GROUP BY accountingName")
    suspend fun getMonthlyAmounts(monthYear: String, accountingType: String): List<MonthlyAmount>

    @Query("SELECT SUM(amount) FROM transaction_list WHERE date LIKE '%' || :monthYear || '%' AND accountingType = :accountingType")
    suspend fun getAmountOfAccountingType(monthYear: String, accountingType: String): Double?

}

data class MonthlyAmount(
    val accountingName: String,
    val totalAmount: Double
)