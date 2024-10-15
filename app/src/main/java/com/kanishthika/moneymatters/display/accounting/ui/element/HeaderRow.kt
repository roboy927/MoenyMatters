package com.kanishthika.moneymatters.display.accounting.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMActionBarItem
import com.kanishthika.moneymatters.display.accounting.data.AmountViewType

@Composable
fun HeaderRow(
    amountViewType: AmountViewType,
    modifier: Modifier,
    sortingList: List<String> = listOf(
        "Name Ascending",
        "Name Descending",
        "Amount Ascending",
        "Amount Descending"
    ),
    showSortDropdownMenu: Boolean,
    onSortDropdownMenuChange: (Boolean) -> Unit,
    showMonthDropdownMenu: Boolean,
    onMonthDropdownMenuChange: (Boolean) -> Unit,
    monthList: List<String>,
    showYearDropdownMenu: Boolean,
    onYearDropdownMenuChange: (Boolean) -> Unit,
    yearList: List<String>,
    monthText: String,
    yearText: String,
    changeTextOfMonth: (String) -> Unit,
    changeTextOfYear: (String) -> Unit,
    sort: (String) -> Unit,
    changeMonthState: (String) -> Unit,
    changeYearState: (String) -> Unit,
    changeAmountViewTypeState: (AmountViewType) -> Unit
) {
    Row(
        modifier = modifier
            .padding(8.dp, 6.dp, 8.dp, 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            MMActionBarItem(
                text = "Sort",
                click = Modifier.clickable { onSortDropdownMenuChange(true) },
                modifier = modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.sort),
                border = Modifier,
                iconSize = 15.dp,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            DropdownMenu(
                modifier = modifier.heightIn(max = 300.dp).background(MaterialTheme.colorScheme.inverseSurface),
                expanded = showSortDropdownMenu,
                onDismissRequest = { onSortDropdownMenuChange(false) }
            ) {
                sortingList.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it) },
                        onClick = {
                            sort(it)
                            onSortDropdownMenuChange(false)
                        }
                    )
                }
            }
        }
        Row {
            Box {
                if (amountViewType == AmountViewType.MONTH) {
                    MMActionBarItem(
                        text = monthText,
                        click = Modifier.clickable { onMonthDropdownMenuChange(true) },
                        modifier = modifier,
                        imageVector = Icons.Default.ArrowDropDown,
                        border = Modifier,
                        iconSize = 20.dp,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    DropdownMenu(
                        modifier = modifier.heightIn(max = 300.dp).background(MaterialTheme.colorScheme.inverseSurface),
                        expanded = showMonthDropdownMenu,
                        onDismissRequest = { onMonthDropdownMenuChange(false) }
                    ) {
                        monthList.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    changeMonthState(it)
                                    onMonthDropdownMenuChange(false)
                                    changeTextOfMonth(it)
                                }
                            )
                        }
                    }
                }
            }
            Box {
                if (amountViewType == AmountViewType.YEAR) {
                    MMActionBarItem(
                        text = yearText,
                        click = Modifier.clickable { onYearDropdownMenuChange(true) },
                        modifier = modifier,
                        imageVector = Icons.Default.ArrowDropDown,
                        border = Modifier,
                        iconSize = 15.dp,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    DropdownMenu(
                        modifier = modifier.heightIn(max = 300.dp).background(MaterialTheme.colorScheme.inverseSurface),
                        expanded = showYearDropdownMenu,
                        onDismissRequest = { onYearDropdownMenuChange(false) }
                    ) {
                        yearList.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    changeYearState(it)
                                    onYearDropdownMenuChange(false)
                                    changeTextOfYear(it)
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = modifier.width(4.dp))
            MMActionBarItem(
                text = when (amountViewType) {
                    AmountViewType.TOTAL -> "Total Amount"
                    AmountViewType.MONTH -> "Month Amount"
                    AmountViewType.YEAR -> "Year Amount"
                },
                modifier = modifier,
                imageVector = Icons.Sharp.MoreVert,
                border = Modifier,
                iconSize = 15.dp,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                click = Modifier.clickable {
                    changeAmountViewTypeState(
                        when (amountViewType) {
                            AmountViewType.TOTAL -> AmountViewType.MONTH
                            AmountViewType.MONTH -> AmountViewType.YEAR
                            AmountViewType.YEAR -> AmountViewType.TOTAL
                        }
                    )
                }
            )
        }
    }
}
