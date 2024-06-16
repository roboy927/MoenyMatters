package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.Lender
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.utils.capitalizeWords

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SelectAccountingDialog(
    addTransactionModel: AddTransactionModel,
    accountingType: String,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val expenseList by addTransactionModel.getAllExpenses.observeAsState(emptyList())
    val investmentList by addTransactionModel.getAllInvestments.observeAsState(emptyList())
    val borrowerList by addTransactionModel.getAllBorrowers.observeAsState(emptyList())
    val lenderList by addTransactionModel.getAllLenders.observeAsState(emptyList())


    val accountingNameList = when (addTransactionModel.transactionUiState.value.accountingType) {
        AccountingType.EXPENSE -> expenseList
        AccountingType.INVESTMENT -> investmentList
        AccountingType.BORROWERS -> borrowerList
        AccountingType.LENDERS -> lenderList
        else -> emptyList()
    }

    Dialog(onDismissRequest = { addTransactionModel.isDialogOpenChanged(true) }) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Column {
                SelectAccountingHeader(modifier, accountingType)
                Spacer(modifier.height(8.dp))
                ItemList(modifier, accountingNameList, addTransactionModel, navHostController)
                BackButton(modifier, navHostController, addTransactionModel)
            }
        }
    }
}

@Composable
fun SelectAccountingHeader(modifier: Modifier, accountingType: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Select type of ${capitalizeWords(accountingType)}",
            modifier = modifier.padding(8.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ItemList(
    modifier: Modifier,
    accountingNameList: List<Any>,
    addTransactionModel: AddTransactionModel,
    navHostController: NavHostController
) {
    LazyColumn(modifier = modifier.padding(horizontal = 4.dp, vertical = 0.dp)) {
        items(accountingNameList) { item ->
            val name = when (item) {
                is Expense -> item.name
                is Investment -> item.name
                is Borrower -> item.borrowerName
                is Lender -> item.lenderName
                else -> ""
            }
            ItemBox(modifier, name) {
                addTransactionModel.changeAccountingName(name)
                navHostController.popBackStack()
                navHostController.navigate(NavigationItem.TransactionSummaryDialog.route)
                addTransactionModel.isDialogOpenChanged(false)
                when(item){
                    is Expense -> addTransactionModel.updateSelectedExpense(item)
                    is Investment -> addTransactionModel.updateSelectedInvestment(item)
                    is Borrower -> addTransactionModel.updateSelectedBorrower(item)
                }
            }
            Spacer(modifier.height(1.dp).fillMaxWidth().background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)))
        }
    }
}

@Composable
fun BackButton(
    modifier: Modifier,
    navHostController: NavHostController,
    addTransactionModel: AddTransactionModel
) {
    Box(
        modifier = modifier
            .clickable {
                navHostController.popBackStack()
                addTransactionModel.isDialogOpenChanged(false)
                addTransactionModel.changeAccountingName("")
            }
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Back",
            modifier = modifier.padding(8.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ItemBox(
    modifier: Modifier,
    accountingName: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            modifier = modifier.padding(10.dp),
            text = capitalizeWords(accountingName),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}





