package com.kanishthika.moneymatters.display.accounting.ui.financialGenerics

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem
import com.kanishthika.moneymatters.display.accounting.ui.element.DropDownItem

@Composable
fun <T : FinancialItem> FinancialItemDetailBox(
    modifier: Modifier,
    item: T,
    itemAmount: String,
    onItemClick: (DropDownItem) -> Unit,
    dropdownItems: List<DropDownItem>,
    itemBlur: Dp,
    onLongPress: () -> Unit,
    onDismiss: () -> Unit
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .blur(itemBlur)
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        isContextMenuVisible = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        onLongPress()
                    },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = modifier.weight(2f)) {
                    Text(
                        text = capitalizeWords(item.name),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = modifier.height(4.dp))
                    LimitWordDescription(text = capitalizeWords(item.description), modifier)
                }
                Spacer(modifier = modifier.width(50.dp))
                Box(contentAlignment = Alignment.TopEnd) {
                    Text(
                        text = stringResource(id = R.string.rupee_symbol) + " " + itemAmount,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
                onDismiss()
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropdownItems.forEach {
                DropdownMenuItem(text = { Text(text = it.text) },
                    onClick = {
                        onItemClick(it)
                        isContextMenuVisible = false
                    }
                )
            }
        }
    }
}

@Composable
fun LimitWordDescription(
    text: String, modifier: Modifier
) {
    var fullText by remember {
        mutableStateOf(false)
    }
    Text(text = text,
        style = MaterialTheme.typography.bodySmall,
        color = if (!fullText) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier.clickable {
            fullText = !fullText
        },
        maxLines = if (fullText) Int.MAX_VALUE else 1
    )
}