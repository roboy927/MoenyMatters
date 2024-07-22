package com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "insurance_type_list")
data class InsuranceType (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)