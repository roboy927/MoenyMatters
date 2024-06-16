package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.components.MMDatePickerInput
import com.kanishthika.moneymatters.config.components.MMDropDownMenu
import com.kanishthika.moneymatters.config.components.MMOutlinedTextField
import com.kanishthika.moneymatters.config.components.MMTopAppBar
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.utils.clickableOnce
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.transaction.data.TransactionType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    addTransactionModel: AddTransactionModel,
    modifier: Modifier,
    navController: NavController,
) {

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val transactionUiState by addTransactionModel.transactionUiState.collectAsState()
    val accountList = addTransactionModel.getAllAccounts.observeAsState(emptyList())
    var accountingTypeList = addTransactionModel.accountingTypeList(TransactionType.DEBIT)

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            MMTopAppBar(titleText = "Add Transaction")
        },
        bottomBar = {
            BottomAppBar(
                containerColor = if (addTransactionModel.isAnyFieldIsEmpty(transactionUiState))
                    MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f) else MaterialTheme.colorScheme.tertiaryContainer,
                modifier = modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clickableOnce(
                        enabled =
                        !addTransactionModel.isAnyFieldIsEmpty(transactionUiState)
                    ) {
                        navController.navigate(
                            NavigationItem.SelectAccounting.createRoute(
                                addTransactionModel.transactionUiState.value.accountingType.getName()
                            )
                        )
                        focusManager.clearFocus()
                        addTransactionModel.isDialogOpenChanged(true)
                    }
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Next",
                        color = if (addTransactionModel.isAnyFieldIsEmpty(transactionUiState))
                            MaterialTheme.colorScheme.onTertiaryContainer.copy(0.5f) else MaterialTheme.colorScheme.onTertiaryContainer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    ) { it ->
        Column(
            modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(dimensionResource(id = R.dimen.uni_screen_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MMDatePickerInput(
                modifier = modifier,
                date = transactionUiState.date,
                onDateSelected = { addTransactionModel.updateDate(it) }
            )

            MMDropDownMenu(
                list = accountList.value,
                name = transactionUiState.account.name,
                modifier = modifier,
                labelText = "Account",
                itemToName = { it.name + "  [" + it.balance + "]" },
                onItemSelected = {
                    addTransactionModel.updateSelectedAccount(it)
                    focusManager.moveFocus(FocusDirection.Next)
                },
            )

            MMOutlinedTextField(
                value = transactionUiState.description,
                onValueChange = { addTransactionModel.updateDescription(it) },
                labelText = "Description",
                modifier = modifier
                    .fillMaxWidth(),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )

            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = transactionUiState.amount,
                onValueChange = { addTransactionModel.updateAmount(it) },
                labelText = "Amount",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            TextSwitch(
                modifier = modifier,
                selectedTxnType = transactionUiState.transactionType
            ) { transactionType ->
                addTransactionModel.changeTxnType(transactionType)
                accountingTypeList = addTransactionModel.accountingTypeList(transactionType)
                addTransactionModel.changeAccountingType(accountingTypeList[0])
            }

            MMDropDownMenu(
                list = accountingTypeList,
                modifier = Modifier,
                name = transactionUiState.accountingType.getName(),
                labelText = "Accounting",
                itemToName = { it.getName() },
                onItemSelected = {
                    addTransactionModel.changeAccountingType(it)
                },
            )
        }
    }
}


