package com.kanishthika.moneymatters.display.label.data.labelType

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "label_type_list")
data class LabelType(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val labelType: String,
)
