package com.kanishthika.moneymatters.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.account.ui.AccountViewModel
import com.kanishthika.moneymatters.dashboard.data.menuItems
import com.kanishthika.moneymatters.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(accountViewModel: AccountViewModel, navController: NavController) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Money Matters",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                colors = TopAppBarDefaults.mediumTopAppBarColors(MaterialTheme.colorScheme.background),
                actions = {
                    IconButton(onClick = { /* Handle settings */ }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.ADDTRANSACTION.name) },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add", Modifier.size(35.dp))
            }
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState) {
                Snackbar(
                    snackbarData = it,
                    actionColor = MaterialTheme.colorScheme.primary,
                    dismissActionContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
//----------------------------------------------------------------------------------------------------
            AccountsDisplayCards(accountViewModel, scope, snackBarHostState) {
                navController.navigate(Screen.ADDACCOUNT.name)
            }

            Spacer(modifier = Modifier.height(12.dp))
//-----------------------------------------------------------------------------------------------------
            Column {
                Text(
                    text = "Menu and Tools",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                //Line of Icon Button
               Menu(menuItems = menuItems, navController = navController)

            }
        }
    }
}
