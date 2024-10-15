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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionType

@Composable
fun TransactionSummaryDialog(
    modifier: Modifier,
    transactionUiState: AddTransactionScreenState,
    onEdit: () -> Unit,
    onSubmit: (Transaction?) -> Unit,
) {

    Dialog(onDismissRequest = { }) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Column {
                TransactionSummaryHeader(modifier)
                Spacer(modifier.height(4.dp))
                TransactionDetails(modifier, transactionUiState)
                Spacer(modifier.height(4.dp))
                ActionButtons(
                    modifier,
                    onEdit = onEdit,
                    onSubmit = { onSubmit(transactionUiState.editTransaction) },
                    editTxn = transactionUiState.editTransaction
                )
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
fun TransactionDetails(modifier: Modifier, state: AddTransactionScreenState) {

    Column(modifier = modifier.padding(6.dp)) {
        DetailRow(
            modifier,
            "",
            state.date,
            MaterialTheme.typography.titleSmall,
            MaterialTheme.colorScheme.onPrimary.copy(0.6f)
        )
        DetailRow(
            modifier = modifier,
            leftText = "${capitalizeWords(if (state.transactionType == TransactionType.CREDIT) "Credited to " else "Debited from")} ${
                capitalizeWords(
                    state.account.name
                )
            }",
            rightText = "₹  ${state.amount}",
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
            state.accountingType.getName(),
            state.financialItem,
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
            state.description,
            "",
            MaterialTheme.typography.titleSmall,
            MaterialTheme.colorScheme.onPrimary.copy(0.6f)
        )
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
    editTxn: Transaction?,
    onEdit: () -> Unit,
    onSubmit: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
    ) {
        ActionButton(modifier, "Edit", 0.5f) {
            onEdit()
        }
        Spacer(modifier.width(10.dp))
        ActionButton(modifier, if (editTxn != null) "Update" else "Submit", widthFraction = 1f) {
            onSubmit()
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
