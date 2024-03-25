package com.kanishthika.moneymatters.accounting.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kanishthika.moneymatters.accounting.accountingBottomNavigation
import com.kanishthika.moneymatters.components.MMTopAppBar
import com.kanishthika.moneymatters.navigation.AccountingNavHost
import com.kanishthika.moneymatters.navigation.Screen


@Composable
fun AccountingScreen(
    modifier: Modifier,
    appNavController: NavController
) {

    val accountingNavController = rememberNavController()

    Scaffold(
        topBar = { MMTopAppBar(titleText = "Accounting") },
        bottomBar = {
            AccountingBottomNavigation(
                items = accountingBottomNavigation,
                accountingNavController = accountingNavController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.size(45.dp),
                onClick = {
                    if (accountingNavController.currentDestination?.route == Screen.EXPENSESLIST.name) {
                        appNavController.navigate(Screen.ADDEXPENSE.name)
                    }
                    if (accountingNavController.currentDestination?.route == Screen.INVESTMENTSLIST.name){
                    appNavController.navigate(Screen.ADDINVESTMENT.name)
                    }
                },
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add", Modifier.size(25.dp))
            }
        }

    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AccountingNavHost(Modifier, accountingNavController)

        }


    }
}