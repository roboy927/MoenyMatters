package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.components.MMActionBarItem
import com.kanishthika.moneymatters.config.utils.clickableOnce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBar(
    modifier: Modifier,
    scope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    isFilterActive: Boolean,
    isAllTransactionsActive: Boolean,
    sortByDate: () -> Unit,
    sortByAmount: () -> Unit,
    sortByDateState: Int,
    sortByAmountState: Int,
    onClickAllTransactions: () -> Unit,
    onSearch: ()-> Unit
) {
    @Composable
    fun sortingIcon(sortState: Int) : ImageVector {
        return when (sortState) {
            1 -> ImageVector.vectorResource(id = R.drawable.ascending)
            2 -> ImageVector.vectorResource(id = R.drawable.descending)
            else -> ImageVector.vectorResource(id = R.drawable.sort)
        }
    }
    


    LazyRow(
        modifier = modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            MMActionBarItem(
                text = "Search",
                click = Modifier.clickableOnce{
                    onSearch()
                },
                modifier = modifier,
                imageVector = Icons.Default.Search
            )
        }
        item {
            MMActionBarItem(
                text = "All Transactions",
                isActive = isAllTransactionsActive,
                click = Modifier.clickableOnce{
                    onClickAllTransactions()
                },
                modifier = modifier,
                imageVector = Icons.Default.Menu
            )
        }
        item {
            MMActionBarItem(
                text = "Filter",
                isActive = isFilterActive,
                click = Modifier.clickableOnce{
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                },
                modifier = modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.filter)
            )
        }
        item {
            MMActionBarItem(
                modifier = modifier,
                isActive = sortByAmountState == 1 || sortByAmountState == 2,
                text = "Sort by Amount",
                click = Modifier.clickable{ sortByAmount()},
                imageVector = sortingIcon(sortState = sortByAmountState)
            )
        }
        item {
            MMActionBarItem(
                modifier = modifier,
                isActive = sortByDateState == 1 || sortByDateState == 2,
                text = "Sort by Date",
                click = Modifier.clickable { sortByDate() },
                imageVector = sortingIcon(sortState = sortByDateState)
            )
        }
    }
}



