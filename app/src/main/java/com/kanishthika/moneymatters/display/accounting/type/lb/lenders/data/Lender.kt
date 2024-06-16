package com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "lenders_list")
data class Lender  (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val lenderName: String,
    val lenderContactNumber: String,
    val amount: Double,
)