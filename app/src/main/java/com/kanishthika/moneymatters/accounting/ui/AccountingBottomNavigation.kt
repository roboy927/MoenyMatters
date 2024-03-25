package com.kanishthika.moneymatters.accounting.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kanishthika.moneymatters.accounting.AccountingBottomNavItem

@Composable
fun AccountingBottomNavigation(
    items: List<AccountingBottomNavItem>,
    accountingNavController: NavController
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val currentRoute = currentRoute(navController = accountingNavController)
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                label = { Text(text = item.name) },
                onClick = { accountingNavController.navigate(item.route){
                    popUpTo(currentRoute.toString()){
                        inclusive = true
                    }

                } },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.name)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    selectedIconColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}