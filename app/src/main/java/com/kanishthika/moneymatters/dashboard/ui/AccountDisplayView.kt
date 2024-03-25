package com.kanishthika.moneymatters.dashboard.ui


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.account.data.Account
import com.kanishthika.moneymatters.account.ui.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsDisplayCards(
    accountViewModel: AccountViewModel,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    addAccountScreen: () -> Unit
) {

    val accounts by accountViewModel.getAllAccounts.observeAsState(listOf())

    LazyRow {
        items(accounts)
        { account ->
            AccountCard(
                scope = scope,
                snackBarHostState = snackBarHostState,
                account = account,
                deleteAccount = { accountViewModel.deleteAccount(account) }
            )
        }
        item {
            Card(
                modifier = Modifier
                    .height(150.dp)
                    .width(250.dp)
                    .padding(8.dp)
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
                            .size(50.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "ADD ACCOUNT",
                        color = MaterialTheme.colorScheme.secondary
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
    deleteAccount: (Account) -> Unit

) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(8.dp)
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
            modifier = Modifier
                .padding(15.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_analytics_24),
                contentDescription = "bank Icon",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = account.name,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Available Balance: ${account.balance}",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}