package com.kanishthika.moneymatters.config.mmComposable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MMText3(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.titleSmall
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = style,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun MMText2(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.titleMedium
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = style
    )
}

@Composable
fun MMText5(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = style,
        maxLines = maxLines,
        overflow = overflow
    )
}