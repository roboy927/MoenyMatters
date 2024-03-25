package com.kanishthika.moneymatters.accounting.investments.data

import androidx.lifecycle.asLiveData
import javax.inject.Inject

class InvestmentRepository @Inject constructor(private val investmentDao: InvestmentDao){

    val getAllInvestments  = investmentDao.getAllInvestments().asLiveData()

    suspend fun insertInvestment(investment: Investment) : Long {
        return investmentDao.insertInvestment(investment)
    }

    suspend fun deleteInvestment(investment: Investment){
        investmentDao.deleteInvestment(investment)
    }
}