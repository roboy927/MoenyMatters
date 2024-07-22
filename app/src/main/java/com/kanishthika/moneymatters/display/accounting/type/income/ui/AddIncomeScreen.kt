package com.kanishthika.moneymatters.display.accounting.type.income.ui

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
import com.kanishthika.moneymatters.display.accounting.type.income.data.Income
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.AddOrUpdateItemScreen
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.InformationText
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.MMOutlinedTextFieldWithState

@Composable
fun AddIncomeScreen(
    incomeModel: IncomeModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    income: Income? = null,
) {

    val incomeUiState by incomeModel.uiState.collectAsState()
    var incomeEditEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (income != null) {
            incomeModel.updateName(income.name)
            incomeModel.updateDescription(income.description)
            incomeModel.updateAmount(income.amount.toString())
            incomeModel.isEditEnabled(capitalizeWords(income.name)) {
                incomeEditEnabled = it
            }
        }
    }

    AddOrUpdateItemScreen(
        viewModel = incomeModel,
        modifier = modifier,
        navController = navController,
        screenTitle = "Add Income",
        buttonText = if (income == null) "Add" else "Update",
        isEnabled = incomeModel.isAnyFieldIsEmpty(incomeUiState).not()
    ) {
        MMOutlinedTextFieldWithState(
            enabled = incomeEditEnabled,
            value = incomeUiState.name,
            onValueChange = { incomeModel.updateName(it) },
            labelText = "Name",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
        MMOutlinedTextFieldWithState(
            value = incomeUiState.description,
            onValueChange = { incomeModel.updateDescription(it) },
            labelText = "Description",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
        MMOutlinedTextFieldWithState(
            enabled = incomeEditEnabled,
            value = incomeUiState.amount,
            onValueChange = { incomeModel.updateAmount(it) },
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )
        if (!incomeEditEnabled) {
            InformationText(modifier = modifier)
        }
    }
}