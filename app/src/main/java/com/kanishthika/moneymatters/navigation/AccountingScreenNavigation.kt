package com.kanishthika.moneymatters.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kanishthika.moneymatters.accounting.expense.ui.ExpenseListScreen
import com.kanishthika.moneymatters.accounting.expense.ui.ExpenseModel
import com.kanishthika.moneymatters.accounting.investments.ui.InvestmentListScreen
import com.kanishthika.moneymatters.accounting.investments.ui.InvestmentModel

@Composable
fun AccountingNavHost(
    modifier: Modifier = Modifier,
    accountingNavController: NavHostController
) {


    NavHost(
        modifier = modifier,
        navController = accountingNavController,
        startDestination = NavigationItem.InvestmentsList.route
    ) {
        composable(NavigationItem.InvestmentsList.route) {
            val investmentModel: InvestmentModel = hiltViewModel()
            InvestmentListScreen(investmentModel = investmentModel , modifier = modifier)
        }
        composable(NavigationItem.ExpensesList.route) {
            val expenseModel: ExpenseModel = hiltViewModel()
            ExpenseListScreen(expenseModel = expenseModel, modifier = modifier)
        }
    }
}