package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxListScreen(
    modifier: Modifier,
    secondFilterList: List<String>,
    checkBoxState: Set<Int>,
    onCheckedChange: (Boolean, String, Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF353454))
            .padding(8.dp)

    ) {
        LazyColumn {
            items(secondFilterList.size) { index ->
                val item = secondFilterList[index]
                val checked = checkBoxState.contains(index)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { isChecked ->
                            onCheckedChange(isChecked, item, index)
                        },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(.8f),
                            checkedColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    Text(
                        text = item,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(.8f)
                    )
                }
            }
        }
    }
}
