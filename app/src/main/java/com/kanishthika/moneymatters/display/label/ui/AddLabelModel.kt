package com.kanishthika.moneymatters.display.label.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.label.data.LabelRepository
import com.kanishthika.moneymatters.display.label.data.labelType.LabelType
import com.kanishthika.moneymatters.display.label.data.labelType.LabelTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLabelModel @Inject constructor(
    private val labelRepository: LabelRepository,
    labelTypeRepository: LabelTypeRepository
): ViewModel() {



    private val _uiState = MutableStateFlow(AddLabelUiState())
    private val _labelTypeList = labelTypeRepository.getAllLabelType()

    val uiState = _uiState.combine(_labelTypeList){
        uiState, labelTypeList -> uiState.copy(labelTypeList = labelTypeList + LabelType(0, "Add New"))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AddLabelUiState())

    fun isAnyFieldIsEmpty(labelUiState: AddLabelUiState): Boolean {
        return labelUiState.labelName.isEmpty() || labelUiState.selectedLabelType == LabelType(0,"") ||
                    labelUiState.initialAmount.isEmpty()
    }

    fun addLabel(){
        viewModelScope.launch {
            labelRepository.addLabel(Label(
            id = 0,
            labelName = uiState.value.labelName,
            labelType = uiState.value.selectedLabelType.labelType,
            amount = uiState.value.initialAmount.toDouble(),
                additional = uiState.value.additional
            ))
        }

    }

    fun updateName(labelName: String) {
        _uiState.update {
            it.copy(
                labelName = labelName
            )
        }
    }

    fun updateType(selectedLabelType: LabelType) {
        _uiState.update {
            it.copy(
                selectedLabelType = selectedLabelType
            )
        }
    }

    fun updateAmount(initialAmount: String) {
        _uiState.update {
            it.copy(
                initialAmount = initialAmount
            )
        }
    }

    fun updateAdditional(additional: String){
        _uiState.update {
            it.copy(
                additional = additional
            )
        }
    }

}