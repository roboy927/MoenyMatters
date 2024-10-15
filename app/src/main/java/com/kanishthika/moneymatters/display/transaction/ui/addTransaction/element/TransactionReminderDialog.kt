package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMDatePickerInput
import com.kanishthika.moneymatters.config.mmComposable.MMOutlinedButton
import com.kanishthika.moneymatters.config.mmComposable.MMOutlinedTextField
import com.kanishthika.moneymatters.config.mmComposable.MMTimePicker
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.AddTransactionEvent
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.AddTransactionModel2
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionReminderDialog(
    modifier: Modifier,
    addTransactionModel2: AddTransactionModel2,
    onClose:()-> Unit
) {
    val reminderUiState by addTransactionModel2.reminderState.collectAsStateWithLifecycle()

    val backGroundColor: Color = MaterialTheme.colorScheme.primaryContainer

    BackHandler {
        onClose()
    }
    LaunchedEffect(key1 = Unit) {
        addTransactionModel2.onEvent(AddTransactionEvent.SetReminderDescription(addTransactionModel2.addTransactionState.value.description))
    }

    Column(
        modifier = modifier
            .background(backGroundColor, RoundedCornerShape(5))
            .padding(dimensionResource(id = R.dimen.uni_screen_padding)),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Reminder",
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Close",
                modifier = modifier.clickable {
                    addTransactionModel2.onEvent(AddTransactionEvent.EmptyReminderState)
                    addTransactionModel2.onEvent(AddTransactionEvent.UpdateHasReminder(false))
                    onClose()
                },
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        MMOutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = reminderUiState.title,
            onValueChange = { addTransactionModel2.onEvent(AddTransactionEvent.SetReminderTitle(it))},
            labelText = "Reminder Title",
            containerColor = backGroundColor
        )
        MMDatePickerInput(
            modifier = modifier,
            date = reminderUiState.date,
            onDateSelected = { date ->
                    addTransactionModel2.onEvent(AddTransactionEvent.SetReminderDate(date))
            },
            initialDate = LocalDate.now(),
            yearRange = IntRange(LocalDate.now().year, 2050),
            backgroundColor = backGroundColor
        )
        MMTimePicker(
            reminderUiState.time,
            modifier,
            0,
            8,
            backGroundColor = backGroundColor
        ) { time ->    // "HH : mm"
                addTransactionModel2.onEvent(AddTransactionEvent.SetReminderTime(time))
        }
        MMOutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = reminderUiState.description,
            onValueChange = { addTransactionModel2.onEvent(AddTransactionEvent.SetReminderDescription(it)) },
            labelText = "Description",
            containerColor = backGroundColor
        )
        Spacer(modifier = modifier)
        MMOutlinedButton(
            modifier = modifier,
            text = "Save",
            enabled = (reminderUiState.title.isNotEmpty() && reminderUiState.date.isNotEmpty() && reminderUiState.time.isNotEmpty() && reminderUiState.description.isNotEmpty())
        ) {
            addTransactionModel2.onEvent(AddTransactionEvent.UpdateHasReminder(true))
            onClose()
        }
    }
}