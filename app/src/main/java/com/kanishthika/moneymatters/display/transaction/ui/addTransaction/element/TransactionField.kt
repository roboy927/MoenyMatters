package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMDatePickerInput
import com.kanishthika.moneymatters.config.mmComposable.MMDropDownMenu
import com.kanishthika.moneymatters.config.mmComposable.MMOutlinedTextField
import com.kanishthika.moneymatters.config.utils.convertToLocalDate
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.transaction.data.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionField(
    modifier: Modifier,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    accountList: List<Account>,
    selectedAccountName: String,
    onAccountSelected: (Account) -> Unit,
    selectedTxnType: TransactionType,
    onChangeTXnType: (TransactionType) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    focusNext: KeyboardActions,
    amount: String,
    onAmountChange: (String) -> Unit,
    accountingTypeList: List<AccountingType>,
    selectedAccountingType: AccountingType,
    onAccountingTypeChange: (AccountingType) -> Unit,
    financialItemList: List<FinancialItem>,
    selectedFinancialItem: String,
    onFinancialItemChange: (FinancialItem) -> Unit
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
            itemToName = { it.name  },
            onItemSelected = { onAccountSelected(it) },
        )

        TextSwitch(
            modifier = modifier.focusable(),
            selectedTxnType = selectedTxnType
        ) { transactionType -> onChangeTXnType(transactionType) }


        MMOutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = amount,
            onValueChange = { onAmountChange(it) },
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = focusNext
        )

        MMOutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            labelText = "Description",
            modifier = modifier.fillMaxWidth(),
            keyboardActions = focusNext
        )

        MMDropDownMenu(
            list = accountingTypeList,
            modifier = Modifier,
            name = selectedAccountingType.getName(),
            labelText = "Accounting",
            itemToName = { it.getName() },
            onItemSelected = { onAccountingTypeChange(it)},
        )

        MMDropDownMenu(
            list = financialItemList,
            name = selectedFinancialItem,
            modifier = modifier,
            itemToName = { it.name },
            onItemSelected = { onFinancialItemChange(it) },
            labelText = "Accounting Types"
        )

    }
}