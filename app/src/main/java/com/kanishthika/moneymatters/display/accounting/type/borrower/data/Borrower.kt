package com.kanishthika.moneymatters.display.accounting.type.borrower.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem

@Entity (tableName = "borrowers_list")
data class Borrower  (
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 1,
    val borrowerName: String,
    val borrowerContactNumber: String,
    override val amount: Double,
) : FinancialItem {
    override val name: String
        get() = borrowerName

    override val description: String
        get() = borrowerContactNumber
}