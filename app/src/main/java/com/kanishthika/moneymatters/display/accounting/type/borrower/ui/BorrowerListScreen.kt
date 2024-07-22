package com.kanishthika.moneymatters.display.accounting.type.borrower.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.borrower.ui.BorrowerModel
import com.kanishthika.moneymatters.display.accounting.ui.element.ConfirmDeleteDialog
import com.kanishthika.moneymatters.display.accounting.ui.element.EmptyStateScreen
import com.kanishthika.moneymatters.display.accounting.ui.element.HeaderRow
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.FinancialItemList

@Composable
fun BorrowerListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    borrowerModel: BorrowerModel
) {
    val items by borrowerModel.items.collectAsState(initial = emptyList())
    val isLoading by borrowerModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val gson = remember { Gson() }
    var pressedItemIndex by remember { mutableIntStateOf(-1) }
    var showMonthDropdownMenu by remember { mutableStateOf(false) }
    var showYearDropdownMenu by remember { mutableStateOf(false) }
    var showSortDropDownMenu by remember { mutableStateOf(false) }

    // This variable for checked Borrower Used or not so it can be Deleted or not
    var borrowerUsed by remember { mutableStateOf(false) }

    //This Variable saved name of borrower to be deleted
    var borrowerNameDelete by remember { mutableStateOf("") }

    val monthList by borrowerModel.monthYearList.collectAsState(initial = emptyList())
    val yearList by borrowerModel.yearList.collectAsState(initial = emptyList())

    val financialUiState by borrowerModel.uiState.collectAsState()


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
                changeTextOfMonth = { borrowerModel.changeMonthText(it) },
                changeTextOfYear = { borrowerModel.changeYearText(it) },
                showSortDropdownMenu = showSortDropDownMenu,
                onSortDropdownMenuChange = { showSortDropDownMenu = it },
                sort = { borrowerModel.sortItems(it) },
                changeMonthState = { borrowerModel.changeMonth(it) },
                changeYearState = { borrowerModel.changeYear(it) },
                changeAmountViewTypeState = { borrowerModel.changeAmountViewTypeState(it) }
            )

            if (items.isEmpty()) {
                EmptyStateScreen(modifier)
            } else {
                FinancialItemList(
                    allItems = items,
                    uiState = financialUiState,
                    modifier = modifier,
                    navController = navController,
                    onShowDialogChange ={ dialog,item ->
                        showDialog = dialog
                        borrowerNameDelete = item.name
                        borrowerModel.changeSelectedItem(item)
                        borrowerModel.isEditEnabled(capitalizeWords(item.name)){borrowerUsed = it}
                    } ,
                    pressedItemIndex = pressedItemIndex ,
                    onPressedItemIndexChange ={ pressedItemIndex = it } ,
                    yearText =yearName ,
                    monthText = monthName,
                    onEditClick = { item->
                        navController.navigate(
                            NavigationItem.AddBorrower.createAddBorrowerScreen(
                                gson.toJson(item)
                            )
                        )
                    },
                    addNewItemClick = {
                        navController.navigate(
                            NavigationItem.AddBorrower.createAddBorrowerScreen(
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
                    borrowerModel.deleteItem()
                    showDialog = false
                    pressedItemIndex = -1
                },
                title = "Sure to Delete $borrowerNameDelete ?",
                text = {
                    if (borrowerUsed) {
                        Text(
                            text = "This Type Used in One or More Transactions, So You can't Delete it," +
                                    "To Delete first Edit all Transaction related this type."
                        )
                    }
                },
                confirmEnable = !borrowerUsed
            )
        }
    }
}