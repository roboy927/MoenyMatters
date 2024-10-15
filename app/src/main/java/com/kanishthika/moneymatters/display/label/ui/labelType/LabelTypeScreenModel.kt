package com.kanishthika.moneymatters.display.label.ui.labelType

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.label.data.labelType.LabelType
import com.kanishthika.moneymatters.display.label.data.labelType.LabelTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelTypeScreenModel @Inject constructor(
    private val labelTypeRepository: LabelTypeRepository
): ViewModel() {

    private val _labelTypeList = MutableStateFlow<List<LabelType>>(emptyList())
    val labelTypeList = _labelTypeList.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            labelTypeRepository.getAllLabelType().collectLatest {
                _labelTypeList.value = it
            }
        }
    }

    fun deleteLabelType(labelType: LabelType){
        viewModelScope.launch {
            labelTypeRepository.deleteLabelType(labelType = labelType)
        }
    }
}