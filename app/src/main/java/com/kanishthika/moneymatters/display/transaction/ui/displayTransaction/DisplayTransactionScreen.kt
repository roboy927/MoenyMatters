package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction

import TransactionListScreen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.components.MMTopAppBar
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.filterBottomSheet.BottomSheetContent
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTransactionScreen(
    modifier: Modifier,
    displayTransactionModel: DisplayTransactionModel,
    navController: NavController
) {

    val isLoading by displayTransactionModel.isLoading.collectAsState()
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

    //---------------------------------------------Scrolling

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

//----------------------------------------------------------------------------------
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
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                            displayTransactionModel.updateFilterToUiState()
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
                    if (!isFilterApplied) {
                        displayTransactionModel.clearAllCheckBoxStates()
                    }
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                },
                resetFilter = { displayTransactionModel.clearAllCheckBoxStates() }
            )
        },
        topBar = {
            MMTopAppBar(
                titleText = "Transactions",
                scrollBehavior = scrollBehavior,
            )
        },
        sheetDragHandle = {},
        sheetShadowElevation = 0.dp,
        sheetTonalElevation = 0.dp,
        sheetSwipeEnabled = false,
        sheetPeekHeight = 0.dp,
        containerColor = MaterialTheme.colorScheme.background,
        scaffoldState = bottomSheetScaffoldState,
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
//-----------------------------Toolbar Column--------------------------------------------------------
            Column {
                ActionBar(
                    modifier = modifier,
                    scope = scope,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    isFilterActive = isFilterApplied,
                    sortByDate = {
                        sortByAmountState = 0
                        when (sortByDateState) {
                            0 -> {
                                displayTransactionModel.sortByDateAscending()
                                sortByDateState = 1
                            }

                            1 -> {
                                displayTransactionModel.sortByDateDescending()
                                sortByDateState = 2
                            }

                            2 -> {
                                displayTransactionModel.filterTransactions()
                                sortByDateState = 0
                            }
                        }
                    },
                    sortByAmount = {
                        sortByDateState = 0
                        when (sortByAmountState) {
                            0 -> {
                                displayTransactionModel.sortByAmountAscending()
                                sortByAmountState = 1
                            }

                            1 -> {
                                displayTransactionModel.sortByAmountDescending()
                                sortByAmountState = 2
                            }

                            2 -> {
                                displayTransactionModel.filterTransactions()
                                sortByAmountState = 0
                            }
                        }
                    },
                    sortByAmountState = sortByAmountState,
                    sortByDateState = sortByDateState,
                    isAllTransactionsActive = displayTransactionUiState.isAllTransactionSelected,
                    onClickAllTransactions = {
                        if (!displayTransactionUiState.isAllTransactionSelected) {
                            displayTransactionModel.fetchAllTransactions()
                            displayTransactionModel.isAllTransactionStateChanged()
                        } else {
                            displayTransactionModel.filterTransactions()
                            displayTransactionModel.isAllTransactionStateChanged()
                        }
                    },
                    onSearch = {
                        navController.navigate(NavigationItem.SearchTransactionScreen.searchTransactionScreen(null))
                    }
                )
            }
//--------------------------Content Column------------------------------------------------
            if (isLoading) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                TransactionListScreen(
                    modifier = modifier,
                    displayTransactions = displayTransactions,
                    emptyDataText = "No Data Found"
                )
            }
        }

    }
}
