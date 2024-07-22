package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.formatTo2Decimal
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.account.data.AccountRepository
import com.kanishthika.moneymatters.display.accounting.data.AccountingRepository
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.DivideOptions
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.SplitOptions
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.getSplitPageCount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    accountRepository: AccountRepository,
    private val accountingRepository: AccountingRepository
) : ViewModel() {

    private val _transactionUiState = MutableStateFlow(TransactionUiState())
    val transactionUiState = _transactionUiState.asStateFlow()

    private val _transactionBodyUiState =
        MutableStateFlow<List<TransactionBodyUiState>>(emptyList())
    val transactionBodyUiState = _transactionBodyUiState.asStateFlow()

    private val _accountingTypeList = MutableStateFlow<List<AccountingType>>(emptyList())
    val accountingTypeList = _accountingTypeList.asStateFlow()

    init {
        initializeTransactionBodyUiState(getSplitPageCount(transactionUiState.value.splitOptions))
        changeAccountingTypeList(TransactionType.DEBIT)
    }

    private fun initializeTransactionBodyUiState(count: Int) {
        _transactionBodyUiState.value = List(count) { TransactionBodyUiState() }
    }

    val getAllAccounts = accountRepository.getAllAccount.asLiveData()

    /* // MutableStateFlows to hold selected items
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
     */


    private fun resetTransactionUiState() {
        _transactionUiState.value = TransactionUiState.empty()
    }

    // Function to add a new transaction
    fun addTransaction() {
        viewModelScope.launch {
            if (transactionUiState.value.splitOptions == SplitOptions.None) {
                transactionBodyUiState.value.forEach { transactionBodyUiState ->
                    val transactionId = transactionRepository.addTransaction(
                        Transaction(
                            id = 0,
                            date = transactionUiState.value.date,
                            account = capitalizeWords(transactionUiState.value.account.name),
                            type = capitalizeWords(transactionUiState.value.transactionType.name),
                            amount = transactionUiState.value.amount.toDouble(),
                            description = capitalizeWords(transactionUiState.value.description),
                            accountingType = capitalizeWords(transactionBodyUiState.accountingType.getName()),
                            accountingName = capitalizeWords(transactionBodyUiState.accountingName)
                        )
                    )
                    if (transactionId > 0L) {
                        resetTransactionUiState()
                    }
                }
            } else {
                transactionBodyUiState.value.forEach { transactionBodyUiState ->
                    val transactionId = transactionRepository.addTransaction(
                        Transaction(
                            id = 0,
                            date = transactionUiState.value.date,
                            account = capitalizeWords(transactionUiState.value.account.name),
                            type = capitalizeWords(transactionUiState.value.transactionType.name),
                            amount = transactionBodyUiState.splitAmount.toDouble(),
                            description = capitalizeWords(transactionBodyUiState.description),
                            accountingType = capitalizeWords(transactionBodyUiState.accountingType.getName()),
                            accountingName = capitalizeWords(transactionBodyUiState.accountingName)
                        )
                    )
                    if (transactionId > 0L) {
                        resetTransactionUiState()
                    }
                }
            }
        }
    }

    fun getAccountingNameList(accountingType: AccountingType): Flow<List<FinancialItem>> =
            when (accountingType) {
                AccountingType.BORROWER -> accountingRepository.getAllBorrowers
                AccountingType.EXPENSE -> accountingRepository.getAllExpenses
                AccountingType.INCOME -> accountingRepository.getAllIncomes
                AccountingType.INSURANCE -> accountingRepository.getAllInsurances
                AccountingType.INVESTMENT -> accountingRepository.getAllInvestments
                AccountingType.LENDER -> accountingRepository.getAllLenders
                AccountingType.LOAN -> accountingRepository.getAllLenders
                AccountingType.LOANEMI -> accountingRepository.getAllLenders
                AccountingType.OTHER -> accountingRepository.getAllIncomes
                AccountingType.RETURNFROMBORROWER -> accountingRepository.getAllBorrowers
                AccountingType.RETURNTOLENDER -> accountingRepository.getAllLenders

        }

    // Functions to update UI state properties

    fun updateDate(newDate: String) {
        _transactionUiState.update { it.copy(date = newDate) }
    }

    fun updateSelectedAccount(newSelectedAccount: Account) {
        _transactionUiState.update { it.copy(account = newSelectedAccount) }
    }


    fun changeTxnType(newTransactionType: TransactionType) {
        _transactionUiState.update {
            it.copy(transactionType = newTransactionType)
        }
        changeAccountingTypeList(newTransactionType)
        _transactionBodyUiState.update { currentList ->
            currentList.map { it.copy(
                accountingType = accountingTypeList.value[0],
                accountingName = ""
            ) }
        }
    }

    fun updateDescription(newDescription: String) {
        _transactionUiState.update { it.copy(description = capitalizeWords(newDescription)) }
    }

    fun updateSplitDescription(newDescription: String, currentPage: Int) {
        _transactionBodyUiState.update { list ->
            list.toMutableList().apply {
                val updatedState = this[currentPage].copy(description = capitalizeWords(newDescription) )
                this[currentPage] = updatedState
            }
        }
    }

    fun updateAmount(newAmount: String) {
        _transactionUiState.update {
            it.copy(
                amount = newAmount, divideOptions = DivideOptions.CUSTOM
            )
        }
        _transactionBodyUiState.update { currentList ->
            currentList.map { it.copy(splitAmount = "") }
        }
        checkSplitAmountSum()
    }

    suspend fun changeSplitOption(splitOptions: SplitOptions) {
        delay(100)
        _transactionUiState.update { it.copy(splitOptions = splitOptions) }
        initializeTransactionBodyUiState(getSplitPageCount(transactionUiState.value.splitOptions))
        /*if(splitOptions != SplitOptions.None){
            _transactionBodyUiState.update { list->
                list.map { it.copy(description = transactionUiState.value.description) }
            }
        }*/
    }

    fun changeDivideOption(divideOptions: DivideOptions) {
        _transactionUiState.update { it.copy(divideOptions = divideOptions) }
        if (transactionUiState.value.divideOptions == DivideOptions.EQUAL && transactionUiState.value.amount.isNotEmpty()) {
            val divideBy = getSplitPageCount(transactionUiState.value.splitOptions)
            val newAmount = transactionUiState.value.amount.toDouble() / divideBy
            _transactionBodyUiState.update { currentList ->
                currentList.map { it.copy(splitAmount = formatTo2Decimal(newAmount.toString())) }
            }
        } else {
            _transactionBodyUiState.update { currentList ->
                currentList.map { it.copy(splitAmount = "") }
            }
        }
    }

    fun updateSplitAmount(newSplitAmount: String, currentPage: Int) {
        _transactionBodyUiState.update { list ->
            list.toMutableList().apply {
                val updatedState =
                    this[currentPage].copy(splitAmount = formatTo2Decimal(newSplitAmount))
                this[currentPage] = updatedState
            }
        }
        checkSplitAmountSum()

    }

    fun changeAccountingType(newAccountingType: AccountingType, currentPage: Int) {
        _transactionBodyUiState.update { list ->
            list.toMutableList().apply {
                val updatedState = this[currentPage].copy(accountingType = newAccountingType)
                this[currentPage] = updatedState
            }
        }
    }

    fun changeAccountingName(newAccountingName: String, currentPage: Int) {
        _transactionBodyUiState.update { list ->
            list.toMutableList().apply {
                val updatedState = this[currentPage].copy(accountingName = newAccountingName)
                this[currentPage] = updatedState
            }
        }
    }

    private fun changeAccountingTypeList(transactionType: TransactionType) {
        when (transactionType) {
            TransactionType.CREDIT -> _accountingTypeList.value = listOf(
                AccountingType.INCOME,
                AccountingType.LENDER,
                AccountingType.LOAN,
                AccountingType.RETURNFROMBORROWER,
                AccountingType.OTHER
            )

            TransactionType.DEBIT -> _accountingTypeList.value = listOf(
                AccountingType.EXPENSE,
                AccountingType.INVESTMENT,
                AccountingType.INSURANCE,
                AccountingType.BORROWER,
                AccountingType.LOANEMI,
                AccountingType.RETURNTOLENDER,
                AccountingType.OTHER
            )
        }
    }

    private fun checkSplitAmountSum() {
        if (transactionUiState.value.splitOptions == SplitOptions.None) {
            _transactionUiState.update {
                it.copy(
                    checkSum = true
                )
            }
        } else {
            if (transactionBodyUiState.value.all { it.splitAmount.isNotEmpty() }) {
                _transactionUiState.update { it ->
                    it.copy(
                        checkSum = transactionUiState.value.amount.toDouble() == (transactionBodyUiState.value.sumOf { it.splitAmount.toDouble() })
                    )
                }
            }
        }
    }

    // Function to check if any field is empty in the UI state
    fun isAnyFieldIsEmpty(
        transactionUiState: TransactionUiState, transactionBodyUiState: List<TransactionBodyUiState>
    ): Boolean {
        return if (transactionUiState.splitOptions == SplitOptions.None) {
            transactionUiState.date.isEmpty() ||
                    transactionUiState.amount.isEmpty() ||
                    transactionUiState.description.isEmpty() ||
                    transactionUiState.account == Account(1, "", "", 0.0) ||
                    transactionBodyUiState.any { it.accountingName.isEmpty() }
        } else {
            transactionUiState.date.isEmpty() ||
                    transactionUiState.amount.isEmpty() ||
                    transactionBodyUiState.any { it.splitAmount.isEmpty() } ||
                    transactionUiState.account == Account(1, "", "", 0.0) ||
                    transactionBodyUiState.any { it.accountingName.isEmpty() } ||
                    transactionBodyUiState.any { it.description.isEmpty() }
        }
    }
}