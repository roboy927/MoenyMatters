package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.account.data.AccountRepository
import com.kanishthika.moneymatters.display.accounting.data.AccountingRepository
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.Lender
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val accountingRepository: AccountingRepository
) : ViewModel() {

    // MutableStateFlows to hold selected items
    private val _selectedExpense = MutableStateFlow(Expense(1, "", 0.0, ""))
    val selectedExpense = _selectedExpense.asStateFlow()

    private val _selectedInvestment = MutableStateFlow(Investment(1, "", 0.0, ""))
    val selectedInvestment = _selectedInvestment.asStateFlow()

    private val _selectedBorrower = MutableStateFlow(Borrower(1, "", "", 0.0))
    val selectedBorrower = _selectedBorrower.asStateFlow()

    // Functions to update selected items
    fun updateSelectedExpense(expense: Expense) {
        _selectedExpense.value = expense
    }

    fun updateSelectedInvestment(investment: Investment) {
        _selectedInvestment.value = investment
    }

    fun updateSelectedBorrower(borrower: Borrower) {
        _selectedBorrower.value = borrower
    }

    // Getting all records from the repository
    val getAllExpenses = accountingRepository.getAllExpenses
    val getAllInvestments = accountingRepository.getAllInvestments
    val getAllBorrowers = accountingRepository.getAllBorrowers
    val getAllLenders = accountingRepository.getAllLenders
    val getAllAccounts = accountRepository.getAllAccount.asLiveData()


    // Update methods for different entities
    fun updateExpense(expense: Expense) {
        viewModelScope.launch { accountingRepository.updateExpense(expense) }
    }

    fun updateInvestment(investment: Investment) {
        viewModelScope.launch { accountingRepository.updateInvestment(investment) }
    }

    fun updateBorrower(borrower: Borrower) {
        viewModelScope.launch { accountingRepository.updateBorrower(borrower) }
    }

    fun updateLender(lender: Lender) {
        viewModelScope.launch { accountingRepository.updateLender(lender) }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch { accountRepository.updateAccount(account) }
    }

    // State to control dialog visibility
    private val _isDialogOpen = MutableStateFlow(false)
    val isDialogOpen = _isDialogOpen.asStateFlow()

    fun isDialogOpenChanged(value: Boolean) {
        _isDialogOpen.value = value
    }

    // Transaction UI state and its management
    private val _transactionUiState = MutableStateFlow(TransactionUiState())
    val transactionUiState = _transactionUiState.asStateFlow()

    private fun resetTransactionUiState() {
        _transactionUiState.value = TransactionUiState.empty()
    }

    // Function to add a new transaction
    @SuppressLint("SuspiciousIndentation")
    fun addTransaction() {
        viewModelScope.launch {
            val transactionId = transactionRepository.addTransaction(
                Transaction(
                    id = 0,
                    date = transactionUiState.value.date,
                    account = capitalizeWords(transactionUiState.value.account.name),
                    amount = transactionUiState.value.amount.toDouble(),
                    description = capitalizeWords(transactionUiState.value.description),
                    accountingType = capitalizeWords(transactionUiState.value.accountingType.getName()),
                    type = capitalizeWords(transactionUiState.value.transactionType.name),
                    accountingName = capitalizeWords(transactionUiState.value.accountingName)
                )
            )
            if (transactionId > 0L) {
                resetTransactionUiState()
            }
        }
    }

    // Functions to update UI state properties
    fun changeAccountingType(newAccountingType: AccountingType) {
        _transactionUiState.update { it.copy(accountingType = newAccountingType) }
    }

    fun changeAccountingName(newAccountingName: String) {
        _transactionUiState.update { it.copy(accountingName = capitalizeWords(newAccountingName)) }
    }

    fun changeTxnType(newTransactionType: TransactionType) {
        _transactionUiState.update { it.copy(transactionType = newTransactionType) }
    }

    fun updateAmount(newAmount: String) {
        _transactionUiState.update { it.copy(amount = newAmount) }
    }

    fun updateDescription(newDescription: String) {
        _transactionUiState.update { it.copy(description = capitalizeWords(newDescription)) }
    }

    fun updateSelectedAccount(newSelectedAccount: Account) {
        _transactionUiState.update { it.copy(account = newSelectedAccount) }
    }

    fun updateDate(newDate: String) {
        _transactionUiState.update { it.copy(date = newDate) }
    }

    // Function to get a list of accounting types based on transaction type
    fun accountingTypeList(transactionType: TransactionType): List<AccountingType> {
        return when (transactionType) {
            TransactionType.CREDIT -> listOf(AccountingType.INCOME, AccountingType.LENDERS)
            TransactionType.DEBIT -> listOf(AccountingType.INVESTMENT, AccountingType.EXPENSE, AccountingType.BORROWERS)
        }
    }

    // Function to check if any field is empty in the UI state
    fun isAnyFieldIsEmpty(uiState: TransactionUiState): Boolean {
        return uiState.date.isEmpty() || uiState.amount.isEmpty() || uiState.description.isEmpty()
                || uiState.account == Account(1,"", 0.0)
    }
}