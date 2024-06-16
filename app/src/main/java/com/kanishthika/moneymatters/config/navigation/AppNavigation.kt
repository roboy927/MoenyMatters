package com.kanishthika.moneymatters.config.navigation

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
    ADDBORROWER,
    BORROWERSLIST,
    ADDLENDER,
    LENDERLIST,
    LBSCREEN,
    TRANSACTIONSUMMARYDIALOG,
    TRANSACTIONLIST
}

sealed class NavigationItem(val route: String) {
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)
    object AddTransaction : NavigationItem(Screen.ADDTRANSACTION.name)
    object AddInvestment : NavigationItem(Screen.ADDINVESTMENT.name)
    object AddAccount : NavigationItem(Screen.ADDACCOUNT.name)
    object SelectAccounting : NavigationItem(Screen.SELECTACCOUNTING.name+"/{accountingType}")
          fun createRoute (accountingType: String) = Screen.SELECTACCOUNTING.name+"/$accountingType"
    object Accounting: NavigationItem(Screen.ACCOUNTING.name)
    object InvestmentsList: NavigationItem(Screen.INVESTMENTSLIST.name)
    object ExpensesList: NavigationItem(Screen.EXPENSESLIST.name)
    object AddExpense: NavigationItem(Screen.ADDEXPENSE.name)
    object AddBorrower: NavigationItem(Screen.ADDBORROWER.name)
    object BorrowerList: NavigationItem(Screen.BORROWERSLIST.name)
    object AddLender: NavigationItem(Screen.ADDLENDER.name)
    object LenderList: NavigationItem(Screen.LENDERLIST.name)
    object LBScreen: NavigationItem(Screen.LBSCREEN.name)
    object TransactionSummaryDialog: NavigationItem(Screen.TRANSACTIONSUMMARYDIALOG.name)
    object TransactionList: NavigationItem(Screen.TRANSACTIONLIST.name)
}
