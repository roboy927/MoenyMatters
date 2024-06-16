package com.kanishthika.moneymatters.display.account.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardActions
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
fun AddAccountScreen (
    accountViewModel: AccountViewModel,
    modifier: Modifier = Modifier

) {

    val accountUiState by accountViewModel.uiState.collectAsState()

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = { MMTopAppBar(titleText = "Add Account") },
        bottomBar = {
            BottomAppBar(
                containerColor = if (accountViewModel.isAnyFieldIsEmpty(accountUiState))
                    MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f) else MaterialTheme.colorScheme.tertiaryContainer,
                modifier = modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clickableOnce(
                        enabled =
                        !accountViewModel.isAnyFieldIsEmpty(accountUiState)
                    ) {
                        accountViewModel.insertAccount()
                        focusManager.clearFocus()
                    }
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add Account",
                        color = if (accountViewModel.isAnyFieldIsEmpty(accountUiState))
                            MaterialTheme.colorScheme.onTertiaryContainer.copy(0.5f) else MaterialTheme.colorScheme.onTertiaryContainer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }) { paddingValue ->
        MMColumnScaffoldContentColumn(
            modifier = Modifier ,
            scaffoldPaddingValues = paddingValue
        ) {
            MMOutlinedTextField(
                value = accountUiState.name,
                onValueChange = { name -> accountViewModel.updateName(name) },
                labelText = "Name",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Next
                )
            )

            MMOutlinedTextField(
                value = accountUiState.amount,
                onValueChange = { amount-> accountViewModel.updateAmount(amount) },
                labelText = "Balance" ,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus(true)})

            )
        }
    }
}
