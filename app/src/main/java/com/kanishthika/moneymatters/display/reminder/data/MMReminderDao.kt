package com.kanishthika.moneymatters.display.reminder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import kotlinx.coroutines.flow.Flow


@Dao
interface MMReminderDao {

    @Query("SELECT * FROM mm_reminder")
    fun getAllMMReminders(): Flow<List<MMReminder>>

    // Insert a new mmReminder into the database
    @Insert
    suspend fun insertMMReminder(mmReminder: MMReminder)

    // Update an existing mmReminder in the database
    @Update
    suspend fun updateMMReminder(mmReminder: MMReminder)

    // Delete an mmReminder from the database
    @Delete
    suspend fun deleteMMReminder (mmReminder: MMReminder)

    @Query("SELECT MAX(id) FROM mm_reminder")
    suspend fun getMaxId(): Int?

    @Query("SELECT * FROM mm_reminder WHERE id = :id LIMIT 1")
    suspend fun getReminderByID (id: Int): MMReminder?
}