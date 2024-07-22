package com.kanishthika.moneymatters.display.account.data.accountType

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_type_list")
data class AccountType (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)