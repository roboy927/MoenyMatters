package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.components.MMTopAppBar
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.toAccountingType
import com.kanishthika.moneymatters.display.transaction.data.stringToTransactionType
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet.BottomSheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    modifier: Modifier,
    displayTransactionModel: DisplayTransactionModel,
    navController: NavController
) {

    val displayTransactionUiState by displayTransactionModel.displayTransactionUiState.collectAsState()
    val displayTransactions by displayTransactionModel.transactions.collectAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden, skipHiddenState = false
        )
    )

    val secondLevelFilterList by displayTransactionModel.getSecondLevelFilterList(
        displayTransactionUiState.selectedFirstLevelFilter
    ).collectAsState(emptyList())

    val checkState =
        displayTransactionModel.getCheckedState(displayTransactionUiState.selectedFirstLevelFilter)

    val scope = rememberCoroutineScope()

    val isFilterApplied = !(displayTransactionUiState.filterByAccount.isEmpty() &&
            displayTransactionUiState.filterByMonth.isEmpty() &&
            displayTransactionUiState.filterByTransactionType.isEmpty() &&
            displayTransactionUiState.filterByAccountingType.isEmpty())

    val isStateAndLocalFilterSame =
        displayTransactionUiState.filterByAccount == displayTransactionModel.accountFilter &&
                displayTransactionUiState.filterByMonth == displayTransactionModel.monthFilter &&
                displayTransactionUiState.filterByTransactionType == displayTransactionModel.transactionTypeFilter &&
                displayTransactionUiState.filterByAccountingType == displayTransactionModel.accountingTypeFilter


    var sortByDateState by remember { mutableIntStateOf(0) }
    var sortByAmountState by remember { mutableIntStateOf(0) }

    BackHandler {
        if (bottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        } else {
            navController.popBackStack()
        }
    }

    BottomSheetScaffold(
        modifier = modifier,
        sheetContent = {
            BottomSheetContent(
                modifier = modifier,
                firstLevelFilter = displayTransactionModel.firstLevelFilter,
                onClickFirstFilterItem = {
                    displayTransactionModel.changeSelectedFirstLevelFilter(it)
                },
                secondFilterList = secondLevelFilterList,
                checkBoxState = checkState,
                onCheckedChange = { it, name, index ->
                    displayTransactionModel.changeCheckBoxState(
                        it, index = index, displayTransactionUiState.selectedFirstLevelFilter
                    )
                    displayTransactionModel.updateFilterList(
                        displayTransactionUiState.selectedFirstLevelFilter,
                        name,
                        it
                    )
                },
                onApplyFilter = {
                    scope.launch {
                        launch {
                            displayTransactionModel.filterTransactions()
                        }
                        launch {
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                    }
                },
                buttonText = if (!isFilterApplied) "Apply Filter" else "Update Filter",
                checkCounts = displayTransactionUiState.checkboxCountState,
                enabledButton = !isStateAndLocalFilterSame,
                onCancel = {
                    if (isFilterApplied) {
                        displayTransactionModel.clearAllCheckBoxStates()
                    }
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                },
                resetFilter = { displayTransactionModel.clearAllCheckBoxStates() }
            )
        },
        topBar = { MMTopAppBar(titleText = "Transactions") },
        sheetDragHandle = {},
        sheetShadowElevation = 0.dp,
        sheetTonalElevation = 0.dp,
        sheetSwipeEnabled = false,
        sheetPeekHeight = 0.dp,
        scaffoldState = bottomSheetScaffoldState,
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            ActionBar(
                modifier = modifier,
                scope = scope,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                isFilterActive = isFilterApplied,
                isSortByDate = sortByDateState == 1 || sortByDateState == 2,
                isSortByAmount = sortByAmountState == 1 || sortByAmountState == 2,
                sortByDate = {
                    sortByAmountState = 0
                    when (sortByDateState) {
                        0 -> {
                            displayTransactionModel.sortByDateAscending(displayTransactions)
                            sortByDateState = 1
                        }

                        1 -> {
                            displayTransactionModel.sortByDateDescending(displayTransactions)
                            sortByDateState = 2
                        }

                        2 -> {
                            displayTransactionModel.fetchAllTransactions()
                            sortByDateState = 0
                        }
                    }
                },
                sortByAmount = {
                    sortByDateState = 0
                    when (sortByAmountState) {
                        0 -> {
                            displayTransactionModel.sortByAmountAscending(displayTransactionModel.transactions)
                            sortByAmountState = 1
                        }

                        1 -> {
                            displayTransactionModel.sortByAmountDescending(displayTransactionModel.transactions)
                            sortByAmountState = 2
                        }

                        2 -> {
                            displayTransactionModel.fetchAllTransactions()
                            sortByAmountState = 0
                        }
                    }
                },
                sortByAmountIcon = displayTransactionModel.getSortingIcon(sortByAmountState),
                sortByDateIcon = displayTransactionModel.getSortingIcon(sortByDateState)
            )

            if (displayTransactions.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Data Found",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                LazyColumn(
                    modifier = modifier.padding(8.dp)
                ) {
                    items(displayTransactions) { transaction ->
                        TransactionDetail(
                            modifier = modifier,
                            accountingName = transaction.accountingName,
                            amount = transaction.amount.toString(),
                            description = transaction.description,
                            date = transaction.date,
                            account = transaction.account,
                            transactionType = stringToTransactionType(capitalizeWords(transaction.type)),
                            icon = displayTransactionModel.getIcon(
                                accountingType = toAccountingType(
                                    transaction.accountingType
                                )
                            ),
                            iconBackground = displayTransactionModel.getIconBackground(
                                transactionType = stringToTransactionType(transaction.type)
                            )
                        )
                        if (displayTransactions.lastIndex != displayTransactions.indexOf(transaction)) {
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                            )
                        }
                    }
                }
            }
        }
    }
}

