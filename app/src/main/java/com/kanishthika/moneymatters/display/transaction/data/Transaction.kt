package com.kanishthika.moneymatters.display.transaction.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "transaction_list")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val account: String,
    val type: String,
    val amount: Double,
    val description: String,
    val accountingType: String,
    val accountingName: String
)