package com.kanishthika.moneymatters.display.reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mm_reminder")
data class MMReminder (
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val timeStamp: Long
)
