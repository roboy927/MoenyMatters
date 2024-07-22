package com.kanishthika.moneymatters.display.accounting.data

import kotlinx.coroutines.flow.Flow


interface FinancialRepository<T : FinancialItem> {
    fun getAllItems(): Flow<List<T>>
    suspend fun insertItem(item: T): Long
    suspend fun updateItem(item: T): Int
    suspend fun deleteItem(item: T)
    // Add more methods as needed
}

