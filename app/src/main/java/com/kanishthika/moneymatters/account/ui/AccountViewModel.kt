package com.kanishthika.moneymatters.account.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.account.data.Account
import com.kanishthika.moneymatters.account.data.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor (private val repository: AccountRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState = _uiState.asStateFlow()

    fun updateName(name: String){
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun updateAmount(amount: String){
        _uiState.update {
            it.copy(amount = amount)
        }
    }

    val getAllAccounts: LiveData<List<Account>> = repository.getAllAccount

    fun insertAccount (){
        viewModelScope.launch {
            repository.insertAccount(
                Account(0, uiState.value.name, uiState.value.amount.toDouble())
            )
            updateAmount("")
            updateName("")
        }
    }

    fun deleteAccount (account: Account){
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
}