package com.kanishthika.moneymatters.accounting.expense.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.accounting.expense.data.Expense
import com.kanishthika.moneymatters.accounting.expense.data.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {


    private val _addExpenseUiState = MutableStateFlow(ExpenseUiState())
    val addExpenseUiState = _addExpenseUiState.asStateFlow()

    fun updateName(name: String) {
        _addExpenseUiState.update {
            it.copy(
                name = name
            )
        }
    }

    fun updateAmount(amount: String) {
        _addExpenseUiState.update {
            it.copy(
                amount = amount
            )
        }
    }


    fun updateDescription(description: String) {
        _addExpenseUiState.update {
            it.copy(
               description = description
            )
        }
    }

    fun addExpense()  {
        viewModelScope.launch {
            try {
                val expenseId =  expenseRepository.insertExpense(
                    Expense(
                        id=0,
                        name= addExpenseUiState.value.name,
                        amount = addExpenseUiState.value.amount.toDouble(),
                        description = addExpenseUiState.value.description
                    )
                )
                if(expenseId > 0L){
                    updateAmount("")
                    updateDescription("")
                    updateName("")
                }
            } catch (e : Exception){
                Log.e("ADD_EXPENSE", "submit: $e ", e )
            }
        }
    }
    val getAllExpense  =
        expenseRepository.getAllExpenses
}