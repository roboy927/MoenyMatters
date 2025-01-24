package com.kanishthika.moneymatters.display.dashboard.ui.element

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.display.dashboard.data.ClickableItem

@Composable
fun Menu(
    clickableItems: List<ClickableItem>,
    navigateTo:(String)-> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        itemsIndexed(clickableItems) { _, menuItem ->
            MenuItemView(clickableItem = menuItem) {
                navigateTo(menuItem.route)
            }
        }
    }
}

@Composable
fun MenuItemView(clickableItem: ClickableItem, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.size(70.dp),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(22.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.5f),
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(
                clickableItem.icon,
                clickableItem.label,
                modifier = Modifier
                    .fillMaxSize(),
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.7f)
            )
        }
        Text(
            modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 4.dp),
            text = clickableItem.label,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}