package com.kanishthika.moneymatters.display.reminder.ui.reminderList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.localDateTimeToTimeStamp
import com.kanishthika.moneymatters.display.reminder.data.MMReminder
import com.kanishthika.moneymatters.display.reminder.data.MMReminderRepository
import com.kanishthika.moneymatters.display.transaction.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class ReminderListViewModel @Inject constructor(
    private val mmReminderRepository: MMReminderRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _reminderListUiState = MutableStateFlow<ReminderListUiState>(ReminderListUiState.Loading)
    val reminderListUiState = _reminderListUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val today: LongRange =
                localDateTimeToTimeStamp(LocalDateTime.now().toLocalDate().atStartOfDay())..
                        localDateTimeToTimeStamp(LocalDate.now().atTime(LocalTime.MAX))
            val tomorrow: LongRange = localDateTimeToTimeStamp(
                LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay()
            )..
                    localDateTimeToTimeStamp(LocalDate.now().plusDays(1).atTime(LocalTime.MAX))
            val week: LongRange = localDateTimeToTimeStamp(
                LocalDateTime.now().plusDays(2).toLocalDate().atStartOfDay()
            )..
                    localDateTimeToTimeStamp(LocalDate.now().plusDays(7).atTime(LocalTime.MAX))
            val month: LongRange = localDateTimeToTimeStamp(
                LocalDateTime.now().plusDays(8).toLocalDate().atStartOfDay()
            )..
                    localDateTimeToTimeStamp(
                        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
                            .atTime(LocalTime.MAX)
                    )
            val monthEnd: Long = localDateTimeToTimeStamp(
                LocalDateTime.now().plusDays(30).toLocalDate().atStartOfDay()
            )

            mmReminderRepository.getAllMMReminder.collectLatest { reminderList ->
                delay(1000)
                _reminderListUiState.value = if (reminderList.isEmpty()) {
                    ReminderListUiState.Empty
                } else {
                    ReminderListUiState.Success(
                        reminderList = reminderList,
                        todayReminder = reminderList.filter {
                            it.timeStamp in today
                        },
                        tomorrowReminder = reminderList.filter {
                            it.timeStamp in tomorrow
                        },
                        weekReminder = reminderList.filter {
                            it.timeStamp in week
                        },
                        monthReminder = reminderList.filter {
                            it.timeStamp in month
                        },
                        otherReminder = reminderList.filter {
                            it.timeStamp > monthEnd
                        },
                        oldReminder = reminderList.filter {
                            it.timeStamp < localDateTimeToTimeStamp(
                                LocalDateTime.now().toLocalDate().atStartOfDay()
                            )
                        },
                        openOldTransaction = false

                    )
                }
            }
        }
    }

    fun cancelReminder(reminder: MMReminder) {
        viewModelScope.launch {
            _reminderListUiState.value = ReminderListUiState.Loading
            mmReminderRepository.deleteMMReminder(reminder)
            val transaction = transactionRepository.getTxnByReminderId(reminderId = reminder.id)
            if (transaction != null) {
                transactionRepository.updateTransaction(
                    transaction.copy(reminderId = null)
                )
            }
        }
    }

    fun changeOpenOldTransaction(){
        val currentState = _reminderListUiState.value
        if (currentState is ReminderListUiState.Success) {
            val updatedState = currentState.copy(openOldTransaction =! currentState.openOldTransaction)
            _reminderListUiState.value = updatedState
        }
    }


}