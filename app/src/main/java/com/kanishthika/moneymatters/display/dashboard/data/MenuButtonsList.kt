package com.kanishthika.moneymatters.display.dashboard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.kanishthika.moneymatters.config.navigation.NavigationItem
import com.kanishthika.moneymatters.config.navigation.Screen


data class MenuItem(val icon: ImageVector, val label: String, val route: String)

val menuItems = listOf(
    MenuItem(Icons.Default.AddCircle, "Expense", Screen.ADDEXPENSE.name),
    MenuItem(Icons.Default.Info, "Investment", Screen.ADDINVESTMENT.name),
    MenuItem(Icons.Default.Menu, "Accounting", Screen.ACCOUNTING.name),
    MenuItem(Icons.Default.AccountCircle, "Borrowers", Screen.ADDLENDER.name),
    MenuItem(Icons.Default.MailOutline, "Transaction", NavigationItem.TransactionList.route)
)

