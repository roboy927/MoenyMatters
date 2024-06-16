package com.kanishthika.moneymatters.display.accounting.type.lb.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui.BorrowerListScreen
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.ui.BorrowerModel
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.ui.LenderListScreen
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.ui.LenderModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LBScreen(
    modifier: Modifier,
    lenderModel: LenderModel,
    borrowerModel: BorrowerModel,
    searchText: String
) {

    val scope = rememberCoroutineScope()
    val lbScreenList = listOf("Lenders", "Borrowers", "Loans")
    val pagerState = rememberPagerState { lbScreenList.size }
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)

    ) {
        SecondaryScrollableTabRow(
            modifier = modifier.padding(4.dp),
            containerColor = MaterialTheme.colorScheme.background,
            edgePadding = 8.dp,
            selectedTabIndex = selectedTabIndex.value,
            divider =  {},
            indicator = { }
        ) {
            lbScreenList.forEachIndexed { index, indexString ->

                Tab(
                    modifier = modifier
                        .requiredHeight(40.dp)
                        .padding(horizontal = 6.dp)
                        .border(
                            1.dp,
                            if (selectedTabIndex.value == index){MaterialTheme.colorScheme.secondary}else{MaterialTheme.colorScheme.outline},
                            RoundedCornerShape(50)
                        )
                        .clip(RoundedCornerShape(50)),
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = MaterialTheme.colorScheme.secondary,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(lbScreenList.indexOf(indexString))
                        }
                    },
                    text = { Text(text = indexString) }
                )
            }
        }
        HorizontalPager(
            state = pagerState
        ) {

            when (it) {
                0 -> LenderListScreen(
                    lenderModel = lenderModel,
                    modifier = modifier,
                    searchText = searchText
                )

                1 -> BorrowerListScreen(
                    borrowerModel = borrowerModel,
                    modifier = modifier,
                    searchText = searchText
                )

                else -> LenderListScreen(
                    lenderModel = lenderModel,
                    modifier = modifier,
                    searchText = searchText
                )
            }

        }
    }
}