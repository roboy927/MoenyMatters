package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.display.label.data.Label

@Composable
fun OptionScreen(
    modifier: Modifier,
    optionState: Boolean,
    optionStateChange: () -> Unit,
    splitOptions: SplitOptions,
    splitEnable: () -> Unit,
    reminderAdded: Boolean,
    addReminderDialog: () -> Unit,
    selectLabelDialog: () -> Unit,
    selectedLabel: Label
) {

    val textColor: Color = MaterialTheme.colorScheme.secondary
    val activeTextColor: Color = MaterialTheme.colorScheme.primary

    Column(
        modifier = modifier
            .border(
                1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(
                    dimensionResource(
                        id = R.dimen.uni_corner_radius
                    )
                )
            )
            .animateContentSize()
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    optionStateChange()
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Options", color = MaterialTheme.colorScheme.secondary)
            Box(
                modifier = modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        if (optionState) {
            Text(
                text = if(!reminderAdded)"Add Reminder For This Transaction" else "Edit Reminder",
                color = if(!reminderAdded) textColor else activeTextColor,
                modifier = modifier.clickable {
                    addReminderDialog()
                    optionStateChange()
                }
            )
            Text(
                text = if (selectedLabel.labelName.isEmpty())"Add Label" else "Label: ${selectedLabel.labelName}" ,
                color =if(selectedLabel.labelName.isEmpty()) textColor else activeTextColor,
                modifier = modifier.clickable {
                    selectLabelDialog()
                    optionStateChange()
                }
            )
            if (splitOptions == SplitOptions.None) {
                Text(
                    text = "Split Transaction",
                    color = textColor,
                    modifier = modifier.clickable {
                        splitEnable()
                        optionStateChange()
                    }
                )
            }
        }
    }
}