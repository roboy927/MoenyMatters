package com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsuranceTypeRepository @Inject constructor(
    private val insuranceTypeDao: InsuranceTypeDao
) {

    fun getAllInsuranceType(): Flow<List<InsuranceType>> {
        return insuranceTypeDao.getAllInsuranceType()
    }

    suspend fun addInsuranceType(insuranceType: InsuranceType): Long{
        return insuranceTypeDao.insertInsuranceType(insuranceType)
    }

    suspend fun deleteInsuranceType(insuranceType: InsuranceType){
         insuranceTypeDao.deleteInsuranceType(insuranceType)
    }
}