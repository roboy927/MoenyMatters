package com.kanishthika.moneymatters.transaction.data.model

import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {

    suspend fun addTransaction(transaction: Transaction){
        transactionDao.addTransaction(transaction)
    }

}