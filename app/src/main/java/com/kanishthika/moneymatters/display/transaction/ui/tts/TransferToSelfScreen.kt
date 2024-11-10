package com.kanishthika.moneymatters.display.transaction.ui.tts

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.config.mmComposable.MMBottomAppBarButton
import com.kanishthika.moneymatters.config.mmComposable.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.mmComposable.MMDatePickerInput
import com.kanishthika.moneymatters.config.mmComposable.MMDropDownMenu
import com.kanishthika.moneymatters.config.mmComposable.MMOutlinedTextField
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.config.utils.convertToLocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferToSelfScreen(
    modifier: Modifier = Modifier,
    viewModel: TtsViewModel,
    next: () -> Unit,
){
    val uiState by viewModel.ttsState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    val nextButtonEnabled by remember { derivedStateOf { (!viewModel.isStateEmpty(uiState)) } }

    Scaffold(
        topBar = { MMTopAppBar(titleText = "Transfer To Self")},
        bottomBar = {
            MMBottomAppBarButton(
                bottomBarText = "Next",
                enabled = nextButtonEnabled,
                modifier = modifier
            ) {
                viewModel.onEvent(TtsEvent.AddTransaction)
                next()
                focusManager.clearFocus()
            }
        }
    ) { paddingValues ->
        MMColumnScaffoldContentColumn(modifier = modifier, scaffoldPaddingValues = paddingValues ) {
            MMDatePickerInput(
                modifier = modifier.focusable(),
                date = uiState.date,
                onDateSelected = {
                    viewModel.onEvent(TtsEvent.SetDate(it))
                },
                initialDate = convertToLocalDate(uiState.date, "dd MMMM yyyy")
            )
            MMDropDownMenu(
                list = uiState.accountList,
                name = uiState.dbAccountName.name,
                modifier = modifier,
                labelText = "Debit Account",
                itemToName = { it.name  },
                onItemSelected = { viewModel.onEvent(TtsEvent.SetDbAccount(it)) },
            )
            MMDropDownMenu(
                list = uiState.accountList,
                name = uiState.crAccountName.name,
                modifier = modifier,
                labelText = "Account",
                itemToName = { it.name  },
                onItemSelected = { viewModel.onEvent(TtsEvent.SetCrAccount(it)) },
            )
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = uiState.amount,
                onValueChange = { viewModel.onEvent(TtsEvent.SetAmount(it)) },
                labelText = "Amount",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
            )
            MMOutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.onEvent(TtsEvent.SetDescription(it)) },
                labelText = "Description",
                modifier = modifier.fillMaxWidth(),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
            )
        }
    }
}