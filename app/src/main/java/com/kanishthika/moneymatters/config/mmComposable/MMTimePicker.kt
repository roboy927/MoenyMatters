package com.kanishthika.moneymatters.config.mmComposable

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.kanishthika.moneymatters.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun MMTimePicker(
    displayTime: String,
    modifier: Modifier,
    initialMinute: Int,
    initialHour: Int,
    backGroundColor: Color = MaterialTheme.colorScheme.background,
    onSelectTime: (String) -> Unit,
) {

    val timePickerState = rememberTimePickerState(
        initialMinute = initialMinute,
        initialHour = initialHour,
        is24Hour = true
    )

    var showTimePickerDialog by remember {
        mutableStateOf(false)
    }

    if (showTimePickerDialog) {
        MMTimePickerDialog(
            onDismissRequest = {
                showTimePickerDialog = false
            },
            confirmButton = {
                Text(
                    text = "Select",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = modifier.clickable {
                        showTimePickerDialog = false
                        val selectedTime = "${timePickerState.hour.toString().padStart(2,'0')} : " +
                                timePickerState.minute.toString().padStart(2, '0')
                        onSelectTime(selectedTime)
                    }
                )
            }
        ) {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = MaterialTheme.colorScheme.onBackground,
                    clockDialUnselectedContentColor = MaterialTheme.colorScheme.background,
                    timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.secondary,
                    timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onBackground,
                    timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(0.4f),
                    timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.secondary.copy(0.4f)
                )
            )
        }
    }

    MMOutlinedTextField(
        trailingIcon = { Icon(Icons.Filled.DateRange, contentDescription = "DateRange") },
        labelText = "Select Time",
        enabled = false,
        value = displayTime,
        disableColor = MaterialTheme.colorScheme.secondary,
        containerColor = backGroundColor,
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.uni_corner_radius)))
            .fillMaxWidth()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                showTimePickerDialog = true
            }
    )
}