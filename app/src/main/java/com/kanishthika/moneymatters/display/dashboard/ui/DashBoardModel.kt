package com.kanishthika.moneymatters.display.dashboard.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.display.account.data.AccountRepository
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.AmountViewType
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _dashBoardUiState = MutableStateFlow(DashBoardUiState())
    val dashBoardUiState = _dashBoardUiState.asStateFlow()

    private val _lastSevenTransaction = MutableStateFlow<List<Transaction>>(emptyList())
    val lastSevenTransaction = _lastSevenTransaction.asStateFlow()

    val monthYearList = transactionRepository.getDistinctMonthYearStrings()
    val yearList = transactionRepository.getDistinctYearStrings()

    private suspend fun getAmounts(monthYear: String) {
            val incomeAmount = transactionRepository.getAmountOfAccountingType(monthYear, AccountingType.INCOME.getName())
            val expenseAmount = transactionRepository.getAmountOfAccountingType(monthYear, AccountingType.EXPENSE.getName())
            val investmentAmount = transactionRepository.getAmountOfAccountingType(monthYear, AccountingType.INVESTMENT.getName())
            val loanEmiAmount = transactionRepository.getAmountOfAccountingType(monthYear, AccountingType.LOANEMI.getName())
            val insuranceAmount = transactionRepository.getAmountOfAccountingType(monthYear, AccountingType.INSURANCE.getName())
            val otherAmount = transactionRepository.getAmountOfAccountingType(monthYear, AccountingType.OTHER.getName())

            _dashBoardUiState.update {
                it.copy(
                    incomeAmount = incomeAmount,
                    expenseAmount = expenseAmount,
                    investmentAmount = investmentAmount,
                    loanEmiAmount = loanEmiAmount,
                    insuranceAmount = insuranceAmount,
                    otherAmount = otherAmount
                )
            }
            Log.d("TAG", "month: ${dashBoardUiState.value.monthYear} ")
            Log.d("TAG", "income: ${dashBoardUiState.value.incomeAmount} ")
            Log.d("TAG", "expense: ${dashBoardUiState.value.expenseAmount} ")
            Log.d("TAG", "investment: ${dashBoardUiState.value.investmentAmount} ")
            Log.d("TAG", "loanEmi: ${dashBoardUiState.value.loanEmiAmount} ")
            Log.d("TAG", "insurance: ${dashBoardUiState.value.insuranceAmount} ")
            Log.d("TAG", "other: ${dashBoardUiState.value.otherAmount} ")
    }

    fun loadData() {
        Log.d("TAG", "loadData: loading")
        viewModelScope.launch {
            accountRepository.getAllAccount.collectLatest { accounts ->
                _dashBoardUiState.update {
                    it.copy(
                        accountList = accounts
                    )
                }
            }
        }
        viewModelScope.launch {
            transactionRepository.getRecentTransaction().collectLatest {
                _lastSevenTransaction.value = it
            }
        }
        viewModelScope.launch {
            getAmounts(dashBoardUiState.value.monthYear)
        }
    }

    fun changeBackPressedOnce(boolean: Boolean){
        _dashBoardUiState.update {
            it.copy(
                backPressedOnce = boolean
            )
        }
    }

    suspend fun getAccountBalance(accountName: String): Double{
        val initialBalance = accountRepository.getAccountByName(accountName).balance
        val txnBalance = transactionRepository.getAccountBalance(accountName)
        return initialBalance + txnBalance
    }

    fun changeAmountViewType(amountViewType: AmountViewType){
        _dashBoardUiState.update{
            it.copy(
                amountViewType = amountViewType
            )
        }
    }
}