package com.kanishthika.moneymatters.display.reminder.ui.addReminder

data class AddReminderUiState(
    val selectedDate: String = "",
    val selectedTime: String = "",
    val title: String = "",
    val description: String = "",
    val id: Int = 0
) {
    fun isAnyFieldEmpty(): Boolean{
        return (selectedTime.isEmpty() || selectedDate.isEmpty() || title.isEmpty() || description.isEmpty())
    }
    fun makeEmpty(): AddReminderUiState {
        return this.copy(
            selectedDate = "",
            selectedTime = "",
            title = "",
            description = "",
            id = 0
        )
    }
}
