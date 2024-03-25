package com.kanishthika.moneymatters.navigation

enum class Screen {
    DASHBOARD,
    ADDINVESTMENT,
    INVESTMENTSLIST,
    ADDEXPENSE,
    EXPENSESLIST,
    ADDTRANSACTION,
    ADDACCOUNT,
    SELECTACCOUNTING,
    ACCOUNTING,
}

sealed class NavigationItem(val route: String) {
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)
    object AddTransaction : NavigationItem(Screen.ADDTRANSACTION.name)
    object AddInvestment : NavigationItem(Screen.ADDINVESTMENT.name)
    object AddAccount : NavigationItem(Screen.ADDACCOUNT.name)
    object SelectAccounting : NavigationItem(Screen.SELECTACCOUNTING.name)
    object Accounting: NavigationItem(Screen.ACCOUNTING.name)
    object InvestmentsList: NavigationItem(Screen.INVESTMENTSLIST.name)
    object ExpensesList: NavigationItem(Screen.EXPENSESLIST.name)
    object AddExpense: NavigationItem(Screen.ADDEXPENSE.name)
}
