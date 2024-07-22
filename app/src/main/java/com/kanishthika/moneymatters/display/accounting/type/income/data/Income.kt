package com.kanishthika.moneymatters.display.accounting.type.income.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem

@Entity(tableName = "income_list")
data class Income(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 1,
    override val name: String,
    override val amount: Double,
    override val description: String
) : FinancialItem