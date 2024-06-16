package com.kanishthika.moneymatters.display.transaction.ui.addTransaction

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanishthika.moneymatters.display.transaction.data.TransactionType


fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

@Composable
fun TextSwitch(
    modifier: Modifier,
    selectedTxnType: TransactionType,
    onSelectionChange: (TransactionType) -> Unit,

    ) {

    val textColor = MaterialTheme.colorScheme.onSecondary
    val transactionType = TransactionType.values()
    val selectedTxnTypeInt: Int = selectedTxnType.ordinal

    val roundRectColor = if (selectedTxnType == TransactionType.CREDIT) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }


    BoxWithConstraints(
        modifier
            .border(1.dp, color = roundRectColor, RoundedCornerShape(50))
            .height(56.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.background)

    ) {


        val maxWidth = this.maxWidth
        val tabWidth = maxWidth / transactionType.size

        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * selectedTxnTypeInt,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "indicator offset"
        )


        Row(modifier = Modifier
            .fillMaxWidth()

            .drawWithContent {

                // This is for setting black tex while drawing on white background
                val padding = 8.dp.toPx()
                drawRoundRect(
                    topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                    size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                    color = textColor,
                    cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                )

                drawWithLayer {
                    drawContent()

                    // This is white top rounded rectangle
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                        size = Size(size.width / 2, size.height),
                        color = roundRectColor,
                        cornerRadius = CornerRadius(x = 100F, y = 100F),
                        blendMode = BlendMode.SrcOut
                    )
                }

            }
        ) {

            transactionType.forEachIndexed { _, transactionType ->
                Box(
                    modifier = Modifier
                        .width(tabWidth)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null,
                            onClick = {
                                onSelectionChange(transactionType)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = transactionType.name,
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                }
            }
        }

    }
}