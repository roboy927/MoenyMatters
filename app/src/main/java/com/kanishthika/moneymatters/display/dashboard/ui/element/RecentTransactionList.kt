package com.kanishthika.moneymatters.display.dashboard.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.clickableOnce
import com.kanishthika.moneymatters.config.utils.convertToLocalDate
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionType

@Composable
fun RecentTransactionList(
    modifier: Modifier,
    list: List<Transaction>,
    navController: NavController
) {
    Column {
        list.forEach { transaction ->
            RecentTransactionView(
                modifier = modifier,
                date = transaction.date,
                accountingName = transaction.accountingName,
                description = transaction.description,
                amount = transaction.amount.toString(),
                type = transaction.type

            )
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
            )
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = "See All",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier.clickableOnce {
                    navController.navigate(NavigationItem.TransactionList.route)
                }
            )
        }
    }
}


@Composable
fun RecentTransactionView(
    modifier: Modifier = Modifier,
    date: String,
    accountingName: String,
    description: String,
    amount: String,
    type: String
) {

    val dateFormatted = convertToLocalDate(date, "dd MMMM yyyy")
    val day = dateFormatted.dayOfMonth.toString()
    val monthWhole = capitalizeWords(dateFormatted.month.name)
    val month = if (monthWhole.length >= 3) monthWhole.substring(0, 3) else monthWhole
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.width(20.dp)
            ) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, lineHeight = 10.sp),
                )
                Text(
                    text = month,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, lineHeight = 10.sp),
                )
            }
        }
        Spacer(modifier = modifier.width(15.dp))
        Text(
            text = "$accountingName / $description",
            maxLines = 1,
            modifier = modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.8f)
        )
        Spacer(modifier = modifier.width(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (type == capitalizeWords(TransactionType.CREDIT.name) ) ImageVector.vectorResource(
                    id = R.drawable.cr_arrow
                )
                else ImageVector.vectorResource(id = R.drawable.db_arrow),
                contentDescription = if (type == capitalizeWords(TransactionType.CREDIT.name)) "credit"
                else "debit",
                modifier = modifier.size(10.dp),
                tint = if (type == capitalizeWords(TransactionType.CREDIT.name)) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = modifier.width(3.dp))
            Text(
                text = stringResource(id = R.string.rupee_symbol) + amount,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = if (type == capitalizeWords(TransactionType.CREDIT.name) ) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onPrimaryContainer.copy(0.9f)
            )
        }
    }

}