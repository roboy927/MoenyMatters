package com.kanishthika.moneymatters.display.accounting.type.income.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.ui.element.ConfirmDeleteDialog
import com.kanishthika.moneymatters.config.mmComposable.MMEmptyStateScreen
import com.kanishthika.moneymatters.display.accounting.ui.element.HeaderRow
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.FinancialItemList

@Composable
fun IncomeListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    incomeModel: IncomeModel
) {
    val items by incomeModel.items.collectAsState(initial = emptyList())
    val isLoading by incomeModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val gson = remember { Gson() }
    var pressedItemIndex by remember { mutableIntStateOf(-1) }
    var showMonthDropdownMenu by remember { mutableStateOf(false) }
    var showYearDropdownMenu by remember { mutableStateOf(false) }
    var showSortDropDownMenu by remember { mutableStateOf(false) }

    // This variable for checked Income Used or not so it can be Deleted or not
    var incomeUsed by remember { mutableStateOf(false) }

    //This Variable saved name of income to be deleted
    var incomeNameDelete by remember { mutableStateOf("") }

    val monthList by incomeModel.monthYearList.collectAsState(initial = emptyList())
    val yearList by incomeModel.yearList.collectAsState(initial = emptyList())

    val financialUiState by incomeModel.uiState.collectAsState()


    val monthName = financialUiState.monthText
    val yearName = financialUiState.yearText

    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier
                .padding(8.dp)
                .blur(if (showDialog || showYearDropdownMenu || showMonthDropdownMenu || showSortDropDownMenu) 6.dp else 0.dp)
        ) {
            HeaderRow(
                amountViewType = financialUiState.amountViewType,
                modifier = modifier,
                showMonthDropdownMenu = showMonthDropdownMenu,
                onMonthDropdownMenuChange = { showMonthDropdownMenu = it },
                monthList = monthList,
                showYearDropdownMenu = showYearDropdownMenu,
                onYearDropdownMenuChange = { showYearDropdownMenu = it },
                yearList = yearList,
                monthText = monthName,
                yearText = yearName,
                changeTextOfMonth = { incomeModel.changeMonthText(it) },
                changeTextOfYear = { incomeModel.changeYearText(it) },
                showSortDropdownMenu = showSortDropDownMenu,
                onSortDropdownMenuChange = { showSortDropDownMenu = it },
                sort = { incomeModel.sortItems(it) },
                changeMonthState = { incomeModel.changeMonth(it) },
                changeYearState = { incomeModel.changeYear(it) },
                changeAmountViewTypeState = { incomeModel.changeAmountViewTypeState(it) }
            )

            if (items.isEmpty()) {
                MMEmptyStateScreen(modifier)
            } else {
                FinancialItemList(
                    allItems = items,
                    uiState = financialUiState,
                    modifier = modifier,
                    navController = navController,
                    onShowDialogChange ={ dialog,item ->
                        showDialog = dialog
                        incomeNameDelete = item.name
                        incomeModel.changeSelectedItem(item)
                        incomeModel.isEditEnabled(capitalizeWords(item.name)){incomeUsed = it}
                    } ,
                    pressedItemIndex = pressedItemIndex ,
                    onPressedItemIndexChange ={ pressedItemIndex = it } ,
                    yearText =yearName ,
                    monthText = monthName,
                    onEditClick = { item->
                        navController.navigate(
                            NavigationItem.AddIncome.createAddIncomeScreen(
                                gson.toJson(item)
                            )
                        )
                    },
                    addNewItemClick = {
                        navController.navigate(
                            NavigationItem.AddIncome.createAddIncomeScreen(
                                null
                            )
                        )
                    }
                )

            }
        }

        if (showDialog) {
            ConfirmDeleteDialog(
                onDismiss = {
                    showDialog = false
                    pressedItemIndex = -1
                },
                onConfirm = {
                    incomeModel.deleteItem()
                    showDialog = false
                    pressedItemIndex = -1
                },
                title = "Sure to Delete $incomeNameDelete ?",
                text = {
                    if (incomeUsed) {
                        Text(
                            text = "This Type Used in One or More Transactions, So You can't Delete it," +
                                    "To Delete first Edit all Transaction related this type."
                        )
                    }
                },
                confirmEnable = !incomeUsed
            )
        }
    }
}