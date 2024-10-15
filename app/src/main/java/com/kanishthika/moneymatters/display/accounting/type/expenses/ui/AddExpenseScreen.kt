package com.kanishthika.moneymatters.display.accounting.type.expenses.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.AddOrUpdateItemScreen
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.InformationText
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.MMOutlinedTextFieldWithState

@Composable
fun AddExpenseScreen(
    expenseModel: ExpenseModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    expense: Expense? = null,
) {

    val expenseUiState by expenseModel.uiState.collectAsState()
    var expenseEditEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (expense != null) {
            expenseModel.updateName(expense.name)
            expenseModel.updateDescription(expense.description)
            expenseModel.updateAmount(expense.amount.toString())
            expenseModel.isEditEnabled(capitalizeWords(expense.name)) {
                expenseEditEnabled = it
            }
        }
    }

    AddOrUpdateItemScreen(
        viewModel = expenseModel,
        modifier = modifier,
        navController = navController,
        screenTitle = "Add Expense",
        buttonText = if (expense == null) "Add" else "Update",
        isEnabled = expenseModel.isAnyFieldIsEmpty(expenseUiState).not(),
        onBottomBarClick = {
            if (expense != null) {
                expenseModel.updateItem(expense.copy(
                    name = expenseUiState.name,
                    description = expenseUiState.description,
                    amount = expenseUiState.amount.toDouble()
                )) {
                    navController.popBackStack()
                }
            } else {
                expenseModel.addItemToDB {
                    navController.popBackStack()
                }
            }
        }
    ) {
        MMOutlinedTextFieldWithState(
            enabled = expenseEditEnabled,
            value = expenseUiState.name,
            onValueChange = { expenseModel.updateName(it) },
            labelText = "Name",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
        MMOutlinedTextFieldWithState(
            value = expenseUiState.description,
            onValueChange = { expenseModel.updateDescription(it) },
            labelText = "Description",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
        MMOutlinedTextFieldWithState(
            enabled = expenseEditEnabled,
            value = expenseUiState.amount,
            onValueChange = { expenseModel.updateAmount(it) },
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )
        if (!expenseEditEnabled) {
            InformationText(modifier = modifier)
        }
    }
}