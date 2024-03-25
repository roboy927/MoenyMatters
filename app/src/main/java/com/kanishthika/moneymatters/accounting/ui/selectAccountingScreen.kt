package com.kanishthika.moneymatters.accounting.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.kanishthika.moneymatters.accounting.AccountingType
import com.kanishthika.moneymatters.accounting.AccountingViewModel
import com.kanishthika.moneymatters.accounting.expense.data.Expense
import com.kanishthika.moneymatters.accounting.investments.data.Investment
import com.kanishthika.moneymatters.components.MMOutlinedButton

@Composable
fun SelectAccountingScreen(
    accountingViewModel: AccountingViewModel,
    accountingType: AccountingType = AccountingType.INVESTMENT,


) {
    val accountingList by when (accountingType) {
        is AccountingType.INVESTMENT -> accountingViewModel.getAllInvestment.observeAsState(
            emptyList()
        )

        is AccountingType.EXPENSE -> accountingViewModel.getAllExpense.observeAsState(emptyList())
        is AccountingType.BORROWERS -> accountingViewModel.getAllExpense.observeAsState(emptyList())
        is AccountingType.INCOME -> accountingViewModel.getAllExpense.observeAsState(emptyList())
        is AccountingType.LENDERS -> accountingViewModel.getAllExpense.observeAsState(emptyList())
    }
    Scaffold {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item {
                MMOutlinedButton(
                    modifier = Modifier,
                    text = "Console"
                ) {
                    Log.d("TAG", "SelectAccountingScreen: $accountingList ")
                }
            }
            items(accountingList) { item ->
                // Generate UI element based on the type of item
                when (item) {
                    is Investment -> {
                        Text(text = item.name)
                    }

                    is Expense -> {
                        Text(text = item.name)
                    }

                }
            }
        }
    }
}

