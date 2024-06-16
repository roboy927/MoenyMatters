package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction

import android.icu.text.SimpleDateFormat
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.convertToLocalDate
import com.kanishthika.moneymatters.display.account.data.AccountRepository
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.getAllAccountingTypes
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet.FirstLevelFilter
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet.TransactionFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
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

    init {
        fetchAllTransactions()
    }

    fun fetchAllTransactions() {
        viewModelScope.launch {
            transactionRepository.getTransactions()
                .collect { allTransactions ->
                    _transactions.value = allTransactions
                }
        }
    }

    private var filteredTransaction = emptyList<Transaction>()
    fun filterTransactions() {
        updateFilterToUiState()
        viewModelScope.launch {
            val filter = TransactionFilter(
                accounts = _displayTransactionUiState.value.filterByAccount,
                month = _displayTransactionUiState.value.filterByMonth,
                types = _displayTransactionUiState.value.filterByTransactionType,
                accountingTypes = _displayTransactionUiState.value.filterByAccountingType,
            )
            transactionRepository.getTransactions()
                .map { transactions ->
                    transactions.filter { transaction ->
                        val monthYear = convertDateToMonthYear(transaction.date)
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
                    _transactions.value = filteredList
                    filteredTransaction = filteredList
                }
        }
    }


    fun sortByDateAscending() {
        viewModelScope.launch {
            transactionRepository.getTransactions()
                .map { transactions ->
                    transactions.sortedBy { convertToLocalDate(it.date, "dd MMMM yyyy") }
                }
                .collect { sortedList ->
                    _transactions.value = sortedList
                }
        }
    }

    fun sortByDateDescending() {
        viewModelScope.launch {
            transactionRepository.getTransactions()
                .map { transactions ->
                    transactions.sortedByDescending { convertToLocalDate(it.date, "dd MMMM yyyy") }
                }
                .collect { sortedList ->
                    _transactions.value = sortedList
                }
        }
    }

    fun sortByAmountAscending() {
        viewModelScope.launch {
            transactionRepository.getTransactions()
                .map { transactions ->
                    transactions.sortedBy { it.amount }
                }
                .collect { sortedList ->
                    _transactions.value = sortedList
                }
        }
    }

    fun sortByAmountDescending() {
        viewModelScope.launch {
            transactionRepository.getTransactions()
                .map { transactions ->
                    transactions.sortedByDescending { it.amount }
                }
                .collect { sortedList ->
                    _transactions.value = sortedList
                }
        }
    }

    @Composable
    fun getIcon(accountingType: AccountingType): ImageVector {
        return when (accountingType) {
            AccountingType.BORROWERS -> ImageVector.vectorResource(id = R.drawable.b_accounting)
            AccountingType.EXPENSE -> ImageVector.vectorResource(id = R.drawable.e_accounting)
            AccountingType.INCOME -> ImageVector.vectorResource(id = R.drawable.i_accounting)
            AccountingType.INVESTMENT -> ImageVector.vectorResource(id = R.drawable.i_accounting)
            AccountingType.LENDERS -> ImageVector.vectorResource(id = R.drawable.l_accounting)
        }
    }

    @Composable
    fun getIconBackground(transactionType: TransactionType): Color {
        return when (transactionType) {
            TransactionType.CREDIT -> MaterialTheme.colorScheme.primary.copy(0.8f)
            TransactionType.DEBIT -> MaterialTheme.colorScheme.secondary.copy(0.8f)
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
    private var monthList = getDistinctMonthYearStrings()

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

    var monthFilter: List<String> = emptyList()
    var accountFilter: List<String> = emptyList()
    var transactionTypeFilter: List<String> = emptyList()
    var accountingTypeFilter: List<String> = emptyList()

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

    private fun updateFilterToUiState() {
        _displayTransactionUiState.update {
            it.copy(
                filterByAccount = accountFilter,
                filterByMonth = monthFilter,
                filterByTransactionType = transactionTypeFilter,
                filterByAccountingType = accountingTypeFilter
            )
        }

    }

    private fun getDistinctMonthYearStrings(): Flow<List<String>> {
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())

        return transactionRepository.getTransactions().map { transactions ->
            transactions.map { transaction ->
                val transactionDate = LocalDate.parse(transaction.date, dateFormatter)
                transactionDate.format(monthYearFormatter)
            }.distinct()
        }
    }

    @Composable
    fun getSortingIcon(sortState: Int): ImageVector {
        return when (sortState) {
            1 -> ImageVector.vectorResource(id = R.drawable.ascending)
            2 -> ImageVector.vectorResource(id = R.drawable.descending)
            else -> ImageVector.vectorResource(id = R.drawable.sort)
        }
    }
}

fun convertDateToMonthYear(inputDate: String): String {
    // Define the input and output date formats
    val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    // Parse the input date
    val date = inputFormat.parse(inputDate)

    // Format the date to the desired output format
    return outputFormat.format(date)
}






