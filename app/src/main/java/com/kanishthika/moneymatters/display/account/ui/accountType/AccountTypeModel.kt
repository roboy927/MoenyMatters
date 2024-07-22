package com.kanishthika.moneymatters.display.account.ui.accountType

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.account.data.accountType.AccountType
import com.kanishthika.moneymatters.display.account.data.accountType.AccountTypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountTypeModel @Inject constructor(private val accountTypeRepository: AccountTypeRepository) :
    ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun updateName(string: String){
        _name.value = string
        _buttonEnabled.value = string.isNotEmpty()
    }

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled = _buttonEnabled.asStateFlow()

    fun addAccountType(onAdded: () -> Unit){
        viewModelScope.launch {
            val id =  accountTypeRepository.addAccountType(AccountType(0, _name.value))
            if (id> 0){
                onAdded()
                _name.value = ""
            } else {
                Log.d("TAG", "addAccountType: Not Added ")
            }
        }
    }

}