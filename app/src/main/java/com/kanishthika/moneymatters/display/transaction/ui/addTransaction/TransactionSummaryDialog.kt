package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.getName

@Composable
fun TransactionSummaryDialog(
    modifier: Modifier,
    addTransactionModel: AddTransactionModel,
    navController: NavController
) {
    val transactionUiState by addTransactionModel.transactionUiState.collectAsState()
    val list by addTransactionModel.transactionBodyUiState.collectAsState()

    Dialog(onDismissRequest = {  }) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Column {
                TransactionSummaryHeader(modifier)
                Spacer(modifier.height(4.dp))
                TransactionDetails(modifier, transactionUiState, list)
                Spacer(modifier.height(4.dp))
                ActionButtons(modifier, addTransactionModel, navController)
                Spacer(modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun TransactionSummaryHeader(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Summary",
            modifier = modifier.padding(8.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f)
        )
    }
}

@Composable
fun TransactionDetails(modifier: Modifier, transactionUiState: TransactionUiState, list: List<TransactionBodyUiState>) {
    list.forEach {
        Column(modifier = modifier.padding(6.dp)) {
            DetailRow(
                modifier,
                "",
                transactionUiState.date,
                MaterialTheme.typography.titleSmall,
                MaterialTheme.colorScheme.onPrimary.copy(0.6f)
            )
            DetailRow(
                modifier,
                "${capitalizeWords(transactionUiState.transactionType.name)}ed from ${
                    capitalizeWords(
                        transactionUiState.account.name
                    )
                }",
                "₹ ${transactionUiState.amount}",
                MaterialTheme.typography.titleMedium,
                MaterialTheme.colorScheme.onPrimary
            )
            Spacer(
                modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            )
            DetailRow(
                modifier,
                it.accountingType.getName(),
                it.accountingName,
                MaterialTheme.typography.titleSmall,
                MaterialTheme.colorScheme.onPrimary

            )
            Spacer(
                modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            )
            DetailRow(
                modifier,
                transactionUiState.description,
                "",
                MaterialTheme.typography.titleSmall,
                MaterialTheme.colorScheme.onPrimary.copy(0.6f)
            )
        }
    }

}

@Composable
fun DetailRow(
    modifier: Modifier,
    leftText: String,
    rightText: String,
    textStyle: TextStyle,
    color: Color
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = leftText, style = textStyle, color = color)
        if (rightText.isNotEmpty()) {
            Text(text = rightText, style = textStyle, color = color)
        }
    }
}

@Composable
fun ActionButtons(
    modifier: Modifier,
    addTransactionModel: AddTransactionModel,
    navController: NavController
) {
    Row(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
    ) {
        ActionButton(modifier, "Edit", 0.5f) {
            navController.popBackStack()
        }
        Spacer(modifier.width(10.dp))
        ActionButton(modifier, "Submit", widthFraction = 1f) {
            handleTransactionSubmit(addTransactionModel)
            navController.popBackStack()
        }
    }
}

@Composable
fun ActionButton(modifier: Modifier, text: String, widthFraction: Float, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth(widthFraction)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier.padding(8.dp, 6.dp),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

fun handleTransactionSubmit(addTransactionModel: AddTransactionModel) {
    val transactionUiState = addTransactionModel.transactionUiState.value
    addTransactionModel.addTransaction()

   /* when (transactionUiState.accountingType) {
        AccountingType.BORROWER -> addTransactionModel.updateBorrower(
            addTransactionModel.selectedBorrower.value.copy(
                amount = addTransactionModel.selectedBorrower.value.amount + transactionUiState.amount.toDouble()
            )
        )

        AccountingType.EXPENSE -> addTransactionModel.updateExpense(
            addTransactionModel.selectedExpense.value.copy(
                amount = addTransactionModel.selectedExpense.value.amount + transactionUiState.amount.toDouble()
            )
        )

        AccountingType.INCOME -> { *//* Handle income updates *//*
        }

        AccountingType.INVESTMENT -> addTransactionModel.updateInvestment(
            addTransactionModel.selectedInvestment.value.copy(
                amount = addTransactionModel.selectedInvestment.value.amount + transactionUiState.amount.toDouble()
            )
        )

        AccountingType.LENDER -> { *//* Handle lenders updates *//*
        }

        AccountingType.INSURANCE -> {}
        AccountingType.LOAN -> {

        }
        AccountingType.LOANEMI -> {

        }
        AccountingType.RETURNFROMBORROWER -> {

        }
        AccountingType.RETURNTOLENDER -> {

        }

        AccountingType.OTHER -> {
            TODO()
        }
    }

    when (transactionUiState.transactionType) {
        TransactionType.CREDIT -> addTransactionModel.updateAccount(
            transactionUiState.account.copy(
                balance = transactionUiState.amount.toDouble() + transactionUiState.account.balance
            )
        )

        TransactionType.DEBIT -> addTransactionModel.updateAccount(
            transactionUiState.account.copy(
                balance = transactionUiState.account.balance - transactionUiState.amount.toDouble()
            )
        )
    }*/
}
