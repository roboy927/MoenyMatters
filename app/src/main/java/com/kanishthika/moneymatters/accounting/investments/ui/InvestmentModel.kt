package com.kanishthika.moneymatters.accounting.investments.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.accounting.investments.data.Investment
import com.kanishthika.moneymatters.accounting.investments.data.InvestmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentModel @Inject constructor(
    private val investmentRepository: InvestmentRepository
) : ViewModel() {

    private val _investmentUiState = MutableStateFlow(InvestmentUiState())
    val investmentUiState = _investmentUiState.asStateFlow()


    fun updateName(name: String) {
        _investmentUiState.update {
            it.copy(
                name = name
            )
        }
    }

    fun updateAmount(amount: String) {
        _investmentUiState.update {
            it.copy(
                amount = amount
            )
        }

    }

    fun updateDescription(description: String) {
        _investmentUiState.update {
            it.copy(
                description = description
            )
        }

    }

    fun addInvestment() {
        viewModelScope.launch {
            try {
                val investmentId = investmentRepository.insertInvestment(
                    Investment(
                        id = 0,
                        name = investmentUiState.value.name,
                        description = investmentUiState.value.description,
                        amount = investmentUiState.value.amount.toDouble()
                    )
                )
                if (investmentId > 0L) {
                    updateAmount("")
                    updateDescription("")
                    updateName("")
                }
            } catch (e: Exception) {
                Log.e("ADD_INVESTMENT", "submit: $e ", e)
            }
        }
    }

    val getAllInvestment = investmentRepository.getAllInvestments
}