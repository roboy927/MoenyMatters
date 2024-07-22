package com.kanishthika.moneymatters.display.dashboard.ui.element


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.account.ui.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsDisplayCards(
    modifier: Modifier,
    accountViewModel: AccountViewModel,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    addAccountScreen: () -> Unit,
) {

    val accounts by accountViewModel.getAllAccounts.collectAsState(emptyList())

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(accounts)
        { account ->
            AccountCard(
                scope = scope,
                snackBarHostState = snackBarHostState,
                account = account,
                deleteAccount = { accountViewModel.deleteAccount(account) },
                modifier = modifier,
                icon = when (account.type) {
                    "Bank" -> ImageVector.vectorResource(id = R.drawable.bankaccount)
                    "Wallet" -> ImageVector.vectorResource(id = R.drawable.wallet)
                    "Credit Card" -> ImageVector.vectorResource(id = R.drawable.crcard)
                    "Debit Card" -> ImageVector.vectorResource(id = R.drawable.crcard)
                    else -> ImageVector.vectorResource(id = R.drawable.rupee)
                }
            )
        }
        item {
            Card(
                modifier = Modifier
                    .height(135.dp)
                    .combinedClickable(
                        onClick = addAccountScreen
                    ),
                colors = CardDefaults.elevatedCardColors(
                    MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add Account",
                        modifier = Modifier
                            .size(30.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Add Account",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountCard(
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    account: Account,
    deleteAccount: (Account) -> Unit,
    modifier: Modifier,
    icon: ImageVector

) {
    Card(
        modifier = modifier
            .height(135.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    scope.launch {
                        val snackBarResult = snackBarHostState.showSnackbar(
                            message = "Delete ${account.name} ?",
                            actionLabel = "YES",
                            withDismissAction = true,
                            duration = SnackbarDuration.Long
                        )
                        when (snackBarResult) {
                            SnackbarResult.Dismissed -> {}
                            SnackbarResult.ActionPerformed ->
                                deleteAccount(account)

                        }
                    }
                }
            ),
        colors = CardDefaults.elevatedCardColors(
            MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = "bank Icon",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = modifier.size(30.dp)
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = capitalizeWords(account.name),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = modifier.height(2.dp))
            Text(
                text = "Balance: " + stringResource(id = R.string.rupee_symbol) + " " + account.balance,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}