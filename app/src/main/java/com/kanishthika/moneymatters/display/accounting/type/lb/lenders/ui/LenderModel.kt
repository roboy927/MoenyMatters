package com.kanishthika.moneymatters.display.accounting.type.lb.lenders.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.Lender
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.LenderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LenderModel @Inject constructor(
    private val lenderRepository: LenderRepository,
) : ViewModel() {


    private val _addLenderUiState = MutableStateFlow(LenderUiState())
    val addLenderUiState = _addLenderUiState.asStateFlow()

    fun searchedLender(query: String): LiveData<List<Lender>> {
        return lenderRepository.searchedLender(query)
    }

    fun updateLenderName(name: String) {
        _addLenderUiState.update {
            it.copy(
                lenderName = name
            )
        }
    }


    fun updateLenderContactNumber(lenderContactNumber: String) {
        _addLenderUiState.update {
            it.copy(
                lenderContactNumber = lenderContactNumber
            )
        }
    }

    fun updateAmount(amount: String) {
        _addLenderUiState.update {
            it.copy(
                amount = amount
            )
        }
    }

    fun addLender() {
        viewModelScope.launch {
            try {
                val lenderId = lenderRepository.insertLender(
                    Lender(
                        id = 0,
                        lenderName = addLenderUiState.value.lenderName,
                        amount = addLenderUiState.value.amount.toDouble(),
                        lenderContactNumber = addLenderUiState.value.lenderContactNumber,
                    )
                )
                if (lenderId > 0L) {
                    updateAmount("")
                    updateLenderName("")
                    updateLenderContactNumber("")
                }
            } catch (e: Exception) {
                Log.e("ADD_EXPENSE", "submit: $e ", e)
            }
        }

    }

    fun isAnyFieldIsEmpty(uiState: LenderUiState): Boolean {
        return uiState.lenderName.isEmpty() || uiState.amount.isEmpty() || uiState.lenderContactNumber.isEmpty()
    }

}