package com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import javax.inject.Inject

class BorrowerRepository @Inject constructor(private val borrowerDao: BorrowerDao){

    val getAllBorrowers = borrowerDao.getAllBorrowers().asLiveData()
    val getListOfBorrowerName = borrowerDao.getListOfBorrowerName().asLiveData()

     fun searchedBorrower(query : String) : LiveData<List<Borrower>>{
        return borrowerDao.searchBorrowers(query).asLiveData()
    }

    suspend fun insertBorrower(borrower: Borrower) : Long {
        return borrowerDao.insertBorrower(borrower)
    }

    suspend fun updateBorrower(borrower: Borrower){
        borrowerDao.updateBorrower(borrower)
    }

    suspend fun deleteBorrower(borrower: Borrower){
        borrowerDao.deleteBorrower(borrower)
    }
}