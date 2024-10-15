package com.kanishthika.moneymatters.display.dashboard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.navigation.Screen


data class MenuItem(val icon: ImageVector, val label: String, val route: String)

@Composable
fun menuItems(): List<MenuItem> {
    return listOf(
        MenuItem(
            ImageVector.vectorResource(id = R.drawable.lender),
            "Expense",
            NavigationItem.AddExpense.createAddExpenseScreen(null)
        ),
        MenuItem(
            ImageVector.vectorResource(id = R.drawable.investment),
            "Investment",
            NavigationItem.AddInsurance.createAddInsuranceScreen(null)
        ),
        MenuItem(
            ImageVector.vectorResource(id = R.drawable.loan),
            "Accounting",
            Screen.ACCOUNTING.name
        ),
        MenuItem(Icons.Default.AccountCircle, "Reminders", NavigationItem.ReminderListScreen.route),
        MenuItem(Icons.Default.MailOutline, "Transaction", NavigationItem.TransactionList.route),
        MenuItem(Icons.Default.Menu, "Master", NavigationItem.Master.route),
        MenuItem(Icons.Default.Notifications, "Reminder", NavigationItem.AddReminderScreen.createAddReminderScreen(null)),
        MenuItem(Icons.Default.Menu, "Label Type", NavigationItem.LabelTypeScreen.route),
        MenuItem(Icons.Default.ShoppingCart, "Labels", NavigationItem.LabelListScreen.route)
    )
}