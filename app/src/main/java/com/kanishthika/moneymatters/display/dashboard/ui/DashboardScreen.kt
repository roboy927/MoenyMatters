package com.kanishthika.moneymatters.display.dashboard.ui

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.reminder.NotificationPermissionHandler
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.dashboard.data.menuItems
import com.kanishthika.moneymatters.display.dashboard.ui.element.AccountsDisplayCards
import com.kanishthika.moneymatters.display.dashboard.ui.element.DashBoardTransactionView
import com.kanishthika.moneymatters.display.dashboard.ui.element.DashboardSummaryView
import com.kanishthika.moneymatters.display.dashboard.ui.element.Menu
import java.text.DecimalFormat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    dashBoardModel: DashBoardModel = hiltViewModel(),
    navController: NavController,
    navigateToAddTransaction: ()-> Unit,
    navigateToAddAccount: ()-> Unit,
    navigateTo: (String) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val handler = remember { Handler(Looper.getMainLooper()) }

    val recentTransactionList by dashBoardModel.lastSevenTransaction.collectAsStateWithLifecycle()
    val dashBoardUiState by dashBoardModel.dashBoardUiState.collectAsStateWithLifecycle()
    val monthList by dashBoardModel.monthYearList.collectAsStateWithLifecycle(initialValue = emptyList())
    val yearList by dashBoardModel.yearList.collectAsStateWithLifecycle(initialValue = emptyList())

    BackHandler {
        if (dashBoardUiState.backPressedOnce) {
            (context as ComponentActivity).finish()
        } else {
            dashBoardModel.changeBackPressedOnce(true)
            Toast.makeText(context, "Press again to close the app", Toast.LENGTH_SHORT).show()

            handler.postDelayed(
                { dashBoardModel.changeBackPressedOnce(false) },
                2000
            )
        }
    }

    NotificationPermissionHandler()

    LaunchedEffect(Unit) {
        dashBoardModel.loadData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Money Matters",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                colors = TopAppBarDefaults.mediumTopAppBarColors(MaterialTheme.colorScheme.background),
                actions = {
                    IconButton(onClick = { navController.navigate(NavigationItem.SignInScreen.route) }) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Account",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(onClick = { navController.navigate(NavigationItem.BackUpScreen.route) }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.height(IntrinsicSize.Min),
                onClick = {
                   navigateToAddTransaction()
                    },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.7f),
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f)
            ) {
                Row(
                    modifier = modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                    Text(
                        text = "Transaction",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        snackbarHost = {
            SnackbarHost(snackBarHostState) {
                Snackbar(
                    snackbarData = it,
                    actionColor = MaterialTheme.colorScheme.primary,
                    dismissActionContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        },
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)

        ) {
//----------------------------------------------------------------------------------------------------
            item {
                AccountsDisplayCards(
                    modifier,
                    accounts = dashBoardUiState.accountList,
                    deleteAccount = { dashBoardModel.deleteAccount(it)},
                    scope,
                    snackBarHostState = snackBarHostState,
                    addAccountScreen = navigateToAddAccount,
                    getBalance = {
                        DecimalFormat("#.##").format(dashBoardModel.getAccountBalance(capitalizeWords(it.name)))
                    }
                )
            }
//----------------------------------------------------------------------------------------------------
            item {
                Column {
                    Text(
                        text = "Menu and Tools",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier.padding(4.dp),
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    Menu(menuItems = menuItems(), navigateTo = { navigateTo(it) })
                }
            }
//---------------------------------------------------------------------------------------------------
            item {
                Column(
                    modifier = modifier.animateContentSize()
                ) {
                    Text(
                        text = "Transactions",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier.padding(4.dp),
                    )
                    DashBoardTransactionView(
                        modifier = modifier,
                        list = recentTransactionList,
                        navController = navController,
                        scope = scope
                    )

                }
            }
//-----------------------------------------------------------------------------------------------------
            item {
                Column {
                    Text(
                        text = "Summary",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier.padding(4.dp),
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    DashboardSummaryView(
                        modifier = modifier,
                        totalIncome = dashBoardUiState.incomeAmount,
                        expense = dashBoardUiState.expenseAmount,
                        investment = dashBoardUiState.investmentAmount,
                        insurance = dashBoardUiState.insuranceAmount,
                        loanEmi = dashBoardUiState.loanEmiAmount,
                        other = dashBoardUiState.otherAmount,
                        amountViewType = dashBoardUiState.amountViewType,
                        monthText = "month",
                        yearText = "year",
                        monthList = monthList,
                        yearList = yearList,
                        changeAmountViewType = { dashBoardModel.changeAmountViewType(it) }
                    )
                }
            }

        }
    }
}
