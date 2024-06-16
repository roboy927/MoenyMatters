package com.kanishthika.moneymatters.display.accounting.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.kanishthika.moneymatters.config.navigation.Screen

data class AccountingBottomNavItem(
    val name: String,
    val icon: ImageVector,
    val route: String
)

val accountingBottomNavigation = listOf(
    AccountingBottomNavItem("Expense", Icons.Default.Home, Screen.EXPENSESLIST.name ),
    AccountingBottomNavItem("Investment", Icons.Default.AccountBox, Screen.INVESTMENTSLIST.name),
    AccountingBottomNavItem("L & B", Icons.Default.Menu, Screen.LBSCREEN.name),
    AccountingBottomNavItem("Income", Icons.Default.Info, Screen.EXPENSESLIST.name)
)