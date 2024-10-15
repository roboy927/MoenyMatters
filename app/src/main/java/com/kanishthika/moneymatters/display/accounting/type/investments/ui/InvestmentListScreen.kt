package com.kanishthika.moneymatters.display.accounting.type.investments.ui

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
fun InvestmentListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    investmentModel: InvestmentModel
) {
    val items by investmentModel.items.collectAsState(initial = emptyList())
    val isLoading by investmentModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val gson = remember { Gson() }
    var pressedItemIndex by remember { mutableIntStateOf(-1) }
    var showMonthDropdownMenu by remember { mutableStateOf(false) }
    var showYearDropdownMenu by remember { mutableStateOf(false) }
    var showSortDropDownMenu by remember { mutableStateOf(false) }

    // This variable for checked Investment Used or not so it can be Deleted or not
    var investmentUsed by remember { mutableStateOf(false) }

    //This Variable saved name of investment to be deleted
    var investmentNameDelete by remember { mutableStateOf("") }

    val monthList by investmentModel.monthYearList.collectAsState(initial = emptyList())
    val yearList by investmentModel.yearList.collectAsState(initial = emptyList())

    val financialUiState by investmentModel.uiState.collectAsState()


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
                changeTextOfMonth = { investmentModel.changeMonthText(it) },
                changeTextOfYear = { investmentModel.changeYearText(it) },
                showSortDropdownMenu = showSortDropDownMenu,
                onSortDropdownMenuChange = { showSortDropDownMenu = it },
                sort = { investmentModel.sortItems(it) },
                changeMonthState = { investmentModel.changeMonth(it) },
                changeYearState = { investmentModel.changeYear(it) },
                changeAmountViewTypeState = { investmentModel.changeAmountViewTypeState(it) }
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
                        investmentNameDelete = item.name
                        investmentModel.changeSelectedItem(item)
                        investmentModel.isEditEnabled(capitalizeWords(item.name)){investmentUsed = it}
                    } ,
                    pressedItemIndex = pressedItemIndex ,
                    onPressedItemIndexChange ={ pressedItemIndex = it } ,
                    yearText =yearName ,
                    monthText = monthName,
                    onEditClick = { item->
                        navController.navigate(
                            NavigationItem.AddInvestment.createAddInvestmentScreen(
                                gson.toJson(item)
                            )
                        )
                    },
                    addNewItemClick = {
                        navController.navigate(
                            NavigationItem.AddInvestment.createAddInvestmentScreen(
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
                    investmentModel.deleteItem()
                    showDialog = false
                    pressedItemIndex = -1
                },
                title = "Sure to Delete $investmentNameDelete ?",
                text = {
                    if (investmentUsed) {
                        Text(
                            text = "This Type Used in One or More Transactions, So You can't Delete it," +
                                    "To Delete first Edit all Transaction related this type."
                        )
                    }
                },
                confirmEnable = !investmentUsed
            )
        }
    }
}
