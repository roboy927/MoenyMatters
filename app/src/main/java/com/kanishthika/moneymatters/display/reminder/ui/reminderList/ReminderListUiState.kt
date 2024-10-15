package com.kanishthika.moneymatters.display.reminder.ui.reminderList

import com.kanishthika.moneymatters.display.reminder.data.MMReminder

sealed class ReminderListUiState {
    object Loading: ReminderListUiState()
    object Empty: ReminderListUiState()
    data class Success(
        val reminderList: List<MMReminder> = emptyList(),
        val todayReminder: List<MMReminder> = emptyList(),
        val tomorrowReminder: List<MMReminder> = emptyList(),
        val weekReminder: List<MMReminder> = emptyList(),
        val monthReminder: List<MMReminder> = emptyList(),
        val otherReminder: List<MMReminder> = emptyList(),
        val oldReminder: List<MMReminder> = emptyList(),
        val openOldTransaction: Boolean = false
    ): ReminderListUiState()
}