package com.kanishthika.moneymatters.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kanishthika.moneymatters.account.ui.AccountViewModel
import com.kanishthika.moneymatters.account.ui.AddAccountScreen
import com.kanishthika.moneymatters.accounting.AccountingViewModel
import com.kanishthika.moneymatters.accounting.expense.ui.AddExpenseScreen
import com.kanishthika.moneymatters.accounting.expense.ui.ExpenseModel
import com.kanishthika.moneymatters.accounting.investments.ui.AddInvestmentScreen
import com.kanishthika.moneymatters.accounting.investments.ui.InvestmentModel
import com.kanishthika.moneymatters.accounting.ui.AccountingScreen
import com.kanishthika.moneymatters.accounting.ui.SelectAccountingScreen
import com.kanishthika.moneymatters.dashboard.ui.HomeScreen
import com.kanishthika.moneymatters.transaction.ui.AddTransactionScreen
import com.kanishthika.moneymatters.transaction.ui.TransactionModel


@Composable
fun AppNavHost() {
    val modifier: Modifier = Modifier
    val navController = rememberNavController()

    val accountViewModel: AccountViewModel = viewModel()
    val investmentModel: InvestmentModel = hiltViewModel()
    val expenseModel: ExpenseModel = hiltViewModel()


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.Dashboard.route
    ) {
        composable(NavigationItem.Dashboard.route) {
            HomeScreen(accountViewModel, navController)
        }
        composable(NavigationItem.AddAccount.route) {
            AddAccountScreen(accountViewModel)
        }
        composable(NavigationItem.AddTransaction.route) {
            val transactionModel: TransactionModel = hiltViewModel()
            AddTransactionScreen(transactionModel, modifier, navController)
        }
        composable(NavigationItem.AddInvestment.route) {
            AddInvestmentScreen(investmentModel = investmentModel, modifier = modifier)
        }
        composable(NavigationItem.SelectAccounting.route){
            val accountingViewModel: AccountingViewModel = hiltViewModel()
            SelectAccountingScreen(accountingViewModel)
        }
        composable(NavigationItem.Accounting.route){
            AccountingScreen(modifier = modifier, navController)
        }
        composable(NavigationItem.AddExpense.route){
            AddExpenseScreen(expenseModel, modifier )
        }

    }
}