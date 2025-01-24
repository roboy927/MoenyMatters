package com.kanishthika.moneymatters.display.accounting.ui.financialGenerics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.clickableOnce
import com.kanishthika.moneymatters.display.accounting.data.AmountViewType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.accounting.ui.element.listOfDropDownItem

@Composable
fun <T : FinancialItem> FinancialItemList(
    allItems: List<T>,
    uiState: FinancialUiState<T>,
    modifier: Modifier,
    navController: NavController,
    onShowDialogChange: (Boolean, T) -> Unit,
    pressedItemIndex: Int,
    onPressedItemIndexChange: (Int) -> Unit,
    onEditClick: (T) -> Unit,
    addNewItemClick: () -> Unit,
    yearText: String,
    monthText: String,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            itemsIndexed(allItems) { index, item ->
                FinancialItemDetailBox(
                    modifier = modifier,
                    item = item,
                    itemAmount = if (uiState.isAmountLoading) "---" else when (uiState.amountViewType) {
                        AmountViewType.TOTAL -> "${item.amount + (uiState.monthlyAmounts[capitalizeWords(item.name)] ?: 0.0)}"
                        AmountViewType.MONTH -> uiState.monthlyAmounts[capitalizeWords(item.name)]?.toString() ?: "0.0"
                        AmountViewType.YEAR -> uiState.monthlyAmounts[capitalizeWords(item.name)]?.toString() ?: "0.0"
                    },
                    onItemClick = {
                        when (it.text) {
                            "Transaction" -> navController.navigate(
                                NavigationItem.SearchTransactionScreen.searchTransactionScreen(item.name)
                            )
                            "Delete" -> {
                                onShowDialogChange(true, item)
                            }
                            "Edit" -> {
                                onEditClick(item)
                            }
                        }
                    },
                    dropdownItems = listOfDropDownItem,
                    itemBlur = if (pressedItemIndex == -1 || pressedItemIndex == index) 0.dp else 3.dp,
                    onLongPress = { onPressedItemIndexChange(index) },
                    onDismiss = { onPressedItemIndexChange(-1) }
                )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableOnce {
                           addNewItemClick()
                        }
                        .padding(12.dp),
                ) {
                    Text(
                        text = "Add New Item",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30))
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(0.4f), RoundedCornerShape(30))
                .padding(horizontal = 12.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (uiState.amountViewType) {
                    AmountViewType.TOTAL -> "Total Amount"
                    AmountViewType.MONTH -> "Amount of $monthText"
                    AmountViewType.YEAR -> "Amount of $yearText"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(id = R.string.rupee_symbol) + " " + if (uiState.isAmountLoading) "---"
                else when (uiState.amountViewType) {
                    AmountViewType.TOTAL -> (allItems.sumOf { it.amount } + uiState.monthlyAmounts.values.sum()).toString()
                    AmountViewType.MONTH -> uiState.monthlyAmounts.values.sum().toString()
                    AmountViewType.YEAR -> uiState.monthlyAmounts.values.sum().toString()
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
