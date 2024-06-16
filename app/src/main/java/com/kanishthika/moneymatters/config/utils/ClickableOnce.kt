package com.kanishthika.moneymatters.config.utils

import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay

fun Modifier.clickableOnce(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed(
    inspectorInfo = {
        name = "clickableOnce"
        value = onClick
    }
) {
    var enableAgain by remember { mutableStateOf(true) }
    LaunchedEffect(enableAgain, block = {
        if (enableAgain) return@LaunchedEffect
        delay(timeMillis = 500L)
        enableAgain = true
    })
    Modifier.clickable(enabled = enabled) {
        if (enableAgain) {
            enableAgain = false
            onClick()
        }
    }
}