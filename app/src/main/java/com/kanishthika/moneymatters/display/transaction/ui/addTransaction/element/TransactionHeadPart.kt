package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.components.MMDatePickerInput
import com.kanishthika.moneymatters.config.components.MMDropDownMenu
import com.kanishthika.moneymatters.config.components.MMOutlinedTextField
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.convertToLocalDate
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.transaction.data.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHeadPart(
    modifier: Modifier,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    accountList: List<Account>,
    selectedAccountName: String,
    onAccountSelected: (Account) -> Unit,
    selectedTxnType: TransactionType,
    onChangeTXnType: (TransactionType) -> Unit,
    splitOptions: SplitOptions,
    description: String,
    onDescriptionChange: (String) -> Unit,
    focusNext: KeyboardActions,
    amount: String,
    onAmountChange: (String) -> Unit,
    onSplitOptionChange: (SplitOptions) -> Unit,
    divideOptions: DivideOptions,
    onDivideOptionChange: (DivideOptions) -> Unit
) {
    Column(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing))
    ) {
        MMDatePickerInput(
            modifier = modifier.focusable(),
            date = selectedDate,
            onDateSelected = {
                onDateSelected(it)
            },
            initialDate = convertToLocalDate(selectedDate, "dd MMMM yyyy")
        )

        MMDropDownMenu(
            list = accountList,
            name = selectedAccountName,
            modifier = modifier,
            labelText = "Account",
            itemToName = { it.name + "  [" + it.balance + "]" },
            onItemSelected = { onAccountSelected(it) },
        )

        TextSwitch(
            modifier = modifier.focusable(),
            selectedTxnType = selectedTxnType
        ) { transactionType -> onChangeTXnType(transactionType) }

        if (splitOptions == SplitOptions.None) {
            MMOutlinedTextField(
                value = description,
                onValueChange = { onDescriptionChange(it) },
                labelText = "Description",
                modifier = modifier.fillMaxWidth(),
                keyboardActions = focusNext
            )
        }

        MMOutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = amount,
            onValueChange = { onAmountChange(it) },
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = focusNext
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            MMDropDownMenu(
                list = SplitOptions.values().toList(),
                name = capitalizeWords(splitOptions.name),
                modifier = modifier.fillMaxWidth(0.5f),
                itemToName = { capitalizeWords(it.name) },
                onItemSelected = { onSplitOptionChange(it) },
                labelText = "Split Transaction"
            )
            Spacer(modifier = modifier.width(12.dp))
            MMDropDownMenu(
                enabled = (splitOptions != SplitOptions.None),
                list = DivideOptions.values().toList(),
                name = capitalizeWords(divideOptions.name),
                modifier = modifier,
                itemToName = { capitalizeWords(it.name) },
                onItemSelected = { onDivideOptionChange(it) },
                labelText = "Divide"
            )
        }
    }
}