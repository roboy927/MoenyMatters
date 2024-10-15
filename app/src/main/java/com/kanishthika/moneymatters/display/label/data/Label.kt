package com.kanishthika.moneymatters.display.label.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "label_list")
data class Label (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val labelName: String,
    val labelType: String,
    val amount: Double,
    val additional: String
)
