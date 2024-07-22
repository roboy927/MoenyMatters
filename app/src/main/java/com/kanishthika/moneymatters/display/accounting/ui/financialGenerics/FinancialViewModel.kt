package com.kanishthika.moneymatters.display.accounting.ui.financialGenerics


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.convertDateToMonthYearString
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.AmountViewType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

abstract class BaseFinancialModel<T>(
    private val repository: FinancialRepository<T>,
    private val transactionRepository: TransactionRepository
) : ViewModel() where T : FinancialItem {

    protected val _uiState = MutableStateFlow(FinancialUiState<T>())
    val uiState: StateFlow<FinancialUiState<T>> = _uiState.asStateFlow()

    private val _items = MutableStateFlow<List<T>>(emptyList())
    val items: StateFlow<List<T>> = _items.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchAllItems()
    }

    private fun fetchAllItems() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isAmountLoading = true)
            }
            delay(700)
            repository.getAllItems().collect { items ->
                _items.value = items
                _uiState.update {
                    it.copy(isAmountLoading = false)
                }
                _isLoading.value = false
            }
        }
    }

    private fun fetchMonthlyAmounts(monthYear: String) {
        _uiState.update {
            it.copy(isAmountLoading = true)
        }

        viewModelScope.launch {
            delay(500)
            val monthlyAmounts = transactionRepository.getMonthlyAmounts(
                monthYear = monthYear,
                accountingType = getAccountingType().getName()
            )
            _uiState.update {
                it.copy(
                    monthlyAmounts = monthlyAmounts,
                    isAmountLoading = false
                )
            }
        }
    }

    abstract fun getAccountingType(): AccountingType

    fun changeMonthText(monthText: String) {
        _uiState.update {
            it.copy(monthText = monthText)
        }
    }

    fun changeYearText(yearText: String) {
        _uiState.update {
            it.copy(yearText = yearText)
        }
    }

    fun changeAmountViewTypeState(amountViewType: AmountViewType) {
        _uiState.update {
            it.copy(
                amountViewType = amountViewType,
                monthText = "Current Month",
                yearText = "Current Year"
            )
        }
        when (amountViewType) {
            AmountViewType.TOTAL -> fetchAllItems()
            AmountViewType.MONTH -> fetchMonthlyAmounts(
                convertDateToMonthYearString(LocalDate.now().toString(), "yyyy-MM-dd")
            )
            AmountViewType.YEAR -> fetchMonthlyAmounts(LocalDate.now().year.toString())
        }
    }

    fun changeMonth(monthYear: String) {
        fetchMonthlyAmounts(monthYear)
    }

    fun changeYear(year: String) {
        fetchMonthlyAmounts(year)
    }

    fun sortItems(sortType: String) {
        _items.value = when (sortType) {
            "Name Ascending" -> _items.value.sortedBy {it .name }
            "Name Descending" -> _items.value.sortedByDescending { it.name }
            "Amount Ascending" -> _items.value.sortedBy { it.amount }
            "Amount Descending" -> _items.value.sortedByDescending { it.amount }
            else -> _items.value
        }
    }

    val monthYearList = transactionRepository.getDistinctMonthYearStrings()
    val yearList = transactionRepository.getDistinctYearStrings()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateAmount(amount: String) {
        _uiState.update { it.copy(amount = amount) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun addItemToDB(onAdded: () -> Unit) {
        viewModelScope.launch {
            try {
                val newItem = createNewItem()
                val itemId = repository.insertItem(newItem)
                if (itemId > 0L) {
                    updateAmount("")
                    updateDescription("")
                    updateName("")
                    onAdded()
                }
            } catch (e: Exception) {
                Log.e("ADD_ITEM", "Error adding item: $e", e)
            }
        }
    }

    abstract fun createNewItem(): T

     fun isEditEnabled(accountingName: String, callback: (Boolean) -> Unit){
            viewModelScope.launch {
                val result = !transactionRepository.isAccountingNamePresent(accountingName)
                callback(result)
            }
    }

    fun updateItem(item: T, onUpdated: () -> Unit) {
        viewModelScope.launch {
            val updateReturn = repository.updateItem(item)
            if (updateReturn > 0) {
                updateAmount("")
                updateDescription("")
                updateName("")
                onUpdated()
            } else {
                Log.e("UPDATE_ITEM", "Error updating item")
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            uiState.value.selectedItem?.let { repository.deleteItem(it) }
        }
    }

    fun changeSelectedItem(item: T) {
        _uiState.update { it.copy(selectedItem = item) }
    }

    //Method used in insurance and Loan






    fun isAnyFieldIsEmpty(uiState: FinancialUiState<T>): Boolean {
        return uiState.name.isEmpty() || uiState.amount.isEmpty() || uiState.description.isEmpty()
    }
}
