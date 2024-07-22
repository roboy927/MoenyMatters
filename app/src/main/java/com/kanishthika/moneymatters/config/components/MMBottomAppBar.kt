package com.kanishthika.moneymatters.config.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.config.utils.clickableOnce

@Composable
fun MMBottomAppBarButton(
    bottomBarText: String,
    enabled: Boolean,
    modifier: Modifier,
    onBottomBarClick: () -> Unit
) {
    BottomAppBar(
        containerColor = if (enabled)
            MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.tertiaryContainer.copy(
            0.5f
        ),
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .clickableOnce(
                enabled = enabled
            ) {
                onBottomBarClick()
            }
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bottomBarText,
                color = if (enabled)
                    MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onTertiaryContainer.copy(
                    0.5f
                ),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}