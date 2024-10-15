package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.utils.clickableOnce
import com.kanishthika.moneymatters.display.transaction.ui.displayTransaction.TransactionListScreen

@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavController,
    searchTransactionModel: SearchTransactionModel,
    searchValue: String
) {

    val focusedColor: Color = MaterialTheme.colorScheme.onBackground
    val unfocusedColor: Color = MaterialTheme.colorScheme.onBackground.copy(0.5f)

    val displayTransaction by searchTransactionModel.searchedTransaction.collectAsState(emptyList())
    val isLoading by searchTransactionModel.isLoading.collectAsState()

    val searchQuery = remember { mutableStateOf(searchValue) }

    LaunchedEffect(Unit) {
        if (searchValue.isNotBlank())
        searchTransactionModel.searchTransactions(searchQuery.value)
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .statusBarsPadding()
            .fillMaxSize()

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = modifier.clickableOnce {
                    navController.popBackStack()
                }
            )
            TextField(
                value = searchQuery.value,
                onValueChange = {
                   searchQuery.value = it
                    if (it.length > 3) {
                        searchTransactionModel.searchTransactions(it)
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text("Search Words") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedPlaceholderColor = unfocusedColor,
                    unfocusedTextColor = unfocusedColor,
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = unfocusedColor,
                    focusedTextColor = focusedColor
                )

            )
            if (searchQuery.value.isNotEmpty()) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Clear",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier.clickableOnce {
                        searchQuery.value = ""
                    }
                )
            }
        }
        Spacer(
            modifier = modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground.copy(0.5f))
        )
        if (isLoading) {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            TransactionListScreen(
                modifier = modifier,
                displayTransactions = displayTransaction,
                emptyDataText = if (searchQuery.value.length > 3){
                    "No Data Found"
                } else {
                    "Enter Word to Search"
                },
                navigateToEdit = {},
                deleteTxn = {}
            )
        }
    }
}
