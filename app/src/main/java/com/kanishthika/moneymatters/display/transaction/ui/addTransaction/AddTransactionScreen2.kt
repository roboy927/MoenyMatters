package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMBottomAppBarButton
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.config.utils.separateDateFromTimeStamp
import com.kanishthika.moneymatters.config.utils.separateTimeFromTimeStamp
import com.kanishthika.moneymatters.display.accounting.data.toAccountingType
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.stringToTransactionType
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.OptionScreen
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.SplitOptions
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element.TransactionField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen2(
    modifier: Modifier = Modifier,
    viewModel: AddTransactionModel2,
    next: () -> Unit,
    openReminderDialog: () -> Unit,
    selectLAbelDialogN: () -> Unit,
    transaction: Transaction? = null
) {

    val state by viewModel.addTransactionState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val nextButtonEnabled by remember { derivedStateOf { (!viewModel.isStateEmpty(state)) } }


//-----------------This part includes code for Edit Transactions----------------------------------------------------
    if (transaction != null) {
        LaunchedEffect(transaction) {
            viewModel.onEvent(AddTransactionEvent.SetEditTransaction(transaction))
            viewModel.onEvent(AddTransactionEvent.SetDate(transaction.date))
            viewModel.onEvent(
                AddTransactionEvent.SetAccount(
                    account = viewModel.getAccountByName(transaction.account)
                )
            )
            viewModel.onEvent(AddTransactionEvent.SetTxnType(stringToTransactionType(transaction.type)))
            viewModel.onEvent(AddTransactionEvent.SetDescription(transaction.description))
            viewModel.onEvent(AddTransactionEvent.SetAmount(transaction.amount.toString()))
            viewModel.onEvent(AddTransactionEvent.SetFinancialType(toAccountingType(transaction.accountingType)))
            viewModel.onEvent(
                AddTransactionEvent.SetFinancialItem(
                    financialItem = viewModel.getFinancialItemByName(
                        toAccountingType(transaction.accountingType),
                        transaction.accountingName
                    )
                )
            )
            viewModel.onEvent(AddTransactionEvent.SetLabel(transaction.label?.let {
                viewModel.getLabelByName(
                    it
                )
            } ?: Label(0, "", "", 0.0, "")))
            if (transaction.reminderId != null) {

                val mmReminder = viewModel.getReminderById(transaction.reminderId)
                viewModel.onEvent(AddTransactionEvent.UpdateHasReminder(true))
                viewModel.onEvent(AddTransactionEvent.SetReminderTitle(mmReminder.title))
                viewModel.onEvent(AddTransactionEvent.SetReminderDescription(mmReminder.description))
                viewModel.onEvent(
                    AddTransactionEvent.SetReminderDate(
                        separateDateFromTimeStamp(
                            mmReminder.timeStamp
                        )
                    )
                )
                viewModel.onEvent(
                    AddTransactionEvent.SetReminderTime(
                        separateTimeFromTimeStamp(
                            mmReminder.timeStamp
                        )
                    )
                )
            }
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = { MMTopAppBar(titleText = if (transaction == null)"Add Transaction" else "Edit Transaction") },
        bottomBar = {
            MMBottomAppBarButton(
                bottomBarText = if (transaction == null) "Next" else "Update",
                enabled = if (transaction == null) nextButtonEnabled else true,
                modifier = modifier
            ) {
                next()
                focusManager.clearFocus()
            }
        }
    ) { paddingValue ->

        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(dimensionResource(id = R.dimen.uni_screen_padding))
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing))
        ) {
            TransactionField(
                modifier = Modifier,
                selectedDate = state.date,
                onDateSelected = { viewModel.onEvent(AddTransactionEvent.SetDate(it)) },
                accountList = state.accountList,
                selectedAccountName = state.account.name,
                onAccountSelected = { viewModel.onEvent(AddTransactionEvent.SetAccount(it)) },
                selectedTxnType = state.transactionType,
                onChangeTXnType = { txnType ->
                    viewModel.onEvent(
                        AddTransactionEvent.SetTxnType(
                            txnType
                        )
                    )
                },
                description = state.description,
                onDescriptionChange = { des ->
                    viewModel.onEvent(
                        AddTransactionEvent.SetDescription(
                            des
                        )
                    )
                },
                focusNext = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) },
                amount = state.amount,
                onAmountChange = { amount -> viewModel.onEvent(AddTransactionEvent.SetAmount(amount)) },
                accountingTypeList = state.accountingTypeList,
                selectedAccountingType = state.accountingType,
                onAccountingTypeChange = { accountingType ->
                    viewModel.onEvent(AddTransactionEvent.SetFinancialType(accountingType))
                    focusManager.moveFocus(FocusDirection.Next)
                },
                financialItemList = state.financialItemList,
                selectedFinancialItem = state.financialItem,
                onFinancialItemChange = { financialItem ->
                    viewModel.onEvent(AddTransactionEvent.SetFinancialItem(financialItem))
                    focusManager.clearFocus()
                }
            )
            OptionScreen(
                modifier = modifier,
                optionState = state.options,
                optionStateChange = { viewModel.onEvent(AddTransactionEvent.ChangeOptionState) },
                splitOptions = SplitOptions.None,
                splitEnable = { /*TODO*/ },
                reminderAdded = state.hasReminder,
                addReminderDialog = { openReminderDialog() },
                selectLabelDialog = { selectLAbelDialogN() },
                selectedLabel = state.selectedLabel

            )

        }
    }


}