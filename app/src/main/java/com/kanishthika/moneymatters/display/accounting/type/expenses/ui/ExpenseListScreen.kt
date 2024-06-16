package com.kanishthika.moneymatters.display.accounting.type.expenses.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.display.accounting.ui.element.listOfDropDownItem

@Composable
fun ExpenseListScreen(
    expenseModel: ExpenseModel,
    modifier: Modifier
) {
    val allExpenses by expenseModel.getAllExpense.observeAsState(listOf())

    Column(modifier = modifier.padding(8.dp)) {

        Row(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Expenses",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Total Amount",
                color = MaterialTheme.colorScheme.outline,
                fontSize = 14.sp
            )
        }
        if (allExpenses.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = "No Data Found")
            }
        } else {
            Box(modifier = modifier.fillMaxSize()) {
                LazyColumn(
                ) {
                    items(allExpenses) { expense ->
                        ExpenseDetailBox(
                            modifier = modifier,
                            expenseName = expense.name,
                            expenseAmount = expense.amount.toString(),
                            expenseDescription = expense.description,
                            dropdownItems = listOfDropDownItem,
                            onItemClick = {
                                when(it.text) {
                                    "Delete" -> expenseModel.deleteExpense(expense)
                                    "Edit" -> {}
                                }
                            }
                        )
                        if (allExpenses.lastIndex != allExpenses.indexOf(expense)) {
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                            )
                        }
                    }
                }
            }
        }

    }
}