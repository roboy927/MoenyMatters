package com.kanishthika.moneymatters.display.label.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {

    // Query all Labels from the database
    @Query("SELECT * FROM Label_List")
    fun getAllLabels(): Flow<List<Label>>

    // Insert a new Label into the database
    @Insert
    suspend fun insertLabel(label: Label) : Long

    // Update an existing Label in the database
    @Update
    suspend fun updateLabel(label: Label)

    // Delete an Label from the database
    @Delete
    suspend fun deleteLabel (label: Label)

    @Query("SELECT * FROM label_list WHERE labelName = :itemName LIMIT 1")
    suspend fun getItemByName(itemName: String): Label?
}