package com.kanishthika.moneymatters.display.accounting.type.lenders.data

import androidx.lifecycle.asLiveData
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LenderRepository @Inject constructor(private val lenderDao: LenderDao) :
    FinancialRepository<Lender> {

    val getAllLenders = lenderDao.getAllLenders().asLiveData()

    override fun getAllItems(): Flow<List<Lender>> {
        return lenderDao.getAllLenders()
    }

    override suspend fun getItemByName(name: String): Lender {
        return lenderDao.getItemByName(name) ?: Lender(1,"","", 0.0)
    }

    override suspend fun deleteItem(item: Lender) {
        lenderDao.deleteLender(item)
    }

    override suspend fun updateItem(item: Lender): Int {
        return lenderDao.updateLender(item)
    }

    override suspend fun insertItem(item: Lender): Long {
        return lenderDao.insertLender(item)
    }

}