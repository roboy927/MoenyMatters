package com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.BorrowerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BorrowerModel @Inject constructor(
    private val borrowerRepository: BorrowerRepository,
) : ViewModel() {


    private val _addBorrowerUiState = MutableStateFlow(BorrowerUiState())
    val addBorrowerUiState = _addBorrowerUiState.asStateFlow()

    fun searchedBorrower(query: String): LiveData<List<Borrower>> {
        return borrowerRepository.searchedBorrower(query)
    }

    fun updateBorrowerName(name: String) {
        _addBorrowerUiState.update {
            it.copy(
                borrowerName = name
            )
        }
    }


    fun updateBorrowerContactNumber(borrowerContactNumber: String) {
        _addBorrowerUiState.update {
            it.copy(
                borrowerContactNumber = borrowerContactNumber
            )
        }
    }

    fun updateAmount(amount: String) {
        _addBorrowerUiState.update {
            it.copy(
                amount = amount
            )
        }
    }

    fun addBorrower() {
        viewModelScope.launch {
            try {
                val borrowerId = borrowerRepository.insertBorrower(
                    Borrower(
                        id = 0,
                        borrowerName = addBorrowerUiState.value.borrowerName,
                        amount = addBorrowerUiState.value.amount.toDouble(),
                        borrowerContactNumber = addBorrowerUiState.value.borrowerContactNumber,
                    )
                )
                if (borrowerId > 0L) {
                    updateAmount("")
                    updateBorrowerName("")
                    updateBorrowerContactNumber("")
                }
            } catch (e: Exception) {
                Log.e("ADD_EXPENSE", "submit: $e ", e)
            }
        }

    }

    fun isAnyFieldIsEmpty(uiState: BorrowerUiState): Boolean {
        return uiState.borrowerName.isEmpty() || uiState.amount.isEmpty() || uiState.borrowerContactNumber.isEmpty()
    }

}