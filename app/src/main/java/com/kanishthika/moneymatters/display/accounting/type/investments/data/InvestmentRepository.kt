package com.kanishthika.moneymatters.display.accounting.type.investments.data

import androidx.lifecycle.asLiveData
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvestmentRepository @Inject constructor(private val investmentDao: InvestmentDao) :
    FinancialRepository<Investment> {

    val getAllInvestments = investmentDao.getAllInvestments().asLiveData()

    override fun getAllItems(): Flow<List<Investment>> {
        return investmentDao.getAllInvestments()
    }

    override suspend fun deleteItem(item: Investment) {
       investmentDao.deleteInvestment(item)
    }

    override suspend fun updateItem(item: Investment): Int {
       return investmentDao.updateInvestment(item)
    }

    override suspend fun insertItem(item: Investment): Long {
        return investmentDao.insertInvestment(item)
    }

}