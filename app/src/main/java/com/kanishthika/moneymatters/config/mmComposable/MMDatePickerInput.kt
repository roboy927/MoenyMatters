package com.kanishthika.moneymatters.config.mmComposable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.kanishthika.moneymatters.R
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MMDatePickerInput(
    modifier: Modifier,
    date: String,
    onDateSelected: (String) -> Unit,
    labelText: String = "Date",
    initialDate: LocalDate?,
    initialDisplayMode: DisplayMode = DisplayMode.Picker,
    yearRange: IntRange = IntRange(2000,2099),
    backgroundColor: Color = MaterialTheme.colorScheme.background

) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    if (showDatePicker) {
        MMDatePickerDialog(
            onDateSelected = onDateSelected,
            onDismiss = { showDatePicker = false },
            initialDate = initialDate,
            initialDisplayMode = initialDisplayMode,
            yearRange = yearRange
        )
    }
    MMOutlinedTextField(
        trailingIcon = { Icon(Icons.Filled.DateRange, contentDescription = "DateRange") },
        labelText = labelText,
        enabled = false,
        value = date,
        containerColor = backgroundColor,
        disableColor = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.uni_corner_radius)))
            .fillMaxWidth()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                showDatePicker = true
            }
    )
}






