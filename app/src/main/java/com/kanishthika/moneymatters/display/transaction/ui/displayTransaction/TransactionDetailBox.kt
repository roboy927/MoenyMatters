package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.display.transaction.data.TransactionType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionDetail(
    modifier: Modifier,
    accountingName: String,
    amount: String,
    description: String,
    date: String,
    account: String,
    transactionType: TransactionType,
    icon: ImageVector,
    iconBackground: Color,
    hasReminder: Boolean,
    hasLabel: Boolean,
    navigateToEdit: () -> Unit,
    deleteTxn: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .combinedClickable(
                    onClick = { navigateToEdit() },
                    onLongClick = {deleteTxn()}
                )
                .padding(8.dp, 10.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20))
                        .background(iconBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "to be changed",
                        modifier = modifier
                            .padding(6.dp)
                            .size(20.dp),
                    )
                }
                Spacer(modifier = modifier.width(10.dp))
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = modifier.weight(1f)
                    ) {
                        Text(
                            text = accountingName,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            maxLines = 1,
                            fontWeight = FontWeight.Light
                        )
                        Spacer(modifier = modifier.height(4.dp))
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.7f),
                            fontWeight = FontWeight.Light
                        )
                    }
                    Spacer(modifier = modifier.width(6.dp))
                    Column (
                        horizontalAlignment = Alignment.End
                    ){
                        Text(
                            text = stringResource(id = R.string.rupee_symbol) + " $amount",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            fontWeight = FontWeight.Light
                        )
                        Spacer(modifier = modifier.height(4.dp))
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            if (hasReminder){
                                Icon(
                                    modifier = modifier.size(17.dp),
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.5f)
                                )
                            }
                            if (hasLabel){
                                Spacer(modifier = modifier.width(4.dp))
                                Icon(
                                    modifier = modifier.size(17.dp),
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.5f)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = modifier.height(6.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.7f),
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = when (transactionType) {
                        TransactionType.CREDIT -> "Credited to $account"
                        TransactionType.DEBIT -> "Debited from $account"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}
