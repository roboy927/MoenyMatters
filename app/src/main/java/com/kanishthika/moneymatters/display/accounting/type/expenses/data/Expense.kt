package com.kanishthika.moneymatters.display.accounting.type.expenses.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem


@Entity (tableName = "expenses_list")
data class Expense  (
    @PrimaryKey(autoGenerate = true)
    override val id: Int =  1,
    override val name: String,
    override val amount: Double,
    override val description: String
) : FinancialItem
