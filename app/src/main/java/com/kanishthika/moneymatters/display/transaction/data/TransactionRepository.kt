package com.kanishthika.moneymatters.display.transaction.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {

    fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransaction()
    }

    fun getRecentTransaction(): Flow<List<Transaction>> {
        return transactionDao.getRecentTransaction()
    }

    suspend fun addTransaction(transaction: Transaction) : Long{
       return transactionDao.addTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction){
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun searchTransactionsByDescription(description: String): List<Transaction> {
        val searchQuery = "%$description%"
        return transactionDao.searchTransactionsByDescription(searchQuery)
    }

    suspend fun isAccountingNamePresent(accountingName: String): Boolean {
        val result = transactionDao.isAccountingNamePresent(accountingName) > 0
        return result
    }

    suspend fun getMonthlyAmounts(monthYear: String, accountingType:String): Map<String, Double> {
        val monthlyAmounts = transactionDao.getMonthlyAmounts(monthYear, accountingType)
        return monthlyAmounts.associate { it.accountingName to it.totalAmount }

    }

    suspend fun getAmountOfAccountingType(monthYear: String, accountingType: String): Double {
        return transactionDao.getAmountOfAccountingType(monthYear, accountingType) ?: 0.0
    }

    suspend fun getAccountBalance(account: String): Double {
        return transactionDao.getAccountBalance(account) ?: 0.0
    }

    fun getDistinctMonthYearStrings(): Flow<List<String>> {
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
        return getTransactions().map { transactions ->
            transactions.map { transaction ->
                val transactionDate = LocalDate.parse(transaction.date, dateFormatter)
                transactionDate.format(monthYearFormatter)
            }.distinct()
        }
    }

    fun getDistinctYearStrings(): Flow<List<String>> {
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        val yearFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.getDefault())
        return getTransactions().map { transactions ->
            transactions.map { transaction ->
                val transactionDate = LocalDate.parse(transaction.date, dateFormatter)
                transactionDate.format(yearFormatter)
            }.distinct()
        }
    }

    suspend fun getDebitTotalForLabel(label: String) : Double {
        return transactionDao.getDebitTotalForLabel(label) ?: 0.0
    }

    suspend fun getCreditTotalForLabel(label: String) : Double {
        return transactionDao.getCreditTotalForLabel(label) ?: 0.0
    }

    fun getRecentLabelTransaction(label: String) : Flow<List<Transaction>> {
        return transactionDao.getRecentLabelTransaction(label)
    }

    suspend fun getTxnByReminderId(reminderId: Int): Transaction?{
        return transactionDao.getTxnByReminderID(reminderId)
    }

}