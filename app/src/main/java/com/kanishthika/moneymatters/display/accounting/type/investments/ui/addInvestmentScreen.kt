package com.kanishthika.moneymatters.display.accounting.type.investments.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.config.components.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.components.MMOutlinedTextField
import com.kanishthika.moneymatters.config.components.MMTopAppBar
import com.kanishthika.moneymatters.config.utils.clickableOnce

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvestmentScreen(
    investmentModel: InvestmentModel, modifier: Modifier
) {

    val investmentUiState by investmentModel.investmentUiState.collectAsState()

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = { MMTopAppBar(titleText = "Add Investment") },
        bottomBar = {
            BottomAppBar(
                containerColor = if (investmentModel.isAnyFieldIsEmpty(investmentUiState))
                    MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f) else MaterialTheme.colorScheme.tertiaryContainer,
                modifier = modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clickableOnce(
                        enabled =
                        !investmentModel.isAnyFieldIsEmpty(investmentUiState)
                    ) {
                        investmentModel.addInvestment()
                        focusManager.clearFocus()
                    }
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Next",
                        color = if (investmentModel.isAnyFieldIsEmpty(investmentUiState))
                            MaterialTheme.colorScheme.onTertiaryContainer.copy(0.5f) else MaterialTheme.colorScheme.onTertiaryContainer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    ) { it ->
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
                    capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next
                )
            )
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = investmentUiState.description,
                onValueChange = { investmentModel.updateDescription(it) },
                labelText = "Description",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next
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