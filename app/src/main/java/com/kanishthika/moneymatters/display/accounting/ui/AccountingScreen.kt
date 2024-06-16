package com.kanishthika.moneymatters.display.accounting.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kanishthika.moneymatters.display.accounting.data.accountingBottomNavigation
import com.kanishthika.moneymatters.display.accounting.ui.element.AccountingBottomNavigation
import com.kanishthika.moneymatters.config.components.MMSearchBar
import com.kanishthika.moneymatters.config.navigation.AccountingNavHost
import com.kanishthika.moneymatters.config.navigation.Screen


@Composable
fun AccountingScreen(
    modifier: Modifier,
    appNavController: NavController,
    accountingViewModel: AccountingViewModel
) {

    val accountingNavController = rememberNavController()


    val searchText by accountingViewModel.searchText.observeAsState("")

    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = {
            MMSearchBar(
                query = searchText,
                modifier = Modifier,
                onQueryChange = {
                    accountingViewModel.onSearchTextChanged(it)

                },
                onClearClick = { accountingViewModel.onClearClick() }
            )

        },
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
                    if (accountingNavController.currentDestination?.route == Screen.INVESTMENTSLIST.name) {
                        appNavController.navigate(Screen.ADDINVESTMENT.name)
                    }
                    if (accountingNavController.currentDestination?.route == Screen.LBSCREEN.name) {
                        appNavController.navigate(Screen.ADDBORROWER.name)
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
            AccountingNavHost(Modifier, accountingNavController, searchText)

        }
    }

}