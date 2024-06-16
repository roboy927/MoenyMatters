package com.kanishthika.moneymatters.config.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kanishthika.moneymatters.display.account.ui.AccountViewModel
import com.kanishthika.moneymatters.display.account.ui.AddAccountScreen
import com.kanishthika.moneymatters.display.accounting.type.expenses.ui.AddExpenseScreen
import com.kanishthika.moneymatters.display.accounting.type.expenses.ui.ExpenseModel
import com.kanishthika.moneymatters.display.accounting.type.investments.ui.AddInvestmentScreen
import com.kanishthika.moneymatters.display.accounting.type.investments.ui.InvestmentModel
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui.AddBorrowerScreen
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui.BorrowerModel
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.ui.AddLenderScreen
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.ui.LenderModel
import com.kanishthika.moneymatters.display.accounting.ui.AccountingScreen
import com.kanishthika.moneymatters.display.accounting.ui.AccountingViewModel
import com.kanishthika.moneymatters.display.dashboard.ui.HomeScreen
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.AddTransactionModel
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.AddTransactionScreen
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.SelectAccountingDialog
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.TransactionSummaryDialog
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.DisplayTransactionModel
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.TransactionListScreen


@Composable
fun AppNavHost() {
    val modifier: Modifier = Modifier
    val navController = rememberNavController()

    NavHost(modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.Dashboard.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            fadeOut(tween(500))

        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(500))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }) {
        composable(NavigationItem.Dashboard.route) {
            val accountViewModel: AccountViewModel = hiltViewModel()
            HomeScreen(accountViewModel, navController)
        }
        composable(NavigationItem.AddAccount.route) {
            val accountViewModel: AccountViewModel = hiltViewModel()
            AddAccountScreen(accountViewModel)
        }

        composable(NavigationItem.AddInvestment.route) {
            val investmentModel: InvestmentModel = hiltViewModel()
            AddInvestmentScreen(investmentModel = investmentModel, modifier = modifier)
        }

        navigation(
            startDestination = NavigationItem.AddTransaction.route, route = "AddTransaction"
        ) {
            composable(NavigationItem.AddTransaction.route) {
                val addTransactionModel =
                    it.sharedViewmodel<AddTransactionModel>(navController = navController)
                AddTransactionScreen(addTransactionModel, modifier, navController)
            }
            dialog(
                route = NavigationItem.SelectAccounting.route,
                arguments = listOf(navArgument("accountingType") { type = NavType.StringType })
            ) { backStackEntry ->
                val accountingType = backStackEntry.arguments?.getString("accountingType") ?: ""
                val addTransactionModel =
                    backStackEntry.sharedViewmodel<AddTransactionModel>(navController = navController)
                SelectAccountingDialog(
                    navHostController = navController,
                    modifier = modifier,
                    accountingType = accountingType,
                    addTransactionModel = addTransactionModel
                )
            }
            dialog(
                NavigationItem.TransactionSummaryDialog.route
            ) {
                val addTransactionModel: AddTransactionModel =
                    it.sharedViewmodel(navController = navController)
                TransactionSummaryDialog(
                    modifier = modifier, addTransactionModel = addTransactionModel, navController
                )
            }
        }



        composable(NavigationItem.Accounting.route) {
            val accountingViewModel: AccountingViewModel = hiltViewModel()
            AccountingScreen(modifier = modifier, navController, accountingViewModel)
        }
        composable(NavigationItem.AddExpense.route) {
            val expenseModel: ExpenseModel = hiltViewModel()
            AddExpenseScreen(expenseModel, modifier)
        }
        composable(NavigationItem.AddBorrower.route) {
            val borrowerModel: BorrowerModel = hiltViewModel()
            AddBorrowerScreen(modifier = modifier, borrowerModel)
        }
        composable(NavigationItem.AddLender.route) {
            val lenderModel: LenderModel = hiltViewModel()
            AddLenderScreen(modifier = modifier, lenderModel)
        }
        composable(NavigationItem.TransactionList.route) {
            val displayTransactionModel: DisplayTransactionModel = hiltViewModel()
            TransactionListScreen(
                modifier = modifier,
                displayTransactionModel = displayTransactionModel,
                navController = navController
            )
        }

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewmodel(navController: NavController): T {
    val parentRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(parentRoute)
    }
    return hiltViewModel(parentEntry)
}