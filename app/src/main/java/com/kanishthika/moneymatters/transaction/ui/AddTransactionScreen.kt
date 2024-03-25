package com.kanishthika.moneymatters.transaction.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.components.MMDatePickerInput
import com.kanishthika.moneymatters.components.MMDropDownMenu
import com.kanishthika.moneymatters.components.MMOutlinedButton
import com.kanishthika.moneymatters.components.MMOutlinedTextField
import com.kanishthika.moneymatters.components.MMTopAppBar
import com.kanishthika.moneymatters.navigation.Screen
import com.kanishthika.moneymatters.transaction.TransactionType


@Composable
fun AddTransactionScreen(
    transactionModel: TransactionModel,
    modifier: Modifier,
    navController: NavController
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val transactionUiState by transactionModel.uiState.collectAsState()

    val accountList = transactionModel.getAllAccounts.observeAsState().value.orEmpty()
    var accountingTypeList = transactionModel.accountingTypeList(TransactionType.DEBIT)

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            MMTopAppBar(titleText = "Add Transaction")
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.uni_screen_padding),
                    10.dp
                )
            ) {
                MMOutlinedButton(modifier = modifier, text = "Add Transaction") {
                    navController.navigate(Screen.SELECTACCOUNTING.name)
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
                onDateSelected = { transactionModel.updateDate(it) }
            )

            MMDropDownMenu(
                list = accountList,
                name = transactionUiState.account,
                modifier = modifier,
                labelText = "Account",
                itemToName = { it.name + "  [" + it.balance + "]" },
                onItemSelected = {
                    transactionModel.updateSelectedAccount(it.name)
                    focusManager.moveFocus(FocusDirection.Next)
                },
            )

            MMOutlinedTextField(
                value = transactionUiState.description,
                onValueChange = { transactionModel.updateDescription(it) },
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
                onValueChange = { transactionModel.updateAmount(it) },
                labelText = "Amount",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            TextSwitch(
                modifier = modifier,
                selectedTxnType = transactionUiState.transactionType
            ) { transactionType ->
                transactionModel.changeTxnType(transactionType)
                accountingTypeList = transactionModel.accountingTypeList(transactionType)
                transactionModel.changeAccounting(accountingTypeList[0])
            }

            MMDropDownMenu(
                list = accountingTypeList,
                modifier = Modifier,
                name = transactionUiState.accountingType,
                labelText = "Accounting",
                itemToName = { it },
                onItemSelected = {
                    transactionModel.changeAccounting(it)
                },
            )

        }

    }

}


