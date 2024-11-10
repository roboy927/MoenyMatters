package com.kanishthika.moneymatters.display.transaction.ui.tts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.account.data.AccountRepository
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TtsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    private val _accountList = accountRepository.getAllAccount.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    private val _ttsState = MutableStateFlow(TtsUiState())
    val ttsState = combine(
        _accountList,
        _ttsState
    ) { accountList, currentState ->
        currentState.copy(
            accountList = accountList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TtsUiState())

    fun onEvent(event: TtsEvent) {
        when (event) {
            is TtsEvent.SetDate -> {
                _ttsState.update {
                    it.copy(
                        date = event.date
                    )
                }
            }

            is TtsEvent.SetDbAccount -> {
                _ttsState.update {
                    it.copy(
                        dbAccountName = event.dbAccount
                    )
                }
            }

            is TtsEvent.SetCrAccount -> {
                _ttsState.update {
                    it.copy(
                        crAccountName = event.crAccount
                    )
                }
            }

            is TtsEvent.SetAmount -> {
                _ttsState.update {
                    it.copy(
                        amount = event.amount
                    )
                }
            }

            is TtsEvent.SetDescription -> {
                _ttsState.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is TtsEvent.AddTransaction -> {
                viewModelScope.launch {
                    transactionRepository.addTransaction(
                        Transaction(
                            0,
                            account = capitalizeWords(ttsState.value.dbAccountName.name),
                            type = capitalizeWords(TransactionType.DEBIT.name),
                            description = capitalizeWords(ttsState.value.description),
                            amount = ttsState.value.amount.toDouble(),
                            date = ttsState.value.date,
                            accountingType = "Tts",
                            accountingName = "Tts"
                        )
                    )
                    transactionRepository.addTransaction(
                        Transaction(
                            0,
                            account = capitalizeWords(ttsState.value.crAccountName.name),
                            type = capitalizeWords(TransactionType.CREDIT.name),
                            description = capitalizeWords(ttsState.value.description),
                            amount = ttsState.value.amount.toDouble(),
                            date = ttsState.value.date,
                            accountingType = "Tts",
                            accountingName = "Tts"
                        )
                    )
                }
            }
        }
    }
    fun isStateEmpty(ttsUiState: TtsUiState): Boolean {
        return ttsUiState.crAccountName == Account(0, "", "", 0.0) ||
                ttsUiState.dbAccountName == Account(0, "", "", 0.0) ||
                ttsUiState.description.isEmpty() ||
                ttsUiState.amount.isEmpty()
    }
}
