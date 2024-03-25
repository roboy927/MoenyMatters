package com.kanishthika.moneymatters.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kanishthika.moneymatters.dashboard.data.MenuItem

@Composable
fun Menu(
    menuItems: List<MenuItem>,
    navController: NavController
) {
    LazyVerticalGrid(columns = GridCells.Fixed(4))
    {
        itemsIndexed(menuItems) { index, menuItem ->
            MenuItemView(menuItem = menuItem) {
                navController.navigate(menuItem.route)
            }
        }
    }
}

@Composable
fun MenuItemView(menuItem: MenuItem, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .size(70.dp)
                .padding(10.dp, 8.dp, 10.dp, 0.dp),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(15.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(
                menuItem.icon,
                menuItem.label,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 4.dp),
            text = menuItem.label,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}