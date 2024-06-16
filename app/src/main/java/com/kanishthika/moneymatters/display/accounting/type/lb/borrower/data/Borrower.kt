package com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "borrowers_list")
data class Borrower  (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val borrowerName: String,
    val borrowerContactNumber: String,
    val amount: Double,
)