package com.kanishthika.moneymatters.display.label.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.mmComposable.MMLoadingScreen
import com.kanishthika.moneymatters.config.mmComposable.MMText2
import com.kanishthika.moneymatters.config.mmComposable.MMText3
import com.kanishthika.moneymatters.config.mmComposable.MMText5
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.config.utils.convertToLocalDate
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionType
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelListScreen(
    modifier: Modifier = Modifier,
    navigateToAdd: () -> Unit,
    labelListModel: LabelListModel = hiltViewModel()
) {

    val labelList by labelListModel.labelList.collectAsStateWithLifecycle()
    val loading by labelListModel.loadingScreen.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = { MMTopAppBar(titleText = "Labels") },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.height(IntrinsicSize.Min),
                onClick = { navigateToAdd() },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.7f),
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(0.8f)
            ) {
                Row(
                    modifier = modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                    Text(
                        text = "Label",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        if (loading) {
            MMLoadingScreen(modifier = modifier.padding(paddingValues))
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.animateContentSize { initialValue, targetValue ->  }
                    .padding(paddingValues)
                    .padding(8.dp),
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalItemSpacing = 12.dp
            ) {
                items(labelList) {
                    val debitAmount by produceState(initialValue = 0.0, it) {
                        value = labelListModel.getDebitTotalForLabel(it.labelName)
                    }
                    val creditAmount by produceState(initialValue = 0.0, it) {
                        value = labelListModel.getCreditTotalForLabel(it.labelName)
                    }
                    val transaction5List by labelListModel.getRecentLabelTransaction(it.labelName)
                        .collectAsStateWithLifecycle(
                            initialValue = emptyList()
                        )
                    LabelBox(
                        modifier = modifier,
                        label = it,
                        creditAmount = creditAmount,
                        debitAmount = debitAmount,
                        transaction5List = transaction5List
                    )
                }
            }
        }
    }
}

@Composable
fun LabelBox(
    modifier: Modifier,
    label: Label,
    debitAmount: Double,
    creditAmount: Double,
    transaction5List: List<Transaction>
) {
    Box(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10))
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MMText2(text = label.labelName)
                MMText3(text = label.labelType)
            }
            if (debitAmount != 0.0) {
                MMText3(text = "Debited: ${stringResource(id = R.string.rupee_symbol)}$debitAmount")
            }
            if (creditAmount != 0.0) {
                MMText3(text = "Credited: ${stringResource(id = R.string.rupee_symbol)}$creditAmount")
            }

            Column {
                transaction5List.forEach {
                    LabelTransactionShort(
                        modifier = modifier,
                        amount = it.amount.toString(),
                        type = it.type,
                        date = it.date,
                        financialItem = it.accountingName
                    )
                }
            }

        }
    }
}

@Composable
fun LabelTransactionShort(
    modifier: Modifier,
    date: String,
    type: String,
    amount: String,
    financialItem: String
) {
    val dateLocale = convertToLocalDate(date, "dd MMMM yyyy")
    val outputDateString = dateLocale.format(DateTimeFormatter.ofPattern("d/M"))

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        MMText5(text = outputDateString)
        MMText5(
            text = financialItem,
            maxLines = 1,
            modifier = modifier.weight(1f),
            overflow = TextOverflow.Ellipsis
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (type == capitalizeWords(TransactionType.CREDIT.name)) ImageVector.vectorResource(
                    id = R.drawable.cr_arrow
                )
                else ImageVector.vectorResource(id = R.drawable.db_arrow),
                contentDescription = if (type == capitalizeWords(TransactionType.CREDIT.name)) "credit"
                else "debit",
                modifier = modifier.size(10.dp),
                tint = if (type == capitalizeWords(TransactionType.CREDIT.name)) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = modifier.width(3.dp))
            MMText5(
                text = stringResource(id = R.string.rupee_symbol) + amount,
                color = if (type == capitalizeWords(TransactionType.CREDIT.name)) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary
            )
        }
    }
}