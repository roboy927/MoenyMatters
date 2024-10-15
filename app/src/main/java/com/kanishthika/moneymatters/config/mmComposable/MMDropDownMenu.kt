package com.kanishthika.moneymatters.config.mmComposable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T : Any> MMDropDownMenu(
    list: List<T>,
    name: String,
    expanded: Boolean = false,
    modifier: Modifier,
    itemToName: (T) -> String,
    onItemSelected: (T) -> Unit,
    onExpandedChange: (Boolean) -> Unit = {},
    labelText: String,
    enabled: Boolean = true
) {
    val expandedState = remember { mutableStateOf(expanded) }

    ExposedDropdownMenuBox(
        expanded = expandedState.value,
        onExpandedChange = {
            expandedState.value = !expandedState.value
            onExpandedChange(expandedState.value)
        },
    ) {
        MMOutlinedTextField(
            enabled = enabled,
            value = name,
            modifier = modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, enabled)
                .fillMaxWidth()
                .onFocusChanged {
                    expandedState.value = it.isFocused
                },
            labelText = labelText,
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false },
            modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = itemToName(item)) },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onBackground
                    ),
                    onClick = {
                        expandedState.value = false
                        onItemSelected(item)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
