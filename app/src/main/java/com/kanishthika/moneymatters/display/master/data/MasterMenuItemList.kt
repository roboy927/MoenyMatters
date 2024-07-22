package com.kanishthika.moneymatters.display.master.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.navigation.NavigationItem

data class MasterMenuItemList(val name: String, val icon: ImageVector, val route: String)



@Composable
fun masterMenuItemList(): List<MasterMenuItemList>{
    return listOf(
        MasterMenuItemList("Add Account", ImageVector.vectorResource(id = R.drawable.bankaccount), NavigationItem.AddAccount.route),
        MasterMenuItemList("Add Expense",ImageVector.vectorResource(id = R.drawable.expense), NavigationItem.AddExpense.route),
        MasterMenuItemList("Add Investment",ImageVector.vectorResource(id = R.drawable.investment), NavigationItem.AddInvestment.route),
        MasterMenuItemList("Add Income",ImageVector.vectorResource(id = R.drawable.income), NavigationItem.AddIncome.route),
        MasterMenuItemList("Add Lender",ImageVector.vectorResource(id = R.drawable.lender), NavigationItem.AddLender.route),
        MasterMenuItemList("Add Borrower",ImageVector.vectorResource(id = R.drawable.borrower), NavigationItem.AddBorrower.route),
        MasterMenuItemList("Account Type",ImageVector.vectorResource(id = R.drawable.bankaccount), NavigationItem.AddAccountType.route),
        MasterMenuItemList("Insurance Type",ImageVector.vectorResource(id = R.drawable.insurance), NavigationItem.AddInsuranceType.route),
        MasterMenuItemList("Add Insurance",ImageVector.vectorResource(id = R.drawable.insurance), NavigationItem.AddInsurance.route)
    )
}