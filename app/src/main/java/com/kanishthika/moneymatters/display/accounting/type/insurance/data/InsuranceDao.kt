package com.kanishthika.moneymatters.display.accounting.type.insurance.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InsuranceDao {

        @Query("SELECT * FROM insurance_list")
        fun getAllInsurances(): Flow<List<Insurance>>

        @Insert
        suspend fun insertInsurance(insurance: Insurance) : Long

        @Update
        suspend fun updateInsurance(insurance: Insurance) : Int

        @Delete
        suspend fun deleteInsurance (insurance: Insurance)

        @Query("SELECT * FROM insurance_list WHERE providerName = :providerName AND policyNumber = :policyNumber LIMIT 1")
        suspend fun getInsuranceByName(providerName: String, policyNumber: Int): Insurance?

}