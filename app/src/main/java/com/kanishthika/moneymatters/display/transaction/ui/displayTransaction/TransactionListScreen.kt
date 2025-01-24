package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.AccountingType
import com.kanishthika.moneymatters.display.accounting.data.toAccountingType
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import com.kanishthika.moneymatters.display.transaction.data.stringToTransactionType

@Composable
fun TransactionListScreen(
    modifier: Modifier,
    displayTransactions: List<Transaction>,
    emptyDataText: String,
    navigateToEdit: (Transaction) -> Unit,
    deleteTxn: (Transaction) -> Unit
) {
    if (displayTransactions.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = emptyDataText,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.padding(8.dp)
        ) {
            items(displayTransactions) { transaction ->
                TransactionDetail(
                    modifier = modifier,
                    accountingName = transaction.accountingName,
                    amount = transaction.amount.toString(),
                    description = transaction.description,
                    date = transaction.date,
                    account = transaction.account,
                    transactionType = stringToTransactionType(capitalizeWords(transaction.type)),
                    icon = when (toAccountingType(transaction.accountingType)){
                        AccountingType.BORROWER -> ImageVector.vectorResource(id = R.drawable.borrower)
                        AccountingType.EXPENSE -> ImageVector.vectorResource(id = R.drawable.expense)
                        AccountingType.INCOME -> ImageVector.vectorResource(id = R.drawable.income)
                        AccountingType.INVESTMENT -> ImageVector.vectorResource(id = R.drawable.investment)
                        AccountingType.LENDER -> ImageVector.vectorResource(id = R.drawable.lender)
                        AccountingType.LOANEMI -> ImageVector.vectorResource(id = R.drawable.loan)
                        AccountingType.RETURNFROMBORROWER -> ImageVector.vectorResource(id = R.drawable.lender)
                        AccountingType.RETURNTOLENDER -> ImageVector.vectorResource(id = R.drawable.borrower)
                        AccountingType.INSURANCE -> ImageVector.vectorResource(id = R.drawable.insurance)
                        AccountingType.LOAN -> ImageVector.vectorResource(id = R.drawable.loan)
                        AccountingType.OTHER -> Icons.Default.Info
                        AccountingType.TTS -> Icons.Default.Refresh
                    } ,
                    iconBackground = when (stringToTransactionType(transaction.type) ) {
                        TransactionType.CREDIT -> MaterialTheme.colorScheme.primary.copy(0.8f)
                        TransactionType.DEBIT -> MaterialTheme.colorScheme.secondary.copy(0.8f)
                    },
                    navigateToEdit = {navigateToEdit(transaction)},
                    hasReminder = transaction.reminderId != null,
                    hasLabel = transaction.label != null,
                    deleteTxn = {deleteTxn(transaction)}
                )
                if (displayTransactions.lastIndex != displayTransactions.indexOf(transaction)) {
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                    )
                }
            }
        }
    }
}


