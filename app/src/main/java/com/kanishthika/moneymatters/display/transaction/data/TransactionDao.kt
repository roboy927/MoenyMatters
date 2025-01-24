package com.kanishthika.moneymatters.display.transaction.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transaction_list")
    fun getAllTransaction(): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_list ORDER BY id DESC LIMIT 6")
    fun getRecentTransaction(): Flow<List<Transaction>>

    @Insert
    suspend fun addTransaction(transaction: Transaction) : Long

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_list WHERE description LIKE :searchQuery OR accountingName LIKE :searchQuery")
    suspend fun searchTransactionsByDescription(searchQuery: String): List<Transaction>

    @Query("SELECT EXISTS(SELECT 1 FROM transaction_list WHERE accountingName = :value)")
    suspend fun isAccountingNamePresent(value: String): Int

    @Query("SELECT * FROM transaction_list WHERE reminderId = :id LIMIT 1")
    suspend fun getTxnByReminderID(id: Int): Transaction?
    //this return 1 if string found and return 0 if not

    @Query("""
    SELECT accountingName, SUM(amount) as totalAmount 
    FROM transaction_list 
    WHERE (date LIKE '%' || :monthYear || '%' OR :monthYear IS NULL) 
    AND accountingType = :accountingType 
    GROUP BY accountingName
""")
    suspend fun getMonthlyAmounts(monthYear: String?, accountingType: String): List<AccountingNameWiseAmount>


    @Query("SELECT accountingName, SUM(amount) as totalAmount FROM transaction_list WHERE accountingType = :accountingType GROUP BY accountingName")
    suspend fun getTotalAmounts(accountingType: String): List<AccountingNameWiseAmount>

    @Query("""
    SELECT SUM(amount) 
    FROM transaction_list 
    WHERE (:monthYear IS NULL OR date LIKE '%' || :monthYear || '%') 
      AND accountingType = :accountingType
    """)
    suspend fun getAmountOfAccountingType(monthYear: String?, accountingType: String): Double?

    @Query("SELECT SUM( CASE  WHEN type = 'Credit' THEN amount WHEN type = 'Debit' THEN -amount ELSE 0 END) AS totalAmount FROM transaction_list WHERE account = :account")
    suspend fun getAccountBalance(account: String): Double?

    @Query("SELECT SUM(CASE WHEN type = 'Debit' THEN amount ELSE 0 END) AS totalDebit FROM transaction_list WHERE label = :label")
    suspend fun getDebitTotalForLabel(label: String): Double?

    @Query("SELECT SUM(CASE WHEN type = 'Credit' THEN amount ELSE 0 END) AS totalCredit FROM transaction_list WHERE label = :label")
    suspend fun getCreditTotalForLabel(label: String):  Double?

    @Query("SELECT * FROM transaction_list WHERE label =:label ORDER BY id DESC LIMIT 5")
    fun getRecentLabelTransaction(label: String): Flow<List<Transaction>>
}


data class AccountingNameWiseAmount(
    val accountingName: String,
    val totalAmount: Double
)