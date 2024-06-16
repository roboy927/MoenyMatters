package com.kanishthika.moneymatters.display.accounting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class AccountingViewModel @Inject constructor(

) : ViewModel() {





    private val _selectedAccountingType = MutableStateFlow<AccountingType>(AccountingType.EXPENSE)
    val selectedAccountingType = _selectedAccountingType.asStateFlow()

    fun updateSelectedAccountingType(accountingType: AccountingType){
        _selectedAccountingType.value = accountingType
    }


    private val _searchedText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchedText

    fun onSearchTextChanged(newSearchText: String) {
        _searchedText.value = newSearchText
    }

    fun onClearClick() {
        _searchedText.value = ""
    }
}

