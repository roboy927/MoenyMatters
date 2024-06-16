package com.kanishthika.moneymatters.config.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kanishthika.moneymatters.display.accounting.type.expenses.ui.ExpenseListScreen
import com.kanishthika.moneymatters.display.accounting.type.expenses.ui.ExpenseModel
import com.kanishthika.moneymatters.display.accounting.type.investments.ui.InvestmentListScreen
import com.kanishthika.moneymatters.display.accounting.type.investments.ui.InvestmentModel
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui.BorrowerListScreen
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui.BorrowerModel
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.ui.LenderListScreen
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.ui.LenderModel
import com.kanishthika.moneymatters.display.accounting.type.lb.ui.LBScreen

@Composable
fun AccountingNavHost(
    modifier: Modifier = Modifier,
    accountingNavController: NavHostController,
    searchText: String
) {

    NavHost(
        modifier = modifier.fillMaxSize(),
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
        composable(NavigationItem.BorrowerList.route){
            val borrowerModel: BorrowerModel = hiltViewModel()
            BorrowerListScreen(borrowerModel = borrowerModel, modifier = modifier, searchText = searchText )
        }
        composable(NavigationItem.LenderList.route){
            val lenderModel: LenderModel = hiltViewModel()
            LenderListScreen(lenderModel, modifier = modifier, searchText = searchText )
        }

        composable(NavigationItem.LBScreen.route){
            val lenderModel: LenderModel = hiltViewModel()
            val borrowerModel: BorrowerModel = hiltViewModel()
            LBScreen(lenderModel = lenderModel, borrowerModel =  borrowerModel, modifier = modifier, searchText = searchText)
        }
    }
}