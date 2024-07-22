package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.components.MMBottomAppBarButton
import com.kanishthika.moneymatters.config.components.MMTopAppBar
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.PageIndicators
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.TransactionHeadPart
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.TransactionSplitDetail
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.getSplitPageCount
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddTransactionScreen(
    addTransactionModel: AddTransactionModel,
    modifier: Modifier,
    navController: NavController,
) {

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val transactionUiState by addTransactionModel.transactionUiState.collectAsState()
    val transactionBodyUiState by addTransactionModel.transactionBodyUiState.collectAsState()

    val pagerState = rememberPagerState {
        getSplitPageCount(transactionUiState.splitOptions)
    }
    val currentPageNumber =
        if (transactionBodyUiState.size > pagerState.currentPage) pagerState.currentPage else 0
    val accountList by addTransactionModel.getAllAccounts.observeAsState(emptyList())
    val accountTypeList by addTransactionModel.accountingTypeList.collectAsState()
    val financialItemList by addTransactionModel.getAccountingNameList(transactionBodyUiState[currentPageNumber].accountingType)
        .collectAsState(initial = emptyList())

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            // Perform an action when the page changes
            focusManager.clearFocus()
        }
    }

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(modifier = Modifier.imePadding(), topBar = {
        MMTopAppBar(titleText = "Add Transaction")
    }, bottomBar = {
        MMBottomAppBarButton(
            bottomBarText = "Next", enabled = !addTransactionModel.isAnyFieldIsEmpty(
                transactionUiState, transactionBodyUiState
            ), modifier = modifier
        ) {
            navController.navigate(NavigationItem.TransactionSummaryDialog.route)
            focusManager.clearFocus()
        }
    }) { paddingValue ->
        LazyColumn(
            modifier
                .padding(paddingValue)
                .background(MaterialTheme.colorScheme.background)
                .animateContentSize()
                .padding(dimensionResource(id = R.dimen.uni_screen_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                TransactionHeadPart(modifier = modifier.animateItemPlacement(),
                    selectedDate = transactionUiState.date,
                    onDateSelected = {
                        addTransactionModel.updateDate(it)
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    accountList = accountList,
                    selectedAccountName = transactionUiState.account.name,
                    onAccountSelected = {
                        addTransactionModel.updateSelectedAccount(it)
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    selectedTxnType = transactionUiState.transactionType,
                    onChangeTXnType = { transactionType ->
                        addTransactionModel.changeTxnType(transactionType)
                    },
                    splitOptions = transactionUiState.splitOptions,
                    description = transactionUiState.description,
                    onDescriptionChange = { addTransactionModel.updateDescription(it) },
                    focusNext = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
                    amount = transactionUiState.amount,
                    onAmountChange = { addTransactionModel.updateAmount(it) },
                    onSplitOptionChange = {
                        coroutineScope.launch {
                            addTransactionModel.changeSplitOption(it)
                        }
                    },
                    divideOptions = transactionUiState.divideOptions,
                    onDivideOptionChange = { addTransactionModel.changeDivideOption(it) })
            }
            item {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .animateContentSize()
                ) {
                    if (pagerState.pageCount > 1) {
                        PageIndicators(pagerState = pagerState, modifier)
                        Spacer(modifier = modifier.height(20.dp))
                    }
                    HorizontalPager(state = pagerState) {
                        TransactionSplitDetail(modifier = modifier,
                            splitOptions = transactionUiState.splitOptions,
                            splitAmount = transactionBodyUiState[currentPageNumber].splitAmount,
                            onSplitAmountChange = {
                                addTransactionModel.updateSplitAmount(it, currentPageNumber)
                            },
                            splitDescription = transactionBodyUiState[currentPageNumber].description,
                            onSplitDescriptionChange = {
                                addTransactionModel.updateSplitDescription(it, currentPageNumber)
                            },
                            accountingTypeList = accountTypeList,
                            selectedAccountingType = transactionBodyUiState[currentPageNumber].accountingType,
                            onAccountingTypeChange = {
                                addTransactionModel.changeAccountingType(it, currentPageNumber)
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                            financialItemList = financialItemList,
                            selectedFinancialItem = transactionBodyUiState[currentPageNumber].accountingName,
                            onFinancialItemChange = {
                                addTransactionModel.changeAccountingName(it.name, currentPageNumber)
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                            inputNumberOnly = KeyboardType.Decimal,
                            moveNext = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) })
                    }
                }
            }
        }
    }
}


