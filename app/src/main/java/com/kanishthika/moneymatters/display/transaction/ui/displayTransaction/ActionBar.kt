package com.kanishthika.moneymatters.display.transaction.ui.displayTransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBar(
    modifier: Modifier,
    scope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    isFilterActive: Boolean,
    isSortByDate: Boolean,
    isSortByAmount: Boolean,
    sortByDateIcon: ImageVector,
    sortByAmountIcon: ImageVector,
    sortByDate: () -> Unit,
    sortByAmount: () -> Unit
) {

    LazyRow(
        modifier = modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ActionBarItem(
                text = "Search",
                onClick = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                },
                modifier = modifier,
                imageVector = Icons.Default.Search
            )
        }
        item {
            ActionBarItem(
                text = "Filter Transaction",
                isActive = isFilterActive,
                onClick = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                },
                modifier = modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.filter)
            )
        }
        item {
            ActionBarItem(
                modifier = modifier,
                isActive = isSortByAmount,
                text = "Sort by Amount",
                onClick = { sortByAmount()},
                imageVector = sortByAmountIcon
            )
        }
        item {
            ActionBarItem(
                modifier = modifier,
                isActive = isSortByDate,
                text = "Sort by Date",
                onClick = { sortByDate() },
                imageVector = sortByDateIcon
            )
        }
    }
}

@Composable
fun ActionBarItem(
    text: String,
    onClick: () -> Unit,
    isActive: Boolean = false,
    modifier: Modifier,
    imageVector: ImageVector
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .background(
                if (!isActive) Color.Transparent else MaterialTheme.colorScheme.tertiaryContainer.copy(
                    0.5f
                ), RoundedCornerShape(30)
            )
            .border(
                0.5.dp,
                if (!isActive) MaterialTheme.colorScheme.outline.copy(
                    0.6f
                ) else MaterialTheme.colorScheme.tertiaryContainer,
                RoundedCornerShape(30)
            )
            .padding(6.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector,
            contentDescription = "null",
            tint = MaterialTheme.colorScheme.onBackground.copy(0.6f),
            modifier = modifier.size(20.dp)
        )
        Spacer(modifier = modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
            fontSize = 14.sp
        )
    }
}




