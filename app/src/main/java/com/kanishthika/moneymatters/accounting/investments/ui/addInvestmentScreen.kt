package com.kanishthika.moneymatters.accounting.investments.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.components.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.components.MMOutlinedButton
import com.kanishthika.moneymatters.components.MMOutlinedTextField
import com.kanishthika.moneymatters.components.MMTopAppBar

@Composable
fun AddInvestmentScreen(
    investmentModel: InvestmentModel, modifier: Modifier
) {

    val investmentUiState by investmentModel.investmentUiState.collectAsState()

    var addButtonEnabled by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = { MMTopAppBar(titleText = "Add Investment") }, bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.uni_screen_padding))
        ) {
            addButtonEnabled =
                !(investmentUiState.name.isEmpty() || investmentUiState.amount.isEmpty() || investmentUiState.description.isEmpty())
            MMOutlinedButton(
                enabled = addButtonEnabled, modifier = modifier, text = "Add Investment"
            ) {
                investmentModel.addInvestment()
            }
        }
    }) { it ->
        MMColumnScaffoldContentColumn(
            modifier = modifier, scaffoldPaddingValues = it
        ) {
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = investmentUiState.name,
                onValueChange = { input ->
                    investmentModel.updateName(input)
                },
                labelText = "Name",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters, imeAction = ImeAction.Next
                )
            )
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = investmentUiState.description,
                onValueChange = { investmentModel.updateDescription(it) },
                labelText = "Description",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters, imeAction = ImeAction.Next
                )
            )
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = investmentUiState.amount,
                onValueChange = { investmentModel.updateAmount(it) },
                labelText = "Amount",
                supportingText = { Text(text = "Total Amount that Invested") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done
                )
            )
        }
    }
}