package com.kanishthika.moneymatters.transaction.ui

import androidx.lifecycle.ViewModel
import com.kanishthika.moneymatters.account.data.AccountRepository
import com.kanishthika.moneymatters.accounting.AccountingType
import com.kanishthika.moneymatters.transaction.TransactionType
import com.kanishthika.moneymatters.transaction.data.model.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TransactionModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    accountRepository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState = _uiState.asStateFlow()

    val getAllAccounts = accountRepository.getAllAccount

    fun changeAccounting(newAccountingType: String) {
        _uiState.update {
            it.copy(
                accountingType = newAccountingType
            )
        }
    }

    fun changeTxnType(newTransactionType: TransactionType) {
        _uiState.update {
            it.copy(
                transactionType = newTransactionType
            )
        }
    }

    fun updateAmount(newAmount: String) {
        _uiState.update {
            it.copy(
                amount = newAmount
            )
        }
    }

    fun updateDescription(newDescription: String) {
        _uiState.update {
            it.copy(
                description = newDescription
            )
        }
    }

    fun updateSelectedAccount(newSelectedAccount: String) {
        _uiState.update {
            it.copy(
                account = newSelectedAccount
            )
        }
    }

    fun updateDate(newDate: String) {
        _uiState.update {
            it.copy(
                date = newDate
            )
        }
    }

    fun accountingTypeList(transactionType: TransactionType): List<String> {
        return when (transactionType) {
            TransactionType.CREDIT -> listOf(
                AccountingType.INCOME,
                AccountingType.LENDERS
            ).map { it::class.simpleName.orEmpty() }
            TransactionType.DEBIT -> listOf(
                AccountingType.INVESTMENT,
                AccountingType.EXPENSE,
                AccountingType.BORROWERS

            ).map { it::class.simpleName.orEmpty() }
        }
    }

}