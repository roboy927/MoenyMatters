package com.kanishthika.moneymatters.accounting.expense.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseListScreen(
    expenseModel: ExpenseModel,
    modifier: Modifier
){
    val allExpenses by expenseModel.getAllExpense.observeAsState(listOf())

    LazyColumn(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(allExpenses){
            expense ->
            ExpenseDetailBox(
                modifier = modifier,
                expenseName = expense.name,
                expenseAmount = expense.amount.toString(),
                expenseDescription = expense.description
            )
            if (allExpenses.lastIndex != allExpenses.indexOf(expense)) {
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)))
            }
        }
    }
}