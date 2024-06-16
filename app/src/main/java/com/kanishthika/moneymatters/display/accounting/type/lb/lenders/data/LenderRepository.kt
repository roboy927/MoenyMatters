package com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import javax.inject.Inject

class LenderRepository @Inject constructor(private val lenderDao: LenderDao){

    val getAllLenders = lenderDao.getAllLenders().asLiveData()
    val getListOfLenderName = lenderDao.getListOfLenderName().asLiveData()

     fun searchedLender(query : String) : LiveData<List<Lender>>{
        return lenderDao.searchLenders(query).asLiveData()
    }

    suspend fun insertLender(lender: Lender) : Long {
        return lenderDao.insertLender(lender)
    }

    suspend fun updateLender(lender: Lender){
        lenderDao.updateLender(lender)
    }

    suspend fun deleteLender(lender: Lender){
        lenderDao.deleteLender(lender)
    }
}