package com.kanishthika.moneymatters.display.backUp

import android.accounts.Account
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes

@Composable
fun BackUpDatabaseScreen(
    modifier: Modifier,
    accountId: String,
    accountEmail: String
){
    val context = LocalContext.current
    val credential = GoogleAccountCredential.usingOAuth2(
        context, listOf(DriveScopes.DRIVE_FILE)
    )
    credential.selectedAccount = Account(accountId, accountEmail)

    val driveService = Drive.Builder(
        NetHttpTransport(),
        JacksonFactory(),
        credential
    ).setApplicationName("Money Matters").build()

   UploadDatabaseScreen(driveService = driveService, context = context )
}

@Composable
fun UploadDatabaseScreen(
    driveService: Drive,
    context: Context
) {
    val progress = remember { mutableStateOf(0) }
    val showProgress = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showProgress.value) {
            CircularProgressIndicator(
                progress = { progress.value / 100f },
                trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Uploading: ${progress.value}%")
        } else {
            Button(onClick = {
                showProgress.value = true

            }) {
                Text("Upload Database")
            }
        }
    }
}