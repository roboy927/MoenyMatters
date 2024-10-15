package com.kanishthika.moneymatters.display.label.ui

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.config.mmComposable.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.mmComposable.MMDropDownMenu
import com.kanishthika.moneymatters.config.mmComposable.MMOutlinedTextField
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.config.utils.clickableOnce
import com.kanishthika.moneymatters.display.label.data.Label

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLabelScreen (
    modifier: Modifier = Modifier,
    labelViewModel: AddLabelModel = hiltViewModel(),
    label: Label?,
    addNewLabelTypeN: () -> Unit
) {

    val uiState by labelViewModel.uiState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = { MMTopAppBar(titleText = "Add Label") },
        bottomBar = {
            BottomAppBar(
                containerColor = if (labelViewModel.isAnyFieldIsEmpty(uiState))
                    MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f) else MaterialTheme.colorScheme.tertiaryContainer,
                modifier = modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clickableOnce(
                        enabled =
                        !labelViewModel.isAnyFieldIsEmpty(uiState)
                    ) {
                        labelViewModel.addLabel()
                        focusManager.clearFocus()
                    }
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add Label",
                        color = if (labelViewModel.isAnyFieldIsEmpty(uiState))
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
                value = uiState.labelName,
                onValueChange = { name -> labelViewModel.updateName(name) },
                labelText = "Name",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )

            MMDropDownMenu(
                list = uiState.labelTypeList,
                name = uiState.selectedLabelType.labelType,
                modifier = modifier ,
                itemToName = { item -> item.labelType} ,
                onItemSelected = {
                    if (it.labelType == "Add New"){
                        addNewLabelTypeN()
                    } else {
                        labelViewModel.updateType(it)
                    }
                },
                labelText = "Type"
            )

            MMOutlinedTextField(
                value = uiState.additional,
                onValueChange = { additional -> labelViewModel.updateAdditional(additional) },
                labelText = "Additional Details",
                supportingText = { Text(text = "Type Details to remember about Object or Event, Like Purchasing Date Etc.")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )

            MMOutlinedTextField(
                value = uiState.initialAmount,
                onValueChange = { amount-> labelViewModel.updateAmount(amount) },
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