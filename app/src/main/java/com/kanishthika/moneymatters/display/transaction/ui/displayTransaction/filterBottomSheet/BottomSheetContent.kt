package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetContent(
    modifier: Modifier,
    firstLevelFilter: List<FirstLevelFilter>,
    onClickFirstFilterItem: (FirstLevelFilter) -> Unit,
    secondFilterList: List<String>,
    checkBoxState: Set<Int>,
    onCheckedChange: (Boolean, String, Int) -> Unit,
    onApplyFilter: () -> Unit,
    buttonText: String,
    checkCounts: Map<FirstLevelFilter, Int>,
    enabledButton: Boolean,
    resetFilter: () -> Unit,
    onCancel: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filter & Sort",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f),
            )
            Row {
                Text(
                    text = "Reset Filter",
                    modifier = modifier
                        .border(
                            0.5.dp,
                            MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f),
                            RoundedCornerShape(30)
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable {
                         resetFilter()
                        },
                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = modifier.width(10.dp))

                Icon(imageVector = Icons.Default.Clear,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f),
                    modifier = modifier.clickable {
                      onCancel()
                    }
                )
            }
        }
    }

    Column(
        modifier = modifier.background(Color(0xFF4A4872))
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            LazyColumn(
                modifier = modifier.weight(1f)
            ) {
                itemsIndexed(firstLevelFilter) { index, item ->
                    val backGroundColor =
                        if (selectedIndex == index) Color(0xFF353454) else Color.Transparent
                    val checkCount = checkCounts[item] ?: 0
                    ItemBox(
                        string = item.name,
                        checkCount = checkCount,
                        modifier = modifier,
                        onClick = {
                            selectedIndex = index
                            onClickFirstFilterItem(item)
                        },
                        backGroundColor = backGroundColor
                    )
                }
            }
            Column(
                modifier = modifier.weight(1f)
            ) {
                CheckBoxListScreen(
                    modifier = modifier,
                    secondFilterList = secondFilterList,
                    checkBoxState = checkBoxState,
                    onCheckedChange = onCheckedChange
                )
            }
        }
        Box(
            modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth()
                .background(if (enabledButton) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.outline.copy(0.4f))
                .clickable(enabled = enabledButton) {
                    onApplyFilter()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = if (enabledButton) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onTertiaryContainer.copy(
                    0.5f
                ),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}


@Composable
fun ItemBox(
    string: String,
    checkCount: Int,
    modifier: Modifier,
    onClick: () -> Unit,
    backGroundColor: Color = Color.Transparent
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(backGroundColor)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = string,
            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(.8f),
            maxLines = 1,
        )
        if (checkCount > 0) {
            Text(
                text = checkCount.toString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF353454),
                modifier = modifier
                    .background(
                        MaterialTheme.colorScheme.onTertiaryContainer.copy(.6f),
                        shape = CircleShape
                    )
                    .padding(3.dp)
            )
        }
    }
}
