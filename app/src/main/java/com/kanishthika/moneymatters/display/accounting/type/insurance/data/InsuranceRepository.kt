package com.kanishthika.moneymatters.display.accounting.type.insurance.data

import androidx.lifecycle.asLiveData
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsuranceRepository @Inject constructor(private val insuranceDao: InsuranceDao) :
    FinancialRepository<Insurance> {

    val getAllInsurances = insuranceDao.getAllInsurances().asLiveData()

    override fun getAllItems(): Flow<List<Insurance>> {
        return insuranceDao.getAllInsurances()
    }

    override suspend fun deleteItem(item: Insurance) {
        insuranceDao.deleteInsurance(item)
    }

    override suspend fun updateItem(item: Insurance): Int {
        return insuranceDao.updateInsurance(item)
    }

    override suspend fun insertItem(item: Insurance): Long {
        return insuranceDao.insertInsurance(item)
    }

}