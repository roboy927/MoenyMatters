package com.kanishthika.moneymatters.display.accounting.type.insurance.ui

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
fun InsuranceListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    insuranceModel: InsuranceModel
) {
    val items by insuranceModel.items.collectAsState(initial = emptyList())
    val isLoading by insuranceModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val gson = remember { Gson() }
    var pressedItemIndex by remember { mutableIntStateOf(-1) }
    var showMonthDropdownMenu by remember { mutableStateOf(false) }
    var showYearDropdownMenu by remember { mutableStateOf(false) }
    var showSortDropDownMenu by remember { mutableStateOf(false) }

    // This variable for checked Insurance Used or not so it can be Deleted or not
    var insuranceUsed by remember { mutableStateOf(false) }

    //This Variable saved name of insurance to be deleted
    var insuranceNameDelete by remember { mutableStateOf("") }

    val monthList by insuranceModel.monthYearList.collectAsState(initial = emptyList())
    val yearList by insuranceModel.yearList.collectAsState(initial = emptyList())

    val financialUiState by insuranceModel.uiState.collectAsState()


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
                changeTextOfMonth = { insuranceModel.changeMonthText(it) },
                changeTextOfYear = { insuranceModel.changeYearText(it) },
                showSortDropdownMenu = showSortDropDownMenu,
                onSortDropdownMenuChange = { showSortDropDownMenu = it },
                sort = { insuranceModel.sortItems(it) },
                changeMonthState = { insuranceModel.changeMonth(it) },
                changeYearState = { insuranceModel.changeYear(it) },
                changeAmountViewTypeState = { insuranceModel.changeAmountViewTypeState(it) }
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
                        insuranceNameDelete = item.name
                        insuranceModel.changeSelectedItem(item)
                        insuranceModel.isEditEnabled(capitalizeWords(item.name)){insuranceUsed = it}
                    } ,
                    pressedItemIndex = pressedItemIndex ,
                    onPressedItemIndexChange ={ pressedItemIndex = it } ,
                    yearText =yearName ,
                    monthText = monthName,
                    onEditClick = { item->
                        navController.navigate(
                            NavigationItem.AddInsurance.createAddInsuranceScreen(
                                gson.toJson(item)
                            )
                        )
                    },
                    addNewItemClick = {
                        navController.navigate(
                            NavigationItem.AddInsurance.createAddInsuranceScreen(
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
                    insuranceModel.deleteItem()
                    showDialog = false
                    pressedItemIndex = -1
                },
                title = "Sure to Delete $insuranceNameDelete ?",
                text = {
                    if (insuranceUsed) {
                        Text(
                            text = "This Type Used in One or More Transactions, So You can't Delete it," +
                                    "To Delete first Edit all Transaction related this type."
                        )
                    }
                },
                confirmEnable = !insuranceUsed
            )
        }
    }
}