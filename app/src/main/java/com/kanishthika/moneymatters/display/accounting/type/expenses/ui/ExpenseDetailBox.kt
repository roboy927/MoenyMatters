package com.kanishthika.moneymatters.display.accounting.type.expenses.ui

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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.kanishthika.moneymatters.R
import com.kanishthika.moneymatters.config.utils.capitalizeWords
import com.kanishthika.moneymatters.display.accounting.ui.element.DropDownItem


@Composable
fun ExpenseDetailBox(
    modifier: Modifier, expenseName: String, expenseAmount: String, expenseDescription: String,
    onItemClick: (DropDownItem) -> Unit,
    dropdownItems: List<DropDownItem>
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
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        isContextMenuVisible = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }
    ) {

        Column(
            modifier = modifier.padding(12.dp),
        ) {
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = modifier.weight(2f)) {
                    Text(
                        text = capitalizeWords(expenseName),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = modifier.height(2.dp))
                    LimitWordDescription(text = capitalizeWords(expenseDescription), modifier)
                }

                Box(contentAlignment = Alignment.TopEnd) {
                    Text(
                        text = stringResource(id = R.string.rupee_symbol) + " " + expenseAmount,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropdownItems.forEach {
                DropdownMenuItem(text = { Text(text = it.text)},
                    onClick = {
                        onItemClick (it)
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
    val words = text.trim().split("\\s+".toRegex())
    val displayText = if (words.size > 4) {
        words.take(4).joinToString(" ") + "..."
    } else {
        text
    }
    Text(text = if (!fullText) displayText else text,
        style = if (!fullText) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.bodyMedium,
        color = if (!fullText) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier.clickable {
            fullText = !fullText
        })
}
