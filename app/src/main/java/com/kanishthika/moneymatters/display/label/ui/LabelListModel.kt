package com.kanishthika.moneymatters.display.label.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.label.data.LabelRepository
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelListModel @Inject constructor(
    private val labelRepository: LabelRepository,
    private val transactionRepository: TransactionRepository
): ViewModel() {

    private val _labelList = MutableStateFlow<List<Label>>(emptyList())
    val labelList = _labelList.asStateFlow()

    private val _loadingScreen = MutableStateFlow(true)
    val loadingScreen = _loadingScreen.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            labelRepository.getAllLabel().collectLatest {
                _labelList.value = it
                _loadingScreen.value = false
            }
        }
    }

    suspend fun getDebitTotalForLabel(label: String): Double{
        return transactionRepository.getDebitTotalForLabel(label)
    }

    suspend fun getCreditTotalForLabel(label: String): Double{
        return transactionRepository.getCreditTotalForLabel(label)
    }

    fun getRecentLabelTransaction(label: String): Flow<List<Transaction>>{
        return transactionRepository.getRecentLabelTransaction(label)
    }
}