package com.kanishthika.moneymatters.display.accounting.type.lenders.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem

@Entity (tableName = "lenders_list")
data class Lender  (
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 1,
    val lenderName: String,
    val lenderContactNumber: String,
    override val amount: Double,
) : FinancialItem {
    override val name: String
        get() = lenderName

    override val description: String
        get() = lenderContactNumber
}