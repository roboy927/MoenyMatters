package com.kanishthika.moneymatters.display.accounting.type.expenses.ui

import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.BaseFinancialModel
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpenseModel @Inject constructor(
    repository: FinancialRepository<Expense>,
    transactionRepository: TransactionRepository
) : BaseFinancialModel<Expense>(repository, transactionRepository) {

    override fun getAccountingType(): AccountingType = AccountingType.EXPENSE

    override fun createNewItem(): Expense {
        return Expense(
            id = 0,
            name = capitalizeWords(uiState.value.name),
            amount = uiState.value.amount.toDouble(),
            description = capitalizeWords(uiState.value.description)
        )
    }

    /* fun changeYearText(yearText: String) {
         _uiState.update {
             it.copy(
                 yearText = yearText
             )
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
         if (amountViewType == AmountViewType.TOTAL) {
             fetchAllItems()
         }
         if (amountViewType == AmountViewType.MONTH) {
             fetchMonthlyAmounts(
                 monthYear = convertDateToMonthYearString(
                     LocalDate.now().toString(), "yyyy-MM-dd"
                 )
             )
         }
         if (amountViewType == AmountViewType.YEAR) {
             fetchMonthlyAmounts(monthYear = LocalDate.now().year.toString())
         }
     }


     fun changeMonth(monthYear: String) {
         fetchMonthlyAmounts(monthYear)
     }

     private val _uiState = MutableStateFlow(FinancialUiState<Expense>())
     val uiState = _uiState.asStateFlow()

     private val _items = MutableStateFlow<List<Expense>>(emptyList())
     val items = _items.asStateFlow()

     private val _isLoading = MutableStateFlow(true)
     val isLoading = _isLoading.asStateFlow()

     init {
         fetchAllItems()
     }

     private fun fetchAllItems() {
         viewModelScope.launch {
             _uiState.update {
                 it.copy(isAmountLoading = true)
             }
             delay(700)
             repository.getAllItems()
                 .collect { items ->
                     _items.value = items
                     _uiState.update {
                         it.copy(
                             isAmountLoading = false,
                         )
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
                 accountingType = AccountingType.EXPENSE.getName() // Adjust as per your need
             )
             _uiState.update {
                 it.copy(
                     monthlyAmounts = monthlyAmounts,
                     isAmountLoading = false
                 )
             }
         }
     }


     fun changeMonthText(monthText: String) {
         _uiState.update {
             it.copy(
                 monthText = monthText
             )
         }
     }

     fun changeYear(year: String) {
         fetchMonthlyAmounts(year)
     }


     fun sortItems(sortType: String) {
         when (sortType) {
             "Name Ascending" -> _items.value = _items.value.sortedBy { it.name }
             "Name Descending" -> _items.value = _items.value.sortedByDescending { it.name }
             "Amount Ascending" -> _items.value = _items.value.sortedBy { it.amount }
             "Amount Descending" -> _items.value = _items.value.sortedByDescending { it.amount }
         }
     }
     val monthYearList = transactionRepository.getDistinctMonthYearStrings()
     val yearList = transactionRepository.getDistinctYearStrings()

     fun updateName(name: String) {
         _uiState.update {
             it.copy(
                 name = name
             )
         }
     }

     fun updateAmount(amount: String) {
         _uiState.update {
             it.copy(
                 amount = amount
             )
         }
     }


     fun updateDescription(description: String) {
         _uiState.update {
             it.copy(
                 description = description
             )
         }
     }

     fun addItemToDB(onAdded: () -> Unit) {
         viewModelScope.launch {
             try {
                 val expenseId = repository.insertItem(
                     Expense(
                         id = 0,
                         name = capitalizeWords(uiState.value.name),
                         amount = uiState.value.amount.toDouble(),
                         description = capitalizeWords(uiState.value.description)
                     )
                 )
                 if (expenseId > 0L) {
                     updateAmount("")
                     updateDescription("")
                     updateName("")
                     onAdded()
                 }
             } catch (e: Exception) {
                 Log.e("ADD_EXPENSE", "submit: $e ", e)
             }
         }
     }

     fun updateExpense(expense: Expense, onUpdated: () -> Unit) {
         viewModelScope.launch {
             val updateReturn = repository.updateItem(expense)
             if (updateReturn > 0) {
                 updateAmount("")
                 updateDescription("")
                 updateName("")
                 onUpdated()
             } else {
                 Log.e("UPDATE_EXPENSE", "updateExpense: Not Updated")
             }
         }
     }

     fun deleteItem() {
         viewModelScope.launch {
             uiState.value.selectedItem?.let { repository.deleteItem(it) }
         }
     }

     fun changeSelectedItem(expense: Expense) {
         _uiState.update {
             it.copy(
                 selectedItem = expense
             )
         }
     }

     // Implement methods for updating name, amount, description, adding, updating, deleting items as per your requirements

     fun isAnyFieldIsEmpty(uiState: FinancialUiState<Expense>): Boolean {
         return uiState.name.isEmpty() || uiState.amount.isEmpty() || uiState.description.isEmpty()
     }
 */
}
