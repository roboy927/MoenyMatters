package com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui

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
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.Borrower

@Composable
fun BorrowerListScreen(
    borrowerModel: BorrowerModel, modifier: Modifier, searchText: String
) {
    val allBorrowers: List<Borrower> by borrowerModel.searchedBorrower(searchText)
        .observeAsState(emptyList())

    Column(
        modifier = modifier.padding(8.dp),
    ) {
        Row(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Borrowers",
                color = MaterialTheme.colorScheme.outline,
                fontSize = 14.sp
            )
            Text(
                text = "Total Amount",
                color = MaterialTheme.colorScheme.outline,
                fontSize = 14.sp
            )
        }
        if (allBorrowers.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = "No Data Found")
            }
        } else {
            Box(modifier = modifier.fillMaxSize()){

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {


                    items(allBorrowers) { borrower ->
                        BorrowerDetailBox(
                            modifier = modifier,
                            borrowerName = borrower.borrowerName,
                            borrowerAmount = borrower.amount.toString(),
                            borrowerContactNumber = borrower.borrowerContactNumber
                        )
                        if (allBorrowers.lastIndex != allBorrowers.indexOf(borrower)) {
                            Spacer(
                                modifier = modifier
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