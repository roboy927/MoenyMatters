package com.kanishthika.moneymatters.config.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalLineWithCenteredText(modifier: Modifier, text: String) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            modifier = modifier
                .fillMaxWidth(0.8f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            modifier = modifier.background(MaterialTheme.colorScheme.background).padding(4.dp, 0.dp),
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge
        )
    }
}