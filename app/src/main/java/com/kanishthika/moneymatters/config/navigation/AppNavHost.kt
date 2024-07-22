package com.kanishthika.moneymatters.config.navigation

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
import com.google.gson.Gson
import com.kanishthika.moneymatters.display.account.ui.AccountViewModel
import com.kanishthika.moneymatters.display.account.ui.AddAccountScreen
import com.kanishthika.moneymatters.display.account.ui.accountType.AccountTypeModel
import com.kanishthika.moneymatters.display.account.ui.accountType.AddAccountType
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.borrower.ui.AddBorrowerScreen
import com.kanishthika.moneymatters.display.accounting.type.borrower.ui.BorrowerModel
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.type.expenses.ui.AddExpenseScreen
import com.kanishthika.moneymatters.display.accounting.type.expenses.ui.ExpenseModel
import com.kanishthika.moneymatters.display.accounting.type.income.data.Income
import com.kanishthika.moneymatters.display.accounting.type.income.ui.AddIncomeScreen
import com.kanishthika.moneymatters.display.accounting.type.income.ui.IncomeModel
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.Insurance
import com.kanishthika.moneymatters.display.accounting.type.insurance.ui.AddInsuranceScreen
import com.kanishthika.moneymatters.display.accounting.type.insurance.ui.InsuranceModel
import com.kanishthika.moneymatters.display.accounting.type.insurance.ui.insuranceType.AddInsuranceType
import com.kanishthika.moneymatters.display.accounting.type.insurance.ui.insuranceType.InsuranceTypeModel
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.type.investments.ui.AddInvestmentScreen
import com.kanishthika.moneymatters.display.accounting.type.investments.ui.InvestmentModel
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.Lender
import com.kanishthika.moneymatters.display.accounting.type.lenders.ui.AddLenderScreen
import com.kanishthika.moneymatters.display.accounting.type.lenders.ui.LenderModel
import com.kanishthika.moneymatters.display.accounting.ui.AccountingScreen
import com.kanishthika.moneymatters.display.accounting.ui.AccountingViewModel
import com.kanishthika.moneymatters.display.dashboard.ui.DashBoardModel
import com.kanishthika.moneymatters.display.dashboard.ui.HomeScreen
import com.kanishthika.moneymatters.display.master.ui.MasterScreen
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.AddTransactionModel
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.AddTransactionScreen
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.TransactionSummaryDialog
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.DisplayTransactionModel
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.DisplayTransactionScreen
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.searchScreen.SearchScreen
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.searchScreen.SearchTransactionModel


@Composable
fun AppNavHost() {
    val modifier: Modifier = Modifier
    val navController = rememberNavController()

    val gson = Gson()

    NavHost(modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.Dashboard.route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            fadeOut(tween(400))

        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(400)
            )
        }) {
        composable(NavigationItem.Dashboard.route) {
            val accountViewModel: AccountViewModel = hiltViewModel()
            val dashBoardModel: DashBoardModel = hiltViewModel()
            HomeScreen(accountViewModel, dashBoardModel, navController)
        }

        composable(NavigationItem.AddAccount.route) {
            val accountViewModel: AccountViewModel = hiltViewModel()
            AddAccountScreen(accountViewModel,navController = navController)
        }

        dialog(
            route = NavigationItem.AddAccountType.route
        ) {
            val accountTypeModel: AccountTypeModel = hiltViewModel()
            AddAccountType(
                modifier = modifier,
                accountTypeModel = accountTypeModel,
                navController = navController
            )
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
            val investmentModel: InvestmentModel = hiltViewModel()
            val expenseModel: ExpenseModel = hiltViewModel()
            val incomeModel: IncomeModel = hiltViewModel()
            val lenderModel: LenderModel = hiltViewModel()
            val borrowerModel: BorrowerModel = hiltViewModel()
            val insuranceModel: InsuranceModel = hiltViewModel()
            AccountingScreen(
                modifier = modifier,
                navController = navController,
                accountingViewModel = accountingViewModel,
                investmentModel = investmentModel,
                expenseModel = expenseModel,
                incomeModel = incomeModel,
                lenderModel = lenderModel,
                borrowerModel = borrowerModel,
                insuranceModel = insuranceModel
            )
        }

        composable(
            NavigationItem.AddExpense.route
        ) { backStackEntry ->
            val expenseJson = backStackEntry.arguments?.getString("expense")
            val expense = gson.fromJson(expenseJson, Expense::class.java)
            val expenseModel: ExpenseModel = hiltViewModel()
            AddExpenseScreen(expenseModel, modifier, navController, expense)
        }

        composable(
            NavigationItem.AddInvestment.route
        ) { backStackEntry ->
            val investmentJson = backStackEntry.arguments?.getString("investment")
            val investment = gson.fromJson(investmentJson, Investment::class.java)
            val investmentModel: InvestmentModel = hiltViewModel()
            AddInvestmentScreen(investmentModel, modifier, navController, investment)
        }

        composable(
            NavigationItem.AddIncome.route
        ) { backStackEntry ->
            val incomeJson = backStackEntry.arguments?.getString("income")
            val income = gson.fromJson(incomeJson, Income::class.java)
            val incomeModel: IncomeModel = hiltViewModel()
            AddIncomeScreen(incomeModel, modifier, navController, income)
        }

        composable(
            NavigationItem.AddLender.route
        ) { backStackEntry ->
            val lenderJson = backStackEntry.arguments?.getString("lender")
            val lender = gson.fromJson(lenderJson, Lender::class.java)
            val lenderModel: LenderModel = hiltViewModel()
            AddLenderScreen(modifier, lenderModel, lender, navController)
        }

        composable(
            NavigationItem.AddInsurance.route
        ) { backStackEntry ->
            val insuranceJson = backStackEntry.arguments?.getString("insurance")
            val insurance = gson.fromJson(insuranceJson, Insurance::class.java)
            val insuranceModel: InsuranceModel = hiltViewModel()
            AddInsuranceScreen(insuranceModel, modifier, navController, insurance)
        }

        dialog(
            route = NavigationItem.AddInsuranceType.route
        ) {
            val insuranceTypeModel: InsuranceTypeModel = hiltViewModel()
            AddInsuranceType(
                modifier = modifier,
                insuranceTypeModel = insuranceTypeModel,
                navController = navController
            )
        }

        composable(
            NavigationItem.AddBorrower.route
        ) { backStackEntry ->
            val borrowerJson = backStackEntry.arguments?.getString("borrower")
            val borrower = gson.fromJson(borrowerJson, Borrower::class.java)
            val borrowerModel: BorrowerModel = hiltViewModel()
            AddBorrowerScreen(modifier, borrowerModel, borrower, navController)
        }

        composable(NavigationItem.TransactionList.route) {
            val displayTransactionModel: DisplayTransactionModel = hiltViewModel()
            DisplayTransactionScreen(
                modifier = modifier,
                displayTransactionModel = displayTransactionModel,
                navController = navController
            )
        }


        composable(
            NavigationItem.SearchTransactionScreen.route,
            arguments = listOf(
                navArgument("accountingName") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val accountingName = backStackEntry.arguments?.getString("accountingName") ?: ""
            val searchTransactionModel: SearchTransactionModel = hiltViewModel()
            SearchScreen(
                modifier = modifier,
                searchTransactionModel = searchTransactionModel,
                navController = navController,
                searchValue = accountingName
            )
        }

        composable(NavigationItem.Master.route){
            MasterScreen(modifier = modifier, navController = navController)
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