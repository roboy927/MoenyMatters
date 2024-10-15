package com.kanishthika.moneymatters.display.reminder.ui.addReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.convertStringsToTimeStamp
import com.kanishthika.moneymatters.display.reminder.data.MMReminder
import com.kanishthika.moneymatters.display.reminder.data.MMReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddReminderViewModel @Inject constructor(
    private val mmReminderRepository: MMReminderRepository
) : ViewModel() {

    private val _addReminderUiState = MutableStateFlow(AddReminderUiState())
    val addReminderUiState = _addReminderUiState.asStateFlow()

    init {
        viewModelScope.launch {
           _addReminderUiState.update {
               it.copy(
                   id = mmReminderRepository.getMAXId() + 1
               )
           }
        }
    }

    fun updateSelectedDate(date: String) {
        _addReminderUiState.update {
            it.copy(
                selectedDate = date
            )
        }
    }

    fun updateSelectedTime(time: String) {
        _addReminderUiState.update {
            it.copy(
                selectedTime = time
            )
        }
    }

    fun updateTitle(title: String) {
        _addReminderUiState.update {
            it.copy(
                title = title
            )
        }
    }

    fun updateDescription(description: String) {
        _addReminderUiState.update {
            it.copy(
                description = capitalizeWords(description)
            )
        }
    }

    fun addReminder(setAlarm: (Long) -> Unit) {
        viewModelScope.launch {
            val timeStamp = convertStringsToTimeStamp(
                addReminderUiState.value.selectedDate,
                addReminderUiState.value.selectedTime
            )
            mmReminderRepository.insertMMReminder(
                MMReminder(
                    addReminderUiState.value.id,
                    capitalizeWords(addReminderUiState.value.title) ,
                    capitalizeWords(addReminderUiState.value.description) ,
                    timeStamp
                )
            )
            setAlarm(timeStamp)
        }
    }

    fun updateReminder(reminder: MMReminder){
        viewModelScope.launch {
            mmReminderRepository.updateMMReminder(reminder)
        }
    }



}