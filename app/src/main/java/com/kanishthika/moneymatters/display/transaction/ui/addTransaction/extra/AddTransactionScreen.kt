package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.extra

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMBottomAppBarButton
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.OptionScreen
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.SplitOptions
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.getSplitPageCount
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddTransactionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    addReminderDialog: () -> Unit
) {

    val addTransactionModel: AddTransactionModel = hiltViewModel()

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    val transactionUiState by addTransactionModel.transactionUiState.collectAsState()
    val transactionBodyUiState by addTransactionModel.transactionBodyUiState.collectAsState()

    val nextButtonEnabled by remember {
        derivedStateOf {
            (!addTransactionModel.isAnyFieldIsEmpty(
                transactionUiState, transactionBodyUiState
            ))
        }
    }

    val pagerState = rememberPagerState {
        getSplitPageCount(transactionUiState.splitOptions)
    }

    val currentPageNumber by remember {
        derivedStateOf {
            if (transactionBodyUiState.size > pagerState.currentPage) {
                pagerState.currentPage
            } else {
                0
            }
        }
    }

    LaunchedEffect(Unit) {
        addTransactionModel.loadData()
        Log.d("TAG", "AddTransactionScreen: Load Data")
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            // Perform an action when the page changes
            focusManager.clearFocus()
            Log.d("TAG", "AddTransactionScreen: Lunch")
        }
    }

    BackHandler {
        coroutineScope.launch {
            navController.navigateUp()
        }
    }

    Scaffold(modifier = Modifier.imePadding(), topBar = {
        MMTopAppBar(titleText = "Add Transaction")
    }, bottomBar = {
        MMBottomAppBarButton(
            bottomBarText = "Next", enabled = nextButtonEnabled, modifier = modifier
        ) {
            navController.navigate(NavigationItem.TransactionSummaryDialog.route)
            focusManager.clearFocus()
        }
    }) { paddingValue ->

        if (transactionUiState.loadingScreen) {
            Box(
                modifier = modifier
                    .padding(paddingValue)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            val financialItemList by transactionBodyUiState[currentPageNumber].financialItemList.collectAsState(
                initial = emptyList()
            )

            Column(
                modifier = modifier
                    .padding(paddingValue)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier
                        .background(MaterialTheme.colorScheme.background)
                        .animateContentSize()
                        .padding(dimensionResource(id = R.dimen.uni_screen_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {
                       /* TransactionHeadPart(modifier = modifier.animateItemPlacement(),
                            selectedDate = transactionUiState.date,
                            onDateSelected = {
                                addTransactionModel.updateDate(it)
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                            accountList = transactionUiState.accountList,
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
                            onDescriptionChange = addTransactionModel::updateDescription,
                            focusNext = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
                            amount = transactionUiState.amount,
                            onAmountChange = { addTransactionModel.updateAmount(it) },
                            onSplitOptionChange = {
                                coroutineScope.launch {
                                    addTransactionModel.changeSplitOption(it)
                                }
                            },
                            divideOptions = transactionUiState.divideOptions,
                            onDivideOptionChange = { addTransactionModel.changeDivideOption(it) })*/
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
                                    splitDescription = transactionBodyUiState[currentPageNumber].splitDescription,
                                    onSplitDescriptionChange = {
                                        addTransactionModel.updateSplitDescription(
                                            it,
                                            currentPageNumber
                                        )
                                    },
                                    accountingTypeList = transactionUiState.accountingTypeList,
                                    selectedAccountingType = transactionBodyUiState[currentPageNumber].accountingType,
                                    onAccountingTypeChange = {
                                        addTransactionModel.changeAccountingType(
                                            it,
                                            currentPageNumber
                                        )
                                        focusManager.moveFocus(FocusDirection.Next)
                                    },
                                    financialItemList = financialItemList,
                                    selectedFinancialItem = transactionBodyUiState[currentPageNumber].accountingName,
                                    onFinancialItemChange = {
                                        addTransactionModel.changeAccountingName(
                                            it.name,
                                            currentPageNumber
                                        )
                                        focusManager.moveFocus(FocusDirection.Next)
                                    },
                                    inputNumberOnly = KeyboardType.Decimal,
                                    moveNext = KeyboardActions {
                                        focusManager.moveFocus(
                                            FocusDirection.Next
                                        )
                                    })
                            }
                        }
                    }
                    item {
                        OptionScreen(
                            modifier = modifier,
                            optionState = transactionUiState.options,
                            optionStateChange = {
                                    addTransactionModel.changeOptionState()
                            },
                            splitEnable = {
                                coroutineScope.launch {
                                    addTransactionModel.changeSplitOption(SplitOptions.TWO)
                                }
                            },
                            splitOptions = transactionUiState.splitOptions,
                            addReminderDialog = addReminderDialog,
                            reminderAdded = transactionUiState.hasReminder,
                            selectLabelDialog = {  },
                            selectedLabel = Label(0,"","",0.0,"")
                        )
                    }
                }
            }


        }
    }
}





