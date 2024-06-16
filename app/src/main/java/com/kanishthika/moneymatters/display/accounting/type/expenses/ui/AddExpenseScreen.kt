package com.kanishthika.moneymatters.display.accounting.type.expenses.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.components.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.components.MMOutlinedButton
import com.kanishthika.moneymatters.config.components.MMOutlinedTextField
import com.kanishthika.moneymatters.config.components.MMTopAppBar

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    expenseModel: ExpenseModel,
    modifier: Modifier
){
    val expenseUiState by expenseModel.addExpenseUiState.collectAsState()
    val focusManager = LocalFocusManager.current

    var addButtonEnabled by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = { MMTopAppBar(titleText = "Expense") },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.uni_screen_padding))
            ) {
                addButtonEnabled = !(
                        expenseUiState.name.isEmpty()||
                                expenseUiState.description.isEmpty()||
                                expenseUiState.amount.isEmpty())
                MMOutlinedButton(
                    enabled = addButtonEnabled,
                    modifier = modifier,
                    text = "ADD"
                ) {
                    expenseModel.addExpense()
                    focusManager.moveFocus(FocusDirection.Exit)
                }
            }
        }
    ) { it ->
        MMColumnScaffoldContentColumn(
            modifier = modifier ,
            scaffoldPaddingValues = it
        ) {
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = expenseUiState.name ,
                onValueChange = { input->
                  expenseModel.updateName(input)
                                },
                labelText = "Category Name",
                supportingText = { Text(text = "Enter a New Categories of Expense")},
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Next
                )
            )
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = expenseUiState.description ,
                onValueChange = {expenseModel.updateDescription(it)},
                labelText = "Description",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Next
                )
            )
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = expenseUiState.amount ,
                onValueChange = {expenseModel.updateAmount(it)},
                labelText = "Initial Amount",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )

        }

    }
}