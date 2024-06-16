package com.kanishthika.moneymatters.display.transaction.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {

    fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransaction()
    }

    suspend fun addTransaction(transaction: Transaction) : Long{
       return transactionDao.addTransaction(transaction)
    }

}