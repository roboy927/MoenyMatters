package com.kanishthika.moneymatters.config.mmComposable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MMActionBarItem(
    text: String,
    isActive: Boolean = false,
    modifier: Modifier,
    border: Modifier = Modifier.border(0.5.dp,
        if (!isActive) MaterialTheme.colorScheme.outline.copy(
            0.6f
        ) else MaterialTheme.colorScheme.tertiaryContainer,
        RoundedCornerShape(30)),
    click: Modifier,
    imageVector: ImageVector,
    iconSize: Dp = 20.dp,
    fontSize: TextUnit = 14.sp,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(0.6f) ,

    ) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(30))
            .background(
                if (!isActive) Color.Transparent else MaterialTheme.colorScheme.tertiaryContainer.copy(
                    0.5f
                ), RoundedCornerShape(30)
            )
            .then(border)
            .then(click)
            .padding(6.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector,
            contentDescription = "null",
            tint = color,
            modifier = modifier.size(iconSize)
        )
        Spacer(modifier = modifier.width(4.dp))
        Text(
            text = text,
            color = color,
            fontSize = fontSize
        )
    }
}