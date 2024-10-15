package com.kanishthika.moneymatters.display.label.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LabelRepository @Inject constructor(private val labelDao: LabelDao){
    fun getAllLabel(): Flow<List<Label>> {
        return labelDao.getAllLabels()
    }

    suspend fun addLabel(label: Label): Long{
        return labelDao.insertLabel(label)
    }

    suspend fun deleteLabel(label: Label){
        labelDao.deleteLabel(label)
    }

    suspend fun getLabelByName(labelName: String): Label{
        return labelDao.getItemByName(labelName) ?: Label(1,"","", 0.0,"")
    }

}