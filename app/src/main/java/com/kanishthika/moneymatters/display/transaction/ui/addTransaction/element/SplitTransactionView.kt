package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.components.MMDropDownMenu
import com.kanishthika.moneymatters.config.components.MMOutlinedTextField
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.accounting.data.getName


@Composable
fun PageIndicators(pagerState: PagerState, modifier: Modifier) {
    val currentPage = pagerState.currentPage
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(pagerState.pageCount) { pageIndex ->
            val color = if (pageIndex == currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(0.5f)
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(color, CircleShape)
            )
            Spacer(modifier = modifier.width(8.dp))
        }
    }
}

@Composable
fun TransactionSplitDetail(
    modifier: Modifier,
    splitOptions: SplitOptions,
    splitAmount: String,
    onSplitAmountChange: (String) -> Unit,
    splitDescription: String,
    onSplitDescriptionChange: (String) -> Unit,
    accountingTypeList: List<AccountingType>,
    selectedAccountingType: AccountingType,
    onAccountingTypeChange: (AccountingType) -> Unit,
    financialItemList: List<FinancialItem>,
    selectedFinancialItem: String,
    onFinancialItemChange: (FinancialItem) -> Unit,
    inputNumberOnly: KeyboardType,
    moveNext: KeyboardActions,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.form_spacing))
    ){
        if (splitOptions != SplitOptions.None){
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = splitAmount,
                onValueChange = { onSplitAmountChange(it)},
                labelText = "Split Amount",
                keyboardOptions = KeyboardOptions(keyboardType = inputNumberOnly),
                keyboardActions = moveNext
            )
            MMOutlinedTextField(
                value = splitDescription,
                onValueChange = { onSplitDescriptionChange(it)},
                labelText = "Description",
                modifier = modifier
                    .fillMaxWidth(),
                keyboardActions = moveNext
            )
        }

        MMDropDownMenu(
            list = accountingTypeList,
            modifier = Modifier,
            name = selectedAccountingType.getName(),
            labelText = "Accounting",
            itemToName = { it.getName() },
            onItemSelected = { onAccountingTypeChange(it) },
        )

        MMDropDownMenu(
            list = financialItemList,
            name = selectedFinancialItem,
            modifier = modifier,
            itemToName = { it.name },
            onItemSelected = { onFinancialItemChange(it) },
            labelText = "Accounting Types"
        )




/// old ---------------------- old ----------------------------- old ------------------------------------
        /*if (splitOptions != SplitOptions.None){
            MMOutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = splitAmount,
                onValueChange = { onSplitAmountChange(it)},
                labelText = "Split Amount",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
            MMOutlinedTextField(
                value = transactionBodyUiState[pageNumber].description,
                onValueChange = { addTransactionModel.updateSplitDescription(it, pageNumber)},
                labelText = "Description",
                modifier = modifier
                    .fillMaxWidth(),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
        }

        MMDropDownMenu(
            list = accountingTypeList,
            modifier = Modifier,
            name = transactionBodyUiState[pageNumber].accountingType.getName(),
            labelText = "Accounting",
            itemToName = { it.getName()  },
            onItemSelected = {
                addTransactionModel.changeAccountingType(it, pageNumber)
                focusManager.moveFocus(FocusDirection.Next)
            },
        )

        MMDropDownMenu(
            list = accountingNameList,
            name = transactionBodyUiState[pageNumber].accountingName,
            modifier = modifier,
            itemToName = {
                when (it) {
                    is Expense -> it.name
                    is Investment -> it.name
                    is Borrower -> it.borrowerName
                    is Lender -> it.lenderName
                    is Income -> it.name
                    is Insurance -> it.name
                    // is Loan -> // TODO:
                    //    is Other -> // TODO:
                    else -> "None"
                }
            },
            onItemSelected = { item ->
                addTransactionModel.changeAccountingName(
                    when (item) {
                        is Expense -> item.name
                        is Investment -> item.name
                        is Borrower -> item.borrowerName
                        is Lender -> item.lenderName
                        is Income -> item.name
                        is Insurance -> item.name
                        // is Loan -> // TODO:
                        //    is Other -> // TODO:
                        else -> "None"
                    },
                    pageNumber
                )
                focusManager.moveFocus(FocusDirection.Next)
            },
            labelText = "Accounting Types"
        )
         pageNumber = if (transactionBodyUiState.size > pagerState.currentPage) {
                    pagerState.currentPage
                } else  { 0 },
        */
    }


}
