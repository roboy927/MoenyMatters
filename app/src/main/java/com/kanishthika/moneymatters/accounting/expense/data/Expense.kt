package com.kanishthika.moneymatters.accounting.expense.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "expenses_list")
data class Expense  (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name: String,
    val amount: Double,
    val description: String
)