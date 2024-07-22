package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTransactionModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _searchedTransaction = MutableStateFlow<List<Transaction>>(emptyList())
    val searchedTransaction = _searchedTransaction.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun searchTransactions(description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000)
            _searchedTransaction.value = transactionRepository.searchTransactionsByDescription(description)
            _isLoading.value = false
        }
    }

}
