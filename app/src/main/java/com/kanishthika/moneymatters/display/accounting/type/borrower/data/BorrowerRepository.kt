package com.kanishthika.moneymatters.display.accounting.type.borrower.data

import androidx.lifecycle.asLiveData
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BorrowerRepository @Inject constructor(private val borrowerDao: BorrowerDao) :
    FinancialRepository<Borrower> {

    val getAllBorrowers = borrowerDao.getAllBorrowers().asLiveData()

    override fun getAllItems(): Flow<List<Borrower>> {
        return borrowerDao.getAllBorrowers()
    }

    override suspend fun deleteItem(item: Borrower) {
        borrowerDao.deleteBorrower(item)
    }

    override suspend fun updateItem(item: Borrower): Int {
        return borrowerDao.updateBorrower(item)
    }

    override suspend fun insertItem(item: Borrower): Long {
        return borrowerDao.insertBorrower(item)
    }

}