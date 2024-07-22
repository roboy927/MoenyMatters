package com.kanishthika.moneymatters.config.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kanishthika.moneymatters.config.theme.MoneyMattersTheme

@Composable
fun MMSimpleLAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String,
    dismissText: String,
    title:  @Composable() (() -> Unit)? = null,
    text: @Composable() (() -> Unit)? = null,
    confirmEnable: Boolean = true

) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.outline.copy(0.7f),
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = confirmEnable
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        },
        title = title,
        text = text

    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAlert() {
    MoneyMattersTheme {
        MMSimpleLAlertDialog(
            onDismiss = {  },
            onConfirm = {  },
            confirmText = "Confirm",
            dismissText = "Dismiss",
            title = null,
            text = { Text(text = "Are You Sure?") },
            confirmEnable = false
        )
    }
}


