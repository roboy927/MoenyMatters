package com.kanishthika.moneymatters.display.dashboard.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DashBoardSummaryView(
    modifier: Modifier,
    totalIncome: Double,
    expense: Double,
    investment: Double,
    insurance: Double,
    loanEmi: Double,
    other: Double
) {

    val expenditure = expense + investment + insurance + loanEmi + other
    val marginAmount = totalIncome - expenditure

    val totalParts = if (marginAmount > 0) {
        expenditure + marginAmount
    } else {
        expenditure
    }

    Box(modifier = modifier.padding(8.dp)) {

        Column {

            Row(
                modifier = modifier
                    .clip(RoundedCornerShape(20))
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.White)
            ) {
                if (expense > 0) {
                    Box(
                        modifier = modifier
                            .weight((expense / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(Color.Red)
                    )
                }
                if (investment > 0) {
                    Box(
                        modifier = modifier
                            .weight((investment / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(Color.Blue)
                    )
                }

                if (insurance > 0) {

                    Box(
                        modifier = modifier
                            .weight((insurance / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(Color.Gray)
                    )
                }

                if (loanEmi > 0) {

                    Box(
                        modifier = modifier
                            .weight((loanEmi / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(Color.Magenta)
                    )
                }
                if (other > 0) {

                    Box(
                        modifier = modifier
                            .weight((other / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(Color.Yellow)
                    )
                }
                if (marginAmount > 0) {
                    Box(
                        modifier = modifier
                            .weight((marginAmount / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(Color.White)
                    )
                }
            }
            Spacer(modifier = modifier.height(4.dp))
            if (totalIncome > 0) {
                if (marginAmount < 0) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    ) {
                        Box(
                            modifier = modifier
                                .fillMaxHeight()
                                .weight((totalIncome / totalParts).toFloat())
                                .background(Color.Transparent)
                        )
                        Box (
                            modifier = modifier
                                .fillMaxHeight()
                                .weight((-marginAmount / totalParts).toFloat())
                                .background(Color.White.copy(0.5f))
                        )

                        }
                }
            } else {
                Box(
                    modifier = modifier
                        .height(5.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.outline)
                )
            }
            if (totalIncome< 0 || marginAmount < 0){
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Warning",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = modifier.size(10.dp)
                    )
                    Spacer(modifier = modifier.width(3.dp))
                    Text(
                        text = "Expenditure higher than Income",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DashBoardSummaryViewPreview() {
    DashBoardSummaryView(
        modifier = Modifier,
        totalIncome = 0.0,
        expense = 150.0,
        investment = 15.0,
        insurance = 10.0,
        loanEmi = 0.0,
        other = 0.0
    )
}