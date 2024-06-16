package com.kanishthika.moneymatters.display.accounting.type.lb.lenders.ui

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
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.Lender

@Composable
fun LenderListScreen(
    lenderModel: LenderModel, modifier: Modifier, searchText: String
) {
    val allLenders: List<Lender> by lenderModel.searchedLender(searchText)
        .observeAsState(emptyList())

    Column(
        modifier = modifier.padding(8.dp),
    ) {
        if (allLenders.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = "No Data Found")
            }
        } else {
            Box(modifier = modifier.fillMaxSize() ){

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {


                    item {
                        Row(
                            modifier = modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Lenders",
                                color = MaterialTheme.colorScheme.outline,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Total Amount",
                                color = MaterialTheme.colorScheme.outline,
                                fontSize = 14.sp
                            )
                        }
                    }
                    items(allLenders) { lender ->
                        LenderDetailBox(
                            modifier = modifier,
                            lenderName = lender.lenderName,
                            lenderAmount = lender.amount.toString(),
                            lenderContactNumber = lender.lenderContactNumber
                        )
                        if (allLenders.lastIndex != allLenders.indexOf(lender)) {
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