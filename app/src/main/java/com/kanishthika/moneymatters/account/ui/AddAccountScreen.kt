package com.kanishthika.moneymatters.account.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.kanishthika.moneymatters.components.MMBottomAppBar
import com.kanishthika.moneymatters.components.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.components.MMOutlinedButton
import com.kanishthika.moneymatters.components.MMOutlinedTextField
import com.kanishthika.moneymatters.components.MMTopAppBar


@Composable
fun AddAccountScreen (accountViewModel: AccountViewModel ) {

    val accountUiState by accountViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { MMTopAppBar(titleText = "Add Account") },
        bottomBar = { MMBottomAppBar {
           MMOutlinedButton(modifier = Modifier , text = "Add Account") {
               accountViewModel.insertAccount()
           }
        }}
    ) { paddingValue ->
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
                )

            )
        }
    }
}
