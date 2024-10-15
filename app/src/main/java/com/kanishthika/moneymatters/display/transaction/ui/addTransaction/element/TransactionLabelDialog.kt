package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kanishthika.moneymatters.config.mmComposable.MMText2
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.label.ui.labelType.FlowingLabels
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.AddTransactionEvent
import com.kanishthika.moneymatters.display.transaction.ui.addTransaction.AddTransactionModel2

@Composable
fun TransactionLabelDialog(
    modifier: Modifier = Modifier,
    addTransactionModel2: AddTransactionModel2,
    closeDialog: () -> Unit
) {
    val state by addTransactionModel2.addTransactionState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.inverseSurface)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MMText2(
                    text = "Select Label",
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = {
                    addTransactionModel2.onEvent(
                        AddTransactionEvent.SetLabel(
                            Label(id = 0, "", "", 0.0, "")
                        )
                    )
                    closeDialog()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.onBackground.copy(0.8f)
                    )
                }
            }
            FlowingLabels(
                horizontalSpacing = 10.dp
            ) {
                state.labelList.forEach {
                    MMText2(
                        text = it.labelName,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                        modifier = modifier
                            .background(
                                if (it.labelName == state.selectedLabel.labelName) MaterialTheme.colorScheme.secondary.copy(
                                    0.7f
                                )
                                else Color.Transparent,
                                RoundedCornerShape(20)
                            )
                            .border(
                                0.7.dp,
                                MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(20)
                            )
                            .clickable {
                                addTransactionModel2.onEvent(
                                    AddTransactionEvent.SetLabel(
                                        it
                                    )
                                )
                                closeDialog()
                            }
                            .padding(6.dp)
                    )
                }
            }
        }
    }
}