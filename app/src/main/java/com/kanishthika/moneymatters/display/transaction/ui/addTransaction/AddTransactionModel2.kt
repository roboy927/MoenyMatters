package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.reminder.ReminderAlarmManager
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.convertStringsToTimeStamp
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.account.data.AccountRepository
import com.kanishthika.moneymatters.display.accounting.data.AccountingRepository
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.label.data.LabelRepository
import com.kanishthika.moneymatters.display.reminder.data.MMReminder
import com.kanishthika.moneymatters.display.reminder.data.MMReminderRepository
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AddTransactionModel2 @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val mmReminderRepository: MMReminderRepository,
    private val accountingRepository: AccountingRepository,
    private val labelRepository: LabelRepository,
    private val reminderAlarmManager: ReminderAlarmManager
) : ViewModel() {

    private val _transactionType = MutableStateFlow(TransactionType.DEBIT)
    private val _accountingType = MutableStateFlow<AccountingType>(AccountingType.EXPENSE)
    private val _accountList = accountRepository.getAllAccount.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    private val _financialItemList = _accountingType.flatMapLatest { accountingType ->
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
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _accountingTypeList = _transactionType.flatMapLatest { transactionType ->
        when (transactionType) {
            TransactionType.CREDIT -> flowOf(
                listOf(
                    AccountingType.INCOME,
                    AccountingType.LENDER,
                    AccountingType.LOAN,
                    AccountingType.RETURNFROMBORROWER,
                    AccountingType.OTHER
                )
            )

            TransactionType.DEBIT -> flowOf(
                listOf(
                    AccountingType.EXPENSE,
                    AccountingType.INVESTMENT,
                    AccountingType.INSURANCE,
                    AccountingType.BORROWER,
                    AccountingType.LOANEMI,
                    AccountingType.RETURNTOLENDER,
                    AccountingType.OTHER
                )
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _labelList = labelRepository.getAllLabel().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )


    private val _addTransactionState = MutableStateFlow(AddTransactionScreenState())
    val addTransactionState = combine(
        combine(
            _transactionType,         // Flow of AccountingType
            _accountList,            // Flow of List<Account>
            _financialItemList,      // Flow of List<FinancialItem>
            _accountingTypeList,     // Flow of List<AccountingType>
            _addTransactionState     // The current addTransactionScreenState
        ) { transactionType, accountList, financialItemList, accountingTypeList, currentState ->
            currentState.copy(
                transactionType = transactionType,
                accountList = accountList,
                financialItemList = financialItemList,
                accountingTypeList = accountingTypeList
            )
        },
        _accountingType,
        _labelList
    ) { currentState, accountingType, labelList ->
        currentState.copy(
            accountingType = accountingType,
            labelList = labelList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AddTransactionScreenState())

    private val _addTransactionReminderState = MutableStateFlow(TransactionReminderUiState())
    val reminderState = _addTransactionReminderState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        TransactionReminderUiState()
    )


    fun onEvent(event: AddTransactionEvent) {
        when (event) {
            is AddTransactionEvent.SetDate -> {
                _addTransactionState.update {
                    it.copy(
                        date = event.date
                    )
                }
            }

            is AddTransactionEvent.SetAccount -> {
                _addTransactionState.update {
                    it.copy(
                        account = event.account
                    )
                }
            }

            is AddTransactionEvent.SetTxnType -> {
                _transactionType.value = event.txnType
                _accountingType.value = _accountingTypeList.value[0]
                _addTransactionState.update {
                    it.copy(
                        financialItem = ""
                    )
                }
            }

            is AddTransactionEvent.SetFinancialType -> {
                _accountingType.value = event.financialType
                _addTransactionState.update {
                    it.copy(
                        financialItem = ""
                    )
                }
            }

            is AddTransactionEvent.SetAmount -> {
                _addTransactionState.update {
                    it.copy(
                        amount = event.amount
                    )
                }
            }

            is AddTransactionEvent.SetDescription -> {
                _addTransactionState.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is AddTransactionEvent.SetFinancialItem -> {
                _addTransactionState.update {
                    it.copy(
                        financialItem = event.financialItem.name
                    )
                }
            }

            is AddTransactionEvent.ChangeOptionState -> {
                _addTransactionState.update {
                    it.copy(
                        options = !_addTransactionState.value.options
                    )
                }
            }

            is AddTransactionEvent.SetReminderDate -> {
                _addTransactionReminderState.update {
                    it.copy(
                        date = event.reminderDate
                    )
                }
            }

            is AddTransactionEvent.SetReminderTime -> {
                _addTransactionReminderState.update {
                    it.copy(
                        time = event.reminderTime
                    )
                }
            }

            is AddTransactionEvent.SetReminderTitle -> {
                _addTransactionReminderState.update {
                    it.copy(
                        title = event.reminderTitle
                    )
                }
            }

            is AddTransactionEvent.SetReminderDescription -> {
                _addTransactionReminderState.update {
                    it.copy(
                        description = event.reminderDescription
                    )
                }
            }

            is AddTransactionEvent.EmptyReminderState -> {
                _addTransactionReminderState.value = TransactionReminderUiState.empty()
            }

            is AddTransactionEvent.UpdateHasReminder -> {
                _addTransactionState.update {
                    it.copy(
                        hasReminder = event.hasReminder
                    )
                }
            }

            is AddTransactionEvent.SetLabel -> {
                _addTransactionState.update {
                    it.copy(
                        selectedLabel = event.label
                    )
                }
            }

            is AddTransactionEvent.AddTransaction -> {
                viewModelScope.launch {
                    val transactionId = transactionRepository.addTransaction(
                        Transaction(
                            id = 0,
                            date = addTransactionState.value.date,
                            account = capitalizeWords(addTransactionState.value.account.name),
                            type = capitalizeWords(addTransactionState.value.transactionType.name),
                            amount = addTransactionState.value.amount.toDouble(),
                            description = capitalizeWords(addTransactionState.value.description),
                            accountingType = capitalizeWords(addTransactionState.value.accountingType.getName()),
                            accountingName = capitalizeWords(addTransactionState.value.financialItem),
                            reminderId = if (addTransactionState.value.hasReminder) mmReminderRepository.getMAXId() + 1 else null,
                            label = addTransactionState.value.selectedLabel.labelName.ifEmpty { null }
                        )
                    )

                    if (addTransactionState.value.hasReminder) {
                        val timeStamp = convertStringsToTimeStamp(
                            reminderState.value.date,
                            reminderState.value.time
                        )
                        mmReminderRepository.insertMMReminder(
                            MMReminder(
                                id = mmReminderRepository.getMAXId() + 1,
                                title = reminderState.value.title,
                                description = reminderState.value.description,
                                timeStamp = timeStamp
                            )
                        )
                        reminderAlarmManager.setAlarm(
                            reminderState.value.title,
                            mmReminderRepository.getMAXId() + 1,
                            timeStamp
                        )
                    }

                    if (transactionId > 0) {
                        _addTransactionState.value = AddTransactionScreenState()
                    }
                }
            }

            is AddTransactionEvent.UpdateTransaction -> {
                viewModelScope.launch {
                    transactionRepository.updateTransaction(event.transaction.copy(
                        date = addTransactionState.value.date,
                        account = capitalizeWords(addTransactionState.value.account.name),
                        type = capitalizeWords(addTransactionState.value.transactionType.name),
                        amount = addTransactionState.value.amount.toDouble(),
                        description = capitalizeWords(addTransactionState.value.description),
                        accountingType = capitalizeWords(addTransactionState.value.accountingType.getName()),
                        accountingName = capitalizeWords(addTransactionState.value.financialItem),
                        reminderId = if (addTransactionState.value.hasReminder) {
                            event.transaction.reminderId ?: (mmReminderRepository.getMAXId() + 1)
                        } else null,

                        label = addTransactionState.value.selectedLabel.labelName.ifEmpty { null }
                    ))
                    if (addTransactionState.value.hasReminder) {
                        val timeStamp = convertStringsToTimeStamp(
                            reminderState.value.date,
                            reminderState.value.time
                        )
                        if (event.transaction.reminderId == null) {
                            mmReminderRepository.insertMMReminder(
                                MMReminder(
                                    id = (mmReminderRepository.getMAXId() + 1),
                                    title = reminderState.value.title,
                                    description = reminderState.value.description,
                                    timeStamp = timeStamp
                                )
                            )
                            reminderAlarmManager.setAlarm(
                                reminderState.value.title,
                                mmReminderRepository.getMAXId() + 1,
                                timeStamp
                            )
                        } else {
                            mmReminderRepository.updateMMReminder(
                                MMReminder(
                                    id = event.transaction.reminderId,
                                    title = reminderState.value.title,
                                    description = reminderState.value.description,
                                    timeStamp = timeStamp
                                )
                            )
                            reminderAlarmManager.cancelAlarm(event.transaction.reminderId)
                            reminderAlarmManager.setAlarm(
                                reminderState.value.title,
                                event.transaction.reminderId,
                                timeStamp)
                        }
                    } else {
                        if (event.transaction.reminderId != null){
                            Log.d("TAG", "onEvent: ${event.transaction.reminderId}")
                            mmReminderRepository.deleteMMReminder(getReminderById(event.transaction.reminderId))
                            reminderAlarmManager.cancelAlarm(event.transaction.reminderId)
                        }
                    }
                }
            }

            is AddTransactionEvent.SetEditTransaction -> {
                _addTransactionState.update {
                    it.copy(
                        editTransaction = event.transaction
                    )
                }
            }

        }
    }

    suspend fun getAccountByName(name: String): Account {
        return accountRepository.getAccountByName(name)
    }

    suspend fun getFinancialItemByName(
        accountingType: AccountingType,
        name: String
    ): FinancialItem {
        return when (accountingType) {
            AccountingType.BORROWER -> accountingRepository.getBorrowerByName(name)
            AccountingType.EXPENSE -> accountingRepository.getExpenseByName(name)
            AccountingType.INCOME -> accountingRepository.getIncomeByName(name)
            AccountingType.INSURANCE -> accountingRepository.getInsuranceByName(name)
            AccountingType.INVESTMENT -> accountingRepository.getInvestmentByName(name)
            AccountingType.LENDER -> accountingRepository.getLenderByName(name)
            AccountingType.LOAN -> accountingRepository.getLenderByName(name)
            AccountingType.LOANEMI -> accountingRepository.getLenderByName(name)
            AccountingType.OTHER -> accountingRepository.getLenderByName(name)
            AccountingType.RETURNFROMBORROWER -> accountingRepository.getBorrowerByName(name)
            AccountingType.RETURNTOLENDER -> accountingRepository.getLenderByName(name)
        }
    }

    suspend fun getLabelByName(labelName: String): Label {
        return labelRepository.getLabelByName(labelName)
    }

    suspend fun getReminderById(id: Int): MMReminder {
        return mmReminderRepository.getReminderById(id)
    }

    fun isStateEmpty(addTransactionScreenState: AddTransactionScreenState): Boolean {
        return addTransactionScreenState.account == Account(1, "", "", 0.0) ||
                addTransactionScreenState.description.isEmpty() ||
                addTransactionScreenState.amount.isEmpty() ||
                addTransactionScreenState.financialItem.isEmpty()
    }
}