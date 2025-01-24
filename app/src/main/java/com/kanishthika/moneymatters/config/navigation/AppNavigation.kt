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
    MASTERSCREEN,
    ADDREMINDER,
    REMINDERLIST,
    TRANSACTIONREMINDER,

    //-0---------
    ADDTRANSACTION2,

    ADDLABELTYPE,
    LABELTYPESCREEN,

    ADDLABELSCREEN,
    LABELLISTSCREEN,

    TRANSACTIONLABELDIALOG,
    TRANSFERTOSELFSCREEN,

    BACKUPSCREEN,
    SIGNINSCREEN,

}

sealed class NavigationItem(val route: String) {
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)
    object AddTransaction : NavigationItem(Screen.ADDTRANSACTION.name)

    object AddExpense : NavigationItem("${Screen.ADDEXPENSE.name}?expense={expense}")
    object AddInvestment : NavigationItem("${Screen.ADDINVESTMENT.name}?investment={investment}")
    object AddLender : NavigationItem("${Screen.ADDLENDER.name}?lender={lender}")
    object AddIncome : NavigationItem("${Screen.ADDINCOME.name}?income={income}")
    object AddBorrower : NavigationItem("${Screen.ADDBORROWER.name}?borrower={borrower}")
    object AddInsurance : NavigationItem("${Screen.ADDINSURANCE.name}?insurance={insurance}")

    object AddInsuranceType : NavigationItem(Screen.ADDINSURANCETYPE.name)
    object AddAccountType : NavigationItem(Screen.ADDACCOUNTTYPE.name)

    object Master : NavigationItem(Screen.MASTERSCREEN.name)

    object AddAccount : NavigationItem(Screen.ADDACCOUNT.name)
    object Accounting : NavigationItem(Screen.ACCOUNTING.name)
    object InvestmentsList : NavigationItem(Screen.INVESTMENTSLIST.name)
    object ExpensesList : NavigationItem(Screen.EXPENSESLIST.name)

    object BorrowerList : NavigationItem(Screen.BORROWERSLIST.name)
    object LenderList : NavigationItem(Screen.LENDERLIST.name)
    object LBScreen : NavigationItem(Screen.LBSCREEN.name)
    object TransactionSummaryDialog : NavigationItem(Screen.TRANSACTIONSUMMARYDIALOG.name)
    object TransactionList : NavigationItem(Screen.TRANSACTIONLIST.name)
    object SearchTransactionScreen :
        NavigationItem("${Screen.SEARCHTRANSACTIONSCREEN.name}/{accountingName}")

    object AddReminderScreen : NavigationItem("${Screen.ADDREMINDER.name}?reminder={reminder}") {
        fun createAddReminderScreen(reminder: String?) =
            "${Screen.ADDREMINDER.name}?reminder=$reminder"
    }

    object AddTransaction2 : NavigationItem("${Screen.ADDTRANSACTION2.name}?transaction={transaction}"){
        fun createAddTransactionScreen(transaction: String?) =
            "${Screen.ADDTRANSACTION2.name}?transaction=$transaction"
    }

    object ReminderListScreen :
        NavigationItem("${Screen.REMINDERLIST.name}?reminderID={reminderID}")

    object TransactionReminder : NavigationItem(Screen.TRANSACTIONREMINDER.name)


    fun searchTransactionScreen(accountingName: String?) =
        "${Screen.SEARCHTRANSACTIONSCREEN.name}/$accountingName"

    fun createAddInvestmentScreen(investment: String?) =
        "${Screen.ADDINVESTMENT.name}?investment=$investment"

    fun createAddExpenseScreen(expense: String?) = "${Screen.ADDEXPENSE.name}?expense=$expense"
    fun createAddIncomeScreen(income: String?) = "${Screen.ADDINCOME.name}?income=$income"
    fun createAddLenderScreen(lender: String?) = "${Screen.ADDLENDER.name}?lender=$lender"
    fun createAddBorrowerScreen(borrower: String?) = "${Screen.ADDBORROWER.name}?borrower=$borrower"
    fun createAddInsuranceScreen(insurance: String?) =
        "${Screen.ADDINSURANCE.name}?insurance=$insurance"

    //  fun createAddReminderScreen(reminder: String?) = "${Screen.ADDREMINDER.name}?reminder=$reminder"
    fun createReminderListScreen(reminderID: String?) =
        "${Screen.REMINDERLIST.name}?reminderID = $reminderID"


    //---------------


    object AddLabelTypeDialog : NavigationItem(Screen.ADDLABELTYPE.name)
    object LabelTypeScreen : NavigationItem(Screen.LABELTYPESCREEN.name)

    object AddLabelScreen: NavigationItem(Screen.ADDLABELSCREEN.name){
        fun createAddLabelScreen(label: String?) =
            "${Screen.ADDLABELSCREEN.name}?label=$label"
    }

    object LabelListScreen: NavigationItem(Screen.LABELLISTSCREEN.name)
    object TransactionLabelDialog: NavigationItem(Screen.TRANSACTIONLABELDIALOG.name)

    object TransferToSelfScreen: NavigationItem(Screen.TRANSFERTOSELFSCREEN.name)

    object BackUpScreen : NavigationItem(Screen.BACKUPSCREEN.name)

    object SignInScreen: NavigationItem(Screen.SIGNINSCREEN.name)

}
