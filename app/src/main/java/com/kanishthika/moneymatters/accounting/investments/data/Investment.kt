package com.kanishthika.moneymatters.accounting.investments.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "investments_list")
data class Investment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name: String,
    val amount: Double,
    val description: String
)