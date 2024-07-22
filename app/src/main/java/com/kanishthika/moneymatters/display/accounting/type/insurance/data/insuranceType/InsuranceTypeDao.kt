package com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InsuranceTypeDao {

    @Query("SELECT * FROM insurance_type_list")
    fun getAllInsuranceType(): Flow<List<InsuranceType>>

    @Insert
    suspend fun insertInsuranceType(insuranceType: InsuranceType) : Long

    @Delete
    suspend fun deleteInsuranceType (insuranceType: InsuranceType)

}