package com.kanishthika.moneymatters.dashboard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.kanishthika.moneymatters.navigation.Screen


data class MenuItem(val icon: ImageVector, val label: String, val route: String)

val menuItems = listOf(
    MenuItem(Icons.Default.AddCircle, "Expense", Screen.ADDEXPENSE.name),
    MenuItem(Icons.Default.Info, "Investment", Screen.ADDINVESTMENT.name),
    MenuItem(Icons.Default.Menu, "Accounting", Screen.ACCOUNTING.name)
)

