package com.kanishthika.moneymatters.display.accounting.type.investments.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem

@Entity (tableName = "investments_list")
data class Investment(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 1,
    override val name: String,
    override val amount: Double,
    override val description: String
) : FinancialItem