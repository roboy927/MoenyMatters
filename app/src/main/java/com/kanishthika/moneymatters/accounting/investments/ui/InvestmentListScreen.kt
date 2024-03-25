package com.kanishthika.moneymatters.accounting.investments.ui

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
fun InvestmentListScreen(
    investmentModel: InvestmentModel,
    modifier: Modifier
){
    val allInvestments by investmentModel.getAllInvestment.observeAsState(listOf())

    LazyColumn(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(allInvestments){
            investment ->
            InvestmentDetailBox(
                modifier = modifier,
                investmentName = investment.name,
                investmentAmount = investment.amount.toString(),
                investmentDescription = investment.description
            )
            if (allInvestments.lastIndex != allInvestments.indexOf(investment)) {
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)))
            }
        }
    }
}