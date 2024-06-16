package com.kanishthika.moneymatters.config.components

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kanishthika.moneymatters.config.theme.MoneyMattersTheme
import com.kanishthika.moneymatters.config.utils.toEpochMillisUTC
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MMDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now().toEpochMillisUTC(),
        yearRange = IntRange(2023,2099),
        initialDisplayMode = DisplayMode.Picker
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    val focusedTextFieldColor = MaterialTheme.colorScheme.tertiary
    val unfocusedTextFieldColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
    val focusedTextColor = MaterialTheme.colorScheme.onBackground
    val unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(0.5f)

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(text = "OK")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.inverseSurface,

        )

    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedYearContainerColor = MaterialTheme.colorScheme.tertiary,
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                yearContentColor = MaterialTheme.colorScheme.onBackground,
                navigationContentColor = MaterialTheme.colorScheme.tertiary,
                titleContentColor = MaterialTheme.colorScheme.tertiary,
                headlineContentColor = MaterialTheme.colorScheme.onBackground,
                dayContentColor = MaterialTheme.colorScheme.onBackground,
                weekdayContentColor = MaterialTheme.colorScheme.secondary,
                selectedDayContainerColor = MaterialTheme.colorScheme.tertiary,
                todayDateBorderColor = MaterialTheme.colorScheme.tertiary,
                todayContentColor = MaterialTheme.colorScheme.onBackground,
                currentYearContentColor = MaterialTheme.colorScheme.onBackground,
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = focusedTextFieldColor,
                    focusedPlaceholderColor = focusedTextFieldColor,
                    focusedTextColor = focusedTextColor,
                    focusedLabelColor = focusedTextFieldColor,
                    unfocusedIndicatorColor = unfocusedTextFieldColor,
                    unfocusedPlaceholderColor = unfocusedTextFieldColor,
                    unfocusedTextColor = unfocusedTextColor,
                    unfocusedLabelColor = unfocusedTextFieldColor,
                    cursorColor = focusedTextColor,

                )

            )

        )
    }
}

@SuppressLint("SimpleDateFormat")
private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy")
    return formatter.format(Date(millis))
}

@Preview
@Composable
fun Preview() {
    MoneyMattersTheme {

        MMDatePickerDialog(onDateSelected = {}) {

        }
    }
}