package com.kanishthika.moneymatters.display.dashboard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
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
    MenuItem(ImageVector.vectorResource(id = R.drawable.lender), "Expense", Screen.ADDEXPENSE.name),
    MenuItem(ImageVector.vectorResource(id = R.drawable.investment), "Investment", NavigationItem.AddInsurance.route),
    MenuItem(ImageVector.vectorResource(id = R.drawable.loan), "Accounting", Screen.ACCOUNTING.name),
    MenuItem(Icons.Default.AccountCircle, "Borrower", Screen.ADDLENDER.name),
    MenuItem(Icons.Default.MailOutline, "Transaction", NavigationItem.TransactionList.route),
    MenuItem(Icons.Default.Menu,"Master", NavigationItem.Master.route)
)
}