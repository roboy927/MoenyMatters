package com.kanishthika.moneymatters.display.backUp

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kanishthika.moneymatters.config.mmComposable.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackUpDatabaseScreen(
    modifier: Modifier = Modifier,
    backUpDBViewModel: BackUpDBViewModel = hiltViewModel(),
    back: () -> Unit
){

    BackHandler {
        back()
    }
    val context = LocalContext.current

    val progress by backUpDBViewModel.uploadProgress // Observing progress state
    val message by backUpDBViewModel.uploadMessage // Observing message state

    val downloadProgress by backUpDBViewModel.downloadProgress // Observing download progress
    val downloadMessage by backUpDBViewModel.downloadMessage // Observing download message

    Scaffold(
        topBar = { MMTopAppBar(titleText = "Backup Data")}
    ) { paddingValues ->
        MMColumnScaffoldContentColumn(modifier = modifier, scaffoldPaddingValues = paddingValues) {
            Button(onClick = {
                backUpDBViewModel.uploadFileToDrive(
                    listOf(
                        File(context.getDatabasePath("AccountDB").absolutePath),
                        File(context.getDatabasePath("AccountDB-shm").absolutePath),
                        File(context.getDatabasePath("AccountDB-wal").absolutePath)
                    )
                    ,
                    onFailure = {},
                    onSuccess = {},
                    onProgress = { percentage ->


                    },
                    onComplete = { success, message -> if (success){
                        Toast.makeText(context, "Backup Done", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Backup Failed", Toast.LENGTH_LONG).show()
                    }}
                )
            }) {
                Text("Upload Database")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress Bar
                Text("Uploading files...")
                LinearProgressIndicator(
                    progress = {
                        progress / 100f // Convert progress to 0..1 range
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )
                Text("$progress%") // Show the percentage

                // Completion Message
                message?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(it, color = if (it.contains("complete")) Color.Green else Color.Red)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Button to start the download
                Button(onClick = {
                    backUpDBViewModel.downloadDatabaseFilesFromDrive(context.getDatabasePath("AccountDB").parent ?: "")
                }) {
                    Text("Download Database")
                }

                // Progress Bar
                Spacer(modifier = Modifier.height(16.dp))
                Text("Downloading files...")
                LinearProgressIndicator(
                    progress = downloadProgress / 100f, // Convert progress to 0..1 range
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
                Text("$downloadProgress%") // Show the percentage

                // Completion Message
                downloadMessage?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(it, color = if (it.contains("successful")) Color.Green else Color.Red)
                }
            }

        }
    }
}