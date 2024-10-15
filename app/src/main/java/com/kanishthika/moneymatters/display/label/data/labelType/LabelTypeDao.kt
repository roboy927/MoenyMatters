package com.kanishthika.moneymatters.display.label.data.labelType

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelTypeDao {
    @Query("SELECT * FROM label_type_list")
    fun getAllLabelType(): Flow<List<LabelType>>

    @Insert
    suspend fun insertLabelType(labelType: LabelType) : Long

    @Delete
    suspend fun deleteLabelType (labelType: LabelType)
}