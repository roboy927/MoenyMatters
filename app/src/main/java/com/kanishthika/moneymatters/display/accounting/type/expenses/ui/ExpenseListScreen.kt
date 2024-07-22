package com.kanishthika.moneymatters.display.accounting.type.expenses.ui

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
import com.kanishthika.moneymatters.display.accounting.ui.element.EmptyStateScreen
import com.kanishthika.moneymatters.display.accounting.ui.element.HeaderRow
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.FinancialItemList


@Composable
fun ExpenseListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    expenseModel: ExpenseModel
) {
    val items by expenseModel.items.collectAsState(initial = emptyList())
    val isLoading by expenseModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val gson = remember { Gson() }
    var pressedItemIndex by remember { mutableIntStateOf(-1) }
    var showMonthDropdownMenu by remember { mutableStateOf(false) }
    var showYearDropdownMenu by remember { mutableStateOf(false) }
    var showSortDropDownMenu by remember { mutableStateOf(false) }

    // This variable for checked Expense Used or not so it can be Deleted or not


    //This Variable saved name of expense to be deleted
    var expenseNameDelete by remember { mutableStateOf("") }

    val monthList by expenseModel.monthYearList.collectAsState(initial = emptyList())
    val yearList by expenseModel.yearList.collectAsState(initial = emptyList())

    val financialUiState by expenseModel.uiState.collectAsState()

    var expenseUsed by remember {
        mutableStateOf(false)
    }


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
                changeTextOfMonth = { expenseModel.changeMonthText(it) },
                changeTextOfYear = { expenseModel.changeYearText(it) },
                showSortDropdownMenu = showSortDropDownMenu,
                onSortDropdownMenuChange = { showSortDropDownMenu = it },
                sort = { expenseModel.sortItems(it) },
                changeMonthState = { expenseModel.changeMonth(it) },
                changeYearState = { expenseModel.changeYear(it) },
                changeAmountViewTypeState = { expenseModel.changeAmountViewTypeState(it) }
            )

            if (items.isEmpty()) {
                EmptyStateScreen(modifier)
            } else {
                FinancialItemList(
                    allItems = items,
                    uiState = financialUiState,
                    modifier = modifier,
                    navController = navController,
                    onShowDialogChange = { dialog, item ->
                        expenseModel.isEditEnabled(capitalizeWords(item.name)){ it ->
                            expenseUsed = !it
                        }
                        showDialog = dialog
                        expenseNameDelete = item.name
                        expenseModel.changeSelectedItem(item)

                    },
                    pressedItemIndex = pressedItemIndex,
                    onPressedItemIndexChange = { pressedItemIndex = it },
                    yearText = yearName,
                    monthText = monthName,
                    onEditClick = { item ->
                        navController.navigate(
                            NavigationItem.AddExpense.createAddExpenseScreen(
                                gson.toJson(item)
                            )
                        )
                    },
                    addNewItemClick = {
                        navController.navigate(
                            NavigationItem.AddExpense.createAddExpenseScreen(
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
                    expenseModel.deleteItem()
                    showDialog = false
                    pressedItemIndex = -1
                },
                title = "Sure to Delete $expenseNameDelete ?",
                text = {
                    if (expenseUsed) {
                        Text(
                            text = "This Type Used in One or More Transactions, So You can't Delete it," +
                                    "To Delete first Edit all Transaction related this type."
                        )
                    }
                },
                confirmEnable = !expenseUsed
            )
        }
    }
}

/*
@Composable
fun ExpenseList(
    allExpenses: List<Expense>,
    expenseUiState: ExpenseUiState,
    modifier: Modifier,
    navController: NavController,
    onShowDialogChange: (Boolean, Double, String) -> Unit,
    pressedItemIndex: Int,
    onPressedItemIndexChange: (Int) -> Unit,
    expenseModel: ExpenseModel,
    gson: Gson,
    yearText: String,
    monthText: String,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            itemsIndexed(allExpenses) { index, expense ->
                ExpenseDetailBox(
                    modifier = modifier,
                    expenseName = expense.name,
                    expenseAmount = if (expenseUiState.isAmountLoading) "---" else when (expenseUiState.amountViewType) {
                        AmountViewType.TOTAL -> expense.amount.toString()
                        AmountViewType.MONTH -> expenseUiState.monthlyAmounts[capitalizeWords(
                            expense.name
                        )]?.toString() ?: "0.0"

                        AmountViewType.YEAR -> expenseUiState.monthlyAmounts[capitalizeWords(
                            expense.name
                        )]?.toString() ?: "0.0"
                    },
                    expenseDescription = expense.description,
                    dropdownItems = listOfDropDownItem,
                    onItemClick = {
                        when (it.text) {
                            "Transaction" -> navController.navigate(
                                NavigationItem.SearchTransactionScreen.searchTransactionScreen(
                                    expense.name
                                )
                            )

                            "Delete" -> {
                                onShowDialogChange(true, expense.amount, expense.name)
                                expenseModel.changeSelectedExpense(expense)
                            }

                            "Edit" -> navController.navigate(
                                NavigationItem.AddExpense.createAddExpenseScreen(
                                    gson.toJson(expense)
                                )
                            )
                        }
                    },
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
                    modifier = modifier
                        .fillMaxWidth()
                        .clickableOnce {
                            navController.navigate(
                                NavigationItem.AddExpense.createAddExpenseScreen(
                                    null
                                )
                            )
                        }
                        .padding(12.dp),
                ) {
                    Text(
                        text = "Add New Expense",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(20.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30))
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(0.4f), RoundedCornerShape(30))
                .padding(horizontal = 12.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (expenseUiState.amountViewType) {
                    AmountViewType.TOTAL -> "Total Expense"
                    AmountViewType.MONTH -> "Expense of $monthText"
                    AmountViewType.YEAR -> "Expense of $yearText"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(id = R.string.rupee_symbol) + " " + if (expenseUiState.isAmountLoading) "---"
                else when (expenseUiState.amountViewType) {
                    AmountViewType.TOTAL -> allExpenses.sumOf { it.amount }.toString()
                    AmountViewType.MONTH -> expenseUiState.monthlyAmounts.values.sum().toString()
                    AmountViewType.YEAR -> expenseUiState.monthlyAmounts.values.sum().toString()
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

*/
