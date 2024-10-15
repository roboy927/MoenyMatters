package com.kanishthika.moneymatters.display.accounting.type.investments.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.AddOrUpdateItemScreen
import com.kanishthika.moneymatters.display.accounting.ui.financialGenerics.MMOutlinedTextFieldWithState

@Composable
fun AddInvestmentScreen(
    investmentModel: InvestmentModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    investment: Investment? = null,
) {

    val investmentUiState by investmentModel.uiState.collectAsState()
    var investmentEditEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (investment != null) {
            investmentModel.updateName(investment.name)
            investmentModel.updateDescription(investment.description)
            investmentModel.updateAmount(investment.amount.toString())
            investmentModel.isEditEnabled(capitalizeWords(investment.name)) {
                investmentEditEnabled = it
            }
        }
    }

    AddOrUpdateItemScreen(
        viewModel = investmentModel,
        modifier = modifier,
        navController = navController,
        screenTitle = "Add Investment",
        buttonText = if (investment == null) "Add" else "Update",
        isEnabled = investmentModel.isAnyFieldIsEmpty(investmentUiState).not(),
        onBottomBarClick = {
            if (investment != null) {
                investmentModel.updateItem(investment.copy(
                    name = investmentUiState.name,
                    description = investmentUiState.description,
                    amount = investmentUiState.amount.toDouble()
                )) {
                    navController.popBackStack()
                }
            } else {
                investmentModel.addItemToDB {
                    navController.popBackStack()
                }
            }
        }
    ) {
        MMOutlinedTextFieldWithState(
            enabled = investmentEditEnabled,
            value = investmentUiState.name,
            onValueChange = { investmentModel.updateName(it) },
            labelText = "Name",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
        MMOutlinedTextFieldWithState(
            value = investmentUiState.description,
            onValueChange = { investmentModel.updateDescription(it) },
            labelText = "Description",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )
        MMOutlinedTextFieldWithState(
            enabled = investmentEditEnabled,
            value = investmentUiState.amount,
            onValueChange = { investmentModel.updateAmount(it) },
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )
        if (!investmentEditEnabled) {
            Box(
                modifier = modifier.padding(8.dp)
            ) {
                Text(
                    text = "* You can't edit expense name and amount, Because it is used in one or more transaction",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}