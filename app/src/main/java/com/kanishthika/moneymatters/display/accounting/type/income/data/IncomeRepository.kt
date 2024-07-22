package com.kanishthika.moneymatters.display.accounting.type.income.data

import androidx.lifecycle.asLiveData
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IncomeRepository @Inject constructor(private val incomeDao: IncomeDao) :
    FinancialRepository<Income> {

    val getAllIncomes = incomeDao.getAllIncomes().asLiveData()

    override fun getAllItems(): Flow<List<Income>> {
        return incomeDao.getAllIncomes()
    }

    override suspend fun deleteItem(item: Income) {
        incomeDao.deleteIncome(item)
    }

    override suspend fun updateItem(item: Income): Int {
        return incomeDao.updateIncome(item)
    }

    override suspend fun insertItem(item: Income): Long {
        return incomeDao.insertIncome(item)
    }

}