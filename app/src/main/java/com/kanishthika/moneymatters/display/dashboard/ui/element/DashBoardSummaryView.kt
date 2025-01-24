package com.kanishthika.moneymatters.display.dashboard.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMActionBarItem
import com.kanishthika.moneymatters.config.mmComposable.MMText3
import com.kanishthika.moneymatters.config.utils.formatTo2Decimal
import com.kanishthika.moneymatters.display.accounting.data.AmountViewType
import com.kanishthika.moneymatters.display.dashboard.data.borrowerColor
import com.kanishthika.moneymatters.display.dashboard.data.expenseColor
import com.kanishthika.moneymatters.display.dashboard.data.incomeColor
import com.kanishthika.moneymatters.display.dashboard.data.insuranceColor
import com.kanishthika.moneymatters.display.dashboard.data.investmentColor
import com.kanishthika.moneymatters.display.dashboard.data.lenderColor
import com.kanishthika.moneymatters.display.dashboard.data.loanEmiColor
import com.kanishthika.moneymatters.display.dashboard.data.otherColor
import com.kanishthika.moneymatters.display.dashboard.data.warningColor

@Composable
fun DashboardSummaryView(
    modifier: Modifier = Modifier,
    amountViewType: AmountViewType,
    changeAmountViewType: (AmountViewType) -> Unit,
    changeSummaryTime: (String) -> Unit,
    totalIncome: Double,
    expense: Double,
    investment: Double,
    insurance: Double,
    loanEmi: Double,
    other: Double,
    lender: Double,
    borrower: Double,
    monthText: String,
    yearText: String,
    monthList: List<String>,
    yearList: List<String>
) {
    var monthDDMenuVisible by remember {
        mutableStateOf(false)
    }

    var yearDDMenuVisible by remember {
        mutableStateOf(false)
    }

    Column(

    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box {
                if (amountViewType == AmountViewType.MONTH) {
                    MMActionBarItem(
                        text = monthText,
                        click = Modifier.clickable { monthDDMenuVisible = true },
                        modifier = modifier,
                        imageVector = Icons.Default.ArrowDropDown,
                        border = Modifier,
                        iconSize = 20.dp,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    DropdownMenu(
                        modifier = modifier
                            .heightIn(max = 300.dp)
                            .background(MaterialTheme.colorScheme.inverseSurface),
                        expanded = monthDDMenuVisible,
                        onDismissRequest = { monthDDMenuVisible = false }
                    ) {
                        monthList.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    monthDDMenuVisible = false
                                    changeSummaryTime(it)
                                }
                            )
                        }
                    }
                }
            }
            Box {
                if (amountViewType == AmountViewType.YEAR) {
                    MMActionBarItem(
                        text = yearText,
                        click = Modifier.clickable { yearDDMenuVisible = true },
                        modifier = modifier,
                        imageVector = Icons.Default.ArrowDropDown,
                        border = Modifier,
                        iconSize = 15.dp,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    DropdownMenu(
                        modifier = modifier
                            .heightIn(max = 300.dp)
                            .background(MaterialTheme.colorScheme.inverseSurface),
                        expanded = yearDDMenuVisible,
                        onDismissRequest = { yearDDMenuVisible = false }
                    ) {
                        yearList.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    yearDDMenuVisible = false
                                    changeSummaryTime(it)
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = modifier.width(4.dp))
            MMActionBarItem(
                text = when (amountViewType) {
                    AmountViewType.TOTAL -> "Total"
                    AmountViewType.MONTH -> "Month"
                    AmountViewType.YEAR -> "Year"
                },
                modifier = modifier,
                imageVector = Icons.Sharp.MoreVert,
                border = Modifier,
                iconSize = 15.dp,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                click = Modifier.clickable {
                    changeAmountViewType(
                        when (amountViewType) {
                            AmountViewType.TOTAL -> AmountViewType.MONTH
                            AmountViewType.MONTH -> AmountViewType.YEAR
                            AmountViewType.YEAR -> AmountViewType.TOTAL
                        }
                    )
                }
            )
        }
        DashBoardSummaryGraphic(
            modifier = modifier,
            totalIncome = totalIncome,
            expense = expense,
            investment = investment,
            insurance = insurance,
            loanEmi = loanEmi,
            other = other,
            lender = lender,
            borrower = borrower
        )
    }
}


@Composable
fun DashBoardSummaryGraphic(
    modifier: Modifier,
    totalIncome: Double,
    expense: Double,
    investment: Double,
    insurance: Double,
    loanEmi: Double,
    other: Double,
    lender: Double,
    borrower: Double
) {

    val expenditure = expense + investment + insurance + loanEmi + other + borrower
    val marginAmount = (totalIncome + lender )- expenditure

    val totalParts = if (marginAmount > 0) {
        expenditure + marginAmount
    } else {
        expenditure
    }

    val emptyData = expenditure == 0.0 && totalIncome == 0.0

    Box(modifier = modifier.padding(8.dp)) {
        Column {
            Row(
                modifier = modifier
                    .clip(RoundedCornerShape(20))
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.Transparent),
                horizontalArrangement = if (emptyData) Arrangement.Center else Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (emptyData) {
                    MMText3(text = "Transaction Not Added")
                }
                if (expense > 0) {
                    Box(
                        modifier = modifier
                            .weight((expense / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(expenseColor)
                    )
                }
                if (investment > 0) {
                    Box(
                        modifier = modifier
                            .weight((investment / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(investmentColor)
                    )
                }
                if (insurance > 0) {
                    Box(
                        modifier = modifier
                            .weight((insurance / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(insuranceColor)
                    )
                }

                if (loanEmi > 0) {
                    Box(
                        modifier = modifier
                            .weight((loanEmi / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(loanEmiColor)
                    )
                }
                if (other > 0) {
                    Box(
                        modifier = modifier
                            .weight((other / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(otherColor)
                    )
                }
                if (borrower > 0) {
                    Box(
                        modifier = modifier
                            .weight((borrower / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(borrowerColor)
                    )
                }
                if (marginAmount > 0) {
                    Box(
                        modifier = modifier
                            .weight((marginAmount / totalParts).toFloat())
                            .fillMaxHeight()
                            .background(Color.Transparent)
                    )
                }

            }
            Spacer(modifier = modifier.height(4.dp))
            if (totalIncome > 0) {
                    Row(
                        modifier = modifier
                            .clip(RoundedCornerShape(40))
                            .fillMaxWidth()
                            .height(5.dp)
                    ) {
                        Box(
                            modifier = modifier
                                .fillMaxHeight()
                                .weight((totalIncome / totalParts).toFloat())
                                .background(incomeColor)
                        )
                        Box(
                            modifier = modifier
                                .fillMaxHeight()
                                .weight((lender.coerceAtLeast(0.1) / totalParts).toFloat())
                                .background(lenderColor)
                        )
                        if (marginAmount < 0){
                            Box(
                                modifier = modifier
                                    .fillMaxHeight()
                                    .weight((-marginAmount / totalParts).toFloat())
                                    .background(warningColor)
                            )
                        }
                    }
            }
            if (totalIncome < 0 || marginAmount < 0) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Warning",
                        tint = warningColor,
                        modifier = modifier.size(10.dp)
                    )
                    Spacer(modifier = modifier.width(3.dp))
                    Text(
                        text = "Debit Amount Higher than Credit",
                        style = MaterialTheme.typography.bodySmall,
                        color = warningColor
                    )
                }
            }
            Spacer(modifier = modifier.height(20.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SummaryItem(
                        modifier = modifier,
                        name = "Expense",
                        color = expenseColor,
                        amount = expense.toString()
                    )
                    SummaryItem(
                        modifier = modifier,
                        name = "Investment",
                        color = investmentColor,
                        amount = investment.toString()
                    )
                    SummaryItem(
                        modifier = modifier,
                        name = "Other",
                        color = otherColor,
                        amount = other.toString()
                    )
                    SummaryItem(
                        modifier = modifier,
                        name = "Borrower",
                        color = borrowerColor ,
                        amount = borrower.toString()
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SummaryItem(
                        modifier = modifier,
                        name = "Insurance",
                        color = insuranceColor,
                        amount = insurance.toString()
                    )
                    SummaryItem(
                        modifier = modifier,
                        name = "Loan EMI",
                        color = loanEmiColor,
                        amount = loanEmi.toString()
                    )
                    SummaryItem(
                        modifier = modifier,
                        name = "Income",
                        color = incomeColor,
                        amount = totalIncome.toString()
                    )
                    SummaryItem(
                        modifier = modifier,
                        name = "Lender",
                        color = lenderColor ,
                        amount = lender.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryItem(
    modifier: Modifier,
    name: String,
    color: Color,
    amount: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = modifier
                .size(15.dp)
                .background(color, RoundedCornerShape(20))
        )
        MMText3(text = "$name ")
        MMText3(text = "${stringResource(id = R.string.rupee_symbol)} ${formatTo2Decimal(amount)}")
    }
}

@Preview(showSystemUi = true)
@Composable
fun DashBoardSummaryViewPreview() {
    DashBoardSummaryGraphic(
        modifier = Modifier,
        totalIncome = 440.0,
        expense = 150.0,
        investment = 15.0,
        insurance = 10.0,
        loanEmi = 0.0,
        other = 0.0,
        lender = 10.0,
        borrower = 10.0
    )
}