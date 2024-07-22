package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.convertDateToMonthYearString
import com.kanishthika.moneymatters.config.utils.convertToLocalDate
import com.kanishthika.moneymatters.display.account.data.AccountRepository
import com.kanishthika.moneymatters.display.accounting.data.getAllAccountingTypes
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet.FirstLevelFilter
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet.TransactionFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DisplayTransactionModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    accountRepository: AccountRepository
) : ViewModel() {

    private val _displayTransactionUiState = MutableStateFlow(DisplayTransactionUiState())
    val displayTransactionUiState = _displayTransactionUiState.asStateFlow()

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions = _transactions.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    var monthFilter: List<String> = emptyList()
    var accountFilter: List<String> = emptyList()
    var transactionTypeFilter: List<String> = emptyList()
    var accountingTypeFilter: List<String> = emptyList()

    init {
        initializeFilters()
        loadTransactions()
    }

    private fun initializeFilters() {
        viewModelScope.launch {
            val currentMonthYear =
                convertDateToMonthYearString(LocalDate.now().toString(), "yyyy-MM-dd")
            val monthList = transactionRepository.getDistinctMonthYearStrings().first()
            val indexOfCurrentMonth = monthList.indexOf(currentMonthYear)

            _displayTransactionUiState.update {
                val updatedCheckBoxStates = it.checkBoxStates.toMutableMap()
                if (indexOfCurrentMonth != -1) {
                    updatedCheckBoxStates[FirstLevelFilter.Month] = setOf(indexOfCurrentMonth)
                }
                val updatedCheckboxCountState = it.checkboxCountState.toMutableMap()
                updatedCheckboxCountState[FirstLevelFilter.Month] =
                    if (indexOfCurrentMonth != -1) 1 else 0

                it.copy(
                    checkBoxStates = updatedCheckBoxStates,
                    checkboxCountState = updatedCheckboxCountState,
                    filterByMonth = listOf(currentMonthYear)
                )
            }
            monthFilter = listOf(currentMonthYear)
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            delay(1500)
            transactionRepository.getTransactions().map { transactions ->
                transactions.filter {
                    convertDateToMonthYearString(it.date, "dd MMMM yyyy") ==
                            convertDateToMonthYearString(LocalDate.now().toString(), "yyyy-MM-dd")
                }
            }.collect { filteredList ->
                _transactions.value = filteredList.reversed()
                _isLoading.value = false
            }
        }
    }

    fun fetchAllTransactions() {
        makeFilterStateEmpty()
        viewModelScope.launch {
            _isLoading.value = true
            delay(1500)
            transactionRepository.getTransactions()
                .collect { transactions ->
                    _transactions.value = transactions.reversed()
                    _isLoading.value = false
                }
        }
    }

    fun isAllTransactionStateChanged() {
        _displayTransactionUiState.update {
            it.copy(
                isAllTransactionSelected = !_displayTransactionUiState.value.isAllTransactionSelected
            )
        }
    }

    fun filterTransactions() {
        updateFilterToUiState()
        val filter = TransactionFilter(
            accounts = _displayTransactionUiState.value.filterByAccount,
            month = _displayTransactionUiState.value.filterByMonth,
            types = _displayTransactionUiState.value.filterByTransactionType,
            accountingTypes = _displayTransactionUiState.value.filterByAccountingType,
        )
        viewModelScope.launch {
            _isLoading.value = true
            delay(1500)
            transactionRepository.getTransactions()
                .map { transactions ->
                    transactions.filter { transaction ->
                        val monthYear =
                            convertDateToMonthYearString(transaction.date, "dd MMMM yyyy")
                        (filter.month.isEmpty() || filter.month.contains(monthYear)) &&
                                (filter.accounts.isEmpty() || filter.accounts.contains(transaction.account)) &&
                                (filter.types.isEmpty() || filter.types.contains(transaction.type)) &&
                                (filter.accountingTypes.isEmpty() || filter.accountingTypes.contains(
                                    transaction.accountingType
                                )) &&
                                (filter.accountingNames.isEmpty() || filter.accountingNames.contains(
                                    transaction.accountingName
                                ))
                    }
                }
                .collect { filteredList ->
                    _transactions.value = filteredList.reversed()
                    _isLoading.value = false
                }
        }
        Log.d("rushit", "filterTransactions: ${_displayTransactionUiState.value.filterByMonth}")
        if (filter.accounts.isEmpty() &&
            filter.month.isEmpty() &&
            filter.types.isEmpty() &&
            filter.accountingTypes.isEmpty()
        ) {
            _displayTransactionUiState.update {
                it.copy(
                    isAllTransactionSelected = true
                )
            }
        } else {
            _displayTransactionUiState.update {
                it.copy(
                    isAllTransactionSelected = false
                )
            }
        }
    }

    fun sortByDateAscending() {
        _transactions.value = _transactions.value.sortedBy {
            convertToLocalDate(it.date, "dd MMMM yyyy")
        }
    }

    fun sortByDateDescending() {
        _transactions.value = _transactions.value.sortedByDescending {
            convertToLocalDate(it.date, "dd MMMM yyyy")
        }
    }

    fun sortByAmountAscending() {
        _transactions.value = _transactions.value.sortedBy {
            it.amount
        }
    }

    fun sortByAmountDescending() {
        _transactions.value = _transactions.value.sortedByDescending {
            it.amount
        }
    }

    val firstLevelFilter = FirstLevelFilter.values().toList()
    fun changeSelectedFirstLevelFilter(firstLevelFilter: FirstLevelFilter) {
        _displayTransactionUiState.update {
            it.copy(
                selectedFirstLevelFilter = firstLevelFilter
            )
        }
    }

    private val accountList = accountRepository.getAllAccount
    private val accountNamesList = accountList.map { accounts ->
        accounts.map { account -> capitalizeWords(account.name) }
    }
    private var monthList = transactionRepository.getDistinctMonthYearStrings()

    private val transactionTypeList = flow {
        emit(TransactionType.values().map { capitalizeWords(it.name) })
    }
    private val accountingTypeList = flow {
        val accountingTypes = getAllAccountingTypes()
        val names = accountingTypes.map { capitalizeWords(it.getName()) }
        emit(names)
    }

    fun getSecondLevelFilterList(selectedFirstLevelFilter: FirstLevelFilter) =
        when (selectedFirstLevelFilter) {
            FirstLevelFilter.Account -> accountNamesList
            FirstLevelFilter.Month -> monthList
            FirstLevelFilter.TransactionType -> transactionTypeList
            FirstLevelFilter.AccountingType -> accountingTypeList
        }

    fun getCheckedState(selectedFirstLevelFilter: FirstLevelFilter): Set<Int> {
        return _displayTransactionUiState.value.checkBoxStates[selectedFirstLevelFilter]
            ?: emptySet()
    }

    fun changeCheckBoxState(
        checked: Boolean,
        index: Int,
        selectedFirstLevelFilter: FirstLevelFilter
    ) {
        _displayTransactionUiState.update {
            val updatedSet = it.checkBoxStates[selectedFirstLevelFilter]?.let { set ->
                if (checked) set + index else set - index
            } ?: emptySet()
            val newCheckBoxState = it.checkBoxStates + (selectedFirstLevelFilter to updatedSet)
            it.copy(
                checkBoxStates = newCheckBoxState,
                checkboxCountState = it.checkboxCountState + (selectedFirstLevelFilter to updatedSet.size)
            )
        }
    }

    fun clearAllCheckBoxStates() {
        _displayTransactionUiState.update {
            it.copy(
                checkBoxStates = it.checkBoxStates.mapValues { (_, _) -> emptySet() },
                checkboxCountState = it.checkboxCountState.mapValues { (_, _) -> 0 }
            )
        }
        makeLocalFilterEmpty()
    }

    fun updateFilterList(
        firstLevelFilter: FirstLevelFilter,
        string: String,
        checked: Boolean
    ) {
        when (firstLevelFilter) {
            FirstLevelFilter.Account -> changeToAccountFilter(string, checked)
            FirstLevelFilter.Month -> changeToMonthFilter(string, checked)
            FirstLevelFilter.TransactionType -> changeToTransactionTypeFilter(string, checked)
            FirstLevelFilter.AccountingType -> changeToAccountingTypeFilter(string, checked)
        }
    }


    private fun makeLocalFilterEmpty() {
        accountFilter = emptyList()
        monthFilter = emptyList()
        transactionTypeFilter = emptyList()
        accountingTypeFilter = emptyList()
    }

    private fun changeToAccountFilter(string: String, checked: Boolean) {
        accountFilter = if (checked) {
            accountFilter + string
        } else {
            accountFilter - string
        }
    }

    private fun changeToMonthFilter(string: String, checked: Boolean) {
        monthFilter = if (checked) {
            monthFilter + string
        } else {
            monthFilter - string
        }
    }

    private fun changeToTransactionTypeFilter(string: String, checked: Boolean) {
        transactionTypeFilter = if (checked) {
            transactionTypeFilter + string
        } else {
            transactionTypeFilter - string
        }
    }

    private fun changeToAccountingTypeFilter(string: String, checked: Boolean) {
        accountingTypeFilter = if (checked) {
            accountingTypeFilter + string
        } else {
            accountingTypeFilter - string
        }
    }

    fun updateFilterToUiState() {
        _displayTransactionUiState.update {
            it.copy(
                filterByAccount = accountFilter,
                filterByMonth = monthFilter,
                filterByTransactionType = transactionTypeFilter,
                filterByAccountingType = accountingTypeFilter
            )
        }
    }

    private fun makeFilterStateEmpty() {
        makeLocalFilterEmpty()
        _displayTransactionUiState.update {
            it.copy(
                filterByAccount = emptyList(),
                filterByMonth = emptyList(),
                filterByTransactionType = emptyList(),
                filterByAccountingType = emptyList(),
                checkBoxStates = it.checkBoxStates.mapValues { (_, _) -> emptySet() },
                checkboxCountState = it.checkboxCountState.mapValues { (_, _) -> 0 }
            )
        }
    }
}






