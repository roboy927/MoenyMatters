package com.kanishthika.moneymatters.display.accounting.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.config.components.MMTopAppBar
import com.kanishthika.moneymatters.display.accounting.data.getName
import com.kanishthika.moneymatters.display.accounting.type.borrower.ui.BorrowerListScreen
import com.kanishthika.moneymatters.display.accounting.type.borrower.ui.BorrowerModel
import com.kanishthika.moneymatters.display.accounting.type.expenses.ui.ExpenseListScreen
import com.kanishthika.moneymatters.display.accounting.type.expenses.ui.ExpenseModel
import com.kanishthika.moneymatters.display.accounting.type.income.ui.IncomeListScreen
import com.kanishthika.moneymatters.display.accounting.type.income.ui.IncomeModel
import com.kanishthika.moneymatters.display.accounting.type.insurance.ui.InsuranceListScreen
import com.kanishthika.moneymatters.display.accounting.type.insurance.ui.InsuranceModel
import com.kanishthika.moneymatters.display.accounting.type.investments.ui.InvestmentListScreen
import com.kanishthika.moneymatters.display.accounting.type.investments.ui.InvestmentModel
import com.kanishthika.moneymatters.display.accounting.type.lenders.ui.LenderListScreen
import com.kanishthika.moneymatters.display.accounting.type.lenders.ui.LenderModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountingScreen(
    modifier: Modifier,
    navController: NavController,
    accountingViewModel: AccountingViewModel,
    investmentModel: InvestmentModel,
    expenseModel: ExpenseModel,
    incomeModel: IncomeModel,
    lenderModel: LenderModel,
    borrowerModel: BorrowerModel,
    insuranceModel: InsuranceModel
    ){

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { accountingViewModel.accountingScreenList.size }
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier
            .statusBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MMTopAppBar(
                titleText = "Accounting",
                scrollBehavior = scrollBehavior
            )
        }

    ) { innerPadding ->

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)

    ) {
        SecondaryScrollableTabRow(
            modifier = modifier.padding(4.dp),
            containerColor = MaterialTheme.colorScheme.background,
            edgePadding = 8.dp,
            divider =  {},
            indicator = { },
            selectedTabIndex = selectedTabIndex.value
        ) {
            accountingViewModel.accountingScreenList.forEachIndexed { index, accountingType ->
                Tab(
                    modifier = modifier
                        .requiredHeight(40.dp)
                        .padding(horizontal = 6.dp)
                        .border(
                            1.dp,
                            if (selectedTabIndex.value == index) {
                                MaterialTheme.colorScheme.secondary
                            } else {
                                MaterialTheme.colorScheme.outline
                            },
                            RoundedCornerShape(50)
                        )
                        .clip(RoundedCornerShape(50)),
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = MaterialTheme.colorScheme.secondary,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(accountingViewModel.accountingScreenList.indexOf(accountingType))
                        }
                    },
                    text = { Text(text = accountingType.getName()) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
        ) {

            when (it) {
                0 -> ExpenseListScreen( modifier = modifier, navController = navController,
                     expenseModel = expenseModel)
                1 -> InvestmentListScreen(investmentModel = investmentModel, modifier =  modifier, navController = navController)
                2 -> IncomeListScreen(navController = navController, incomeModel = incomeModel)
                3 -> InsuranceListScreen(navController = navController, insuranceModel = insuranceModel)
                4 -> {}
                5 -> BorrowerListScreen(navController = navController, borrowerModel = borrowerModel)
                6 -> LenderListScreen(navController = navController, lenderModel = lenderModel)
                else -> {}
            }

        }
    }
}
}