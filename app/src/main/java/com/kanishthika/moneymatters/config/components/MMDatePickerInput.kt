package com.kanishthika.moneymatters.config.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip


@Composable
fun MMDatePickerInput(
    modifier: Modifier,
    date: String,
    onDateSelected: (String) -> Unit

) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    if (showDatePicker) {
        MMDatePickerDialog(
            onDateSelected = onDateSelected,
            onDismiss = { showDatePicker = false },

        )
    }
    MMOutlinedTextField(
        trailingIcon = { Icon(Icons.Filled.DateRange, contentDescription = "DateRange") },
        labelText = "Date",
        enabled = false,
        value = date,
        disableColor = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .clip(RoundedCornerShape(50, 50, 40, 40))
            .fillMaxWidth()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null){
                showDatePicker = true
            }


    )


}






