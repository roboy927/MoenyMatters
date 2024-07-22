package com.kanishthika.moneymatters.display.dashboard.ui.element

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardTransactionView(
    modifier: Modifier,
    list: List<Transaction>,
    navController: NavController,
    scope: CoroutineScope
) {
    val pagerState = rememberPagerState { 3 }
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val tabList = listOf("Recent", "Upcoming", "Starred")

    Column{
        SecondaryTabRow(
            containerColor = MaterialTheme.colorScheme.background,
            selectedTabIndex = selectedTabIndex.value,
            divider = {},
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(selectedTabIndex.value, matchContentSize = false),
                    color = MaterialTheme.colorScheme.outline,
                    height = 2.dp
                )
            }
        ) {
            tabList.forEachIndexed { index, tabName ->
                Tab(
                    modifier = modifier
                        .requiredHeight(40.dp),
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = MaterialTheme.colorScheme.onBackground,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = tabName) }
                )
            }
        }
        Spacer(modifier = modifier.height(8.dp))

        HorizontalPager(state = pagerState) {
            when (it) {
                0 -> RecentTransactionList(
                    modifier = modifier,
                    list = list,
                    navController = navController
                )
            }
        }
    }
}

