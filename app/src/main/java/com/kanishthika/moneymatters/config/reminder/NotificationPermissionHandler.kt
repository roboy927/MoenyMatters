package com.kanishthika.moneymatters.config.reminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationPermissionHandler() {
    val context = LocalContext.current
    val permissionState = remember { mutableStateOf(false) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionState.value = isGranted
    }

    // Check if the permission is already granted
    val permissionStatus = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.POST_NOTIFICATIONS
    )

    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
        permissionState.value = true
    } else {
        // Request notification permission
        SideEffect {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}