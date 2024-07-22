package com.kanishthika.moneymatters.display.accounting.ui.element

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kanishthika.moneymatters.config.components.MMSimpleLAlertDialog

@Composable
fun ConfirmDeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: @Composable (()-> Unit)? = null,
    confirmEnable: Boolean
) {
    MMSimpleLAlertDialog(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        confirmText = "Delete",
        dismissText = "Cancel",
        title = { Text(text = title) },
        text = text,
        confirmEnable = confirmEnable
    )
}