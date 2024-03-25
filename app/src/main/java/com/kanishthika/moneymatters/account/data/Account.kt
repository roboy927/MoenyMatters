package com.kanishthika.moneymatters.account.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "accounts_List")
data class Account(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val name: String,
    val balance: Double
)
