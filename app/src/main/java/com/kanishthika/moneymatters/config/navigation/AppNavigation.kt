package com.kanishthika.moneymatters.config.navigation

enum class Screen {
    DASHBOARD,
    ADDINVESTMENT,
    INVESTMENTSLIST,
    ADDEXPENSE,
    EXPENSESLIST,
    ADDTRANSACTION,
    ADDACCOUNT,
    ACCOUNTING,
    ADDBORROWER,
    BORROWERSLIST,
    ADDLENDER,
    LENDERLIST,
    LBSCREEN,
    TRANSACTIONSUMMARYDIALOG,
    TRANSACTIONLIST,
    SEARCHTRANSACTIONSCREEN,
    ADDINCOME,
    ADDINSURANCE,
    ADDINSURANCETYPE,
    ADDACCOUNTTYPE,
    MASTERSCREEN

}

sealed class NavigationItem(val route: String) {
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)
    object AddTransaction : NavigationItem(Screen.ADDTRANSACTION.name)

    object AddExpense: NavigationItem("${Screen.ADDEXPENSE.name}?expense={expense}")
    object AddInvestment : NavigationItem("${Screen.ADDINVESTMENT.name}?investment={investment}")
    object AddLender: NavigationItem("${Screen.ADDLENDER.name}?lender={lender}")
    object AddIncome : NavigationItem("${Screen.ADDINCOME.name}?income={income}")
    object AddBorrower: NavigationItem("${Screen.ADDBORROWER.name}?borrower={borrower}")
    object AddInsurance: NavigationItem("${Screen.ADDINSURANCE.name}?insurance={insurance}")

    object AddInsuranceType: NavigationItem(Screen.ADDINSURANCETYPE.name)
    object AddAccountType: NavigationItem(Screen.ADDACCOUNTTYPE.name)

    object Master: NavigationItem(Screen.MASTERSCREEN.name)

    object AddAccount : NavigationItem(Screen.ADDACCOUNT.name)
    object Accounting: NavigationItem(Screen.ACCOUNTING.name)
    object InvestmentsList: NavigationItem(Screen.INVESTMENTSLIST.name)
    object ExpensesList: NavigationItem(Screen.EXPENSESLIST.name)

    object BorrowerList: NavigationItem(Screen.BORROWERSLIST.name)
    object LenderList: NavigationItem(Screen.LENDERLIST.name)
    object LBScreen: NavigationItem(Screen.LBSCREEN.name)
    object TransactionSummaryDialog: NavigationItem(Screen.TRANSACTIONSUMMARYDIALOG.name)
    object TransactionList: NavigationItem(Screen.TRANSACTIONLIST.name)
    object SearchTransactionScreen: NavigationItem("${Screen.SEARCHTRANSACTIONSCREEN.name}/{accountingName}")



    fun searchTransactionScreen(accountingName: String?) = "${Screen.SEARCHTRANSACTIONSCREEN.name}/$accountingName"

    fun createAddInvestmentScreen(investment: String?) = "${Screen.ADDINVESTMENT.name}?investment=$investment"
    fun createAddExpenseScreen(expense: String?) = "${Screen.ADDEXPENSE.name}?expense=$expense"
    fun createAddIncomeScreen(income: String?) = "${Screen.ADDINCOME.name}?income=$income"
    fun createAddLenderScreen(lender: String?) = "${Screen.ADDLENDER.name}?lender=$lender"
    fun createAddBorrowerScreen(borrower: String?) = "${Screen.ADDBORROWER.name}?borrower=$borrower"
    fun createAddInsuranceScreen(insurance: String?) = "${Screen.ADDINSURANCE.name}?insurance=$insurance"
}
