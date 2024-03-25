package com.kanishthika.moneymatters.transaction.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanishthika.moneymatters.transaction.TransactionType

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val account: String,
    val type: TransactionType,
    val amount: Double,
    val description: String,
    val accountingType: String
)