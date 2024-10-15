package com.kanishthika.moneymatters.display.reminder.ui.addReminder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMBottomAppBarButton
import com.kanishthika.moneymatters.config.mmComposable.MMDatePickerInput
import com.kanishthika.moneymatters.config.mmComposable.MMOutlinedTextField
import com.kanishthika.moneymatters.config.mmComposable.MMTimePicker
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.config.reminder.NotificationPermissionHandler
import com.kanishthika.moneymatters.config.reminder.ReminderAlarmManager
import com.kanishthika.moneymatters.config.utils.convertStringsToTimeStamp
import com.kanishthika.moneymatters.config.utils.separateDateFromTimeStamp
import com.kanishthika.moneymatters.config.utils.separateTimeFromTimeStamp
import com.kanishthika.moneymatters.display.reminder.data.MMReminder
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(
    modifier: Modifier,
    addReminderViewModel: AddReminderViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    reminder: MMReminder? = null,
){

    val addReminderUiState by addReminderViewModel.addReminderUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val reminderAlarmManager = ReminderAlarmManager(context)

    NotificationPermissionHandler()
    LaunchedEffect(Unit) {
        if(reminder != null){
            addReminderViewModel.updateTitle(reminder.title)
            addReminderViewModel.updateDescription(reminder.description)
            addReminderViewModel.updateSelectedDate(separateDateFromTimeStamp(reminder.timeStamp))
            addReminderViewModel.updateSelectedTime(separateTimeFromTimeStamp(reminder.timeStamp))
        }
    }

    Scaffold(
        topBar = { MMTopAppBar(titleText = "Add Reminder") },
        bottomBar = { MMBottomAppBarButton(
            bottomBarText = "Add Reminder",
            enabled = !addReminderUiState.isAnyFieldEmpty(),
            modifier = modifier
        ) {
            if (reminder != null){
                addReminderViewModel.updateReminder(
                    reminder.copy(
                        id = reminder.id,
                        title = addReminderUiState.title,
                        description = addReminderUiState.description,
                        timeStamp = convertStringsToTimeStamp(
                            addReminderUiState.selectedDate,
                            addReminderUiState.selectedTime
                        )
                    )
                )
                reminderAlarmManager.cancelAlarm(reminder.id)
                reminderAlarmManager.setAlarm(
                    reminderTitle = addReminderUiState.title,
                    reminderId = reminder.id,
                    timeMillis = convertStringsToTimeStamp(
                        addReminderUiState.selectedDate,
                        addReminderUiState.selectedTime
                    )
                )
            } else {
                addReminderViewModel.addReminder { timeStamp ->
                    reminderAlarmManager.setAlarm(
                        reminderTitle = addReminderUiState.title,
                        reminderId = addReminderUiState.id,
                        timeMillis = timeStamp
                    )
                    navigateUp()
                }
            }
        }}
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(dimensionResource(id = R.dimen.uni_screen_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing))
        ) {
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = addReminderUiState.title,
                onValueChange = addReminderViewModel::updateTitle,
                labelText = "Reminder Title"
            )
            MMDatePickerInput(
                modifier = modifier,
                date = addReminderUiState.selectedDate,
                onDateSelected = { date ->
                    addReminderViewModel.updateSelectedDate(date)
                },
                initialDate = LocalDate.now(),
                yearRange = IntRange(LocalDate.now().year, 2050)
            )
            MMTimePicker(
                addReminderUiState.selectedTime,
                modifier,
                0,
                8
            ){ time ->    // "HH : mm"
                addReminderViewModel.updateSelectedTime(time)
            }
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = addReminderUiState.description,
                onValueChange = { description-> addReminderViewModel.updateDescription(description)},
                labelText = "Description"
            )

        }
    }

}
