package com.kanishthika.moneymatters.display.accounting.type.insurance.data

import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsuranceRepository @Inject constructor(private val insuranceDao: InsuranceDao) :
    FinancialRepository<Insurance> {


    override fun getAllItems(): Flow<List<Insurance>> {
        return insuranceDao.getAllInsurances()
    }

    override suspend fun getItemByName(name: String): Insurance {
        val parts = name.split(" ")
        if (parts.size < 2) return Insurance(1,"",0.0,"",1,0.0,"","",0.0,"")
        val providerName = parts[0]
        val policyNumber = parts[1].toIntOrNull() ?: return Insurance(1,"",0.0,"",1,0.0,"","",0.0,"")
        return insuranceDao.getInsuranceByName(providerName, policyNumber) ?: Insurance(1,"",0.0,"",1,0.0,"","",0.0,"")
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