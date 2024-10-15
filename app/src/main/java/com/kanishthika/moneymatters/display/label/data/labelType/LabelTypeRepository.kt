package com.kanishthika.moneymatters.display.label.data.labelType

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LabelTypeRepository @Inject constructor(
    private val labelTypeDao: LabelTypeDao
) {

    fun getAllLabelType(): Flow<List<LabelType>> {
        return labelTypeDao.getAllLabelType()
    }

    suspend fun addLabelType(labelType: LabelType): Long{
        return labelTypeDao.insertLabelType(labelType)
    }

    suspend fun deleteLabelType(labelType: LabelType){
        labelTypeDao.deleteLabelType(labelType)
    }
}