package com.kanishthika.moneymatters.display.googleSignIn

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.kanishthika.moneymatters.config.mmComposable.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar
import com.kanishthika.moneymatters.display.backUp.GoogleViewModel
import kotlinx.coroutines.CoroutineScope
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    googleViewModel: GoogleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Launcher for Google Sign-In intent
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        googleViewModel.handleSignInResult(result.data) { account ->
            if (account != null) {
                // Handle successful sign-in
                Toast.makeText(context, "Signed in as ${account.displayName}", Toast.LENGTH_SHORT).show()
            } else {
                // Handle sign-in failure
                Toast.makeText(context, "Sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val progress by googleViewModel.uploadProgress // Observing progress state
    val message by googleViewModel.uploadMessage // Observing message state

    // Trigger upload when the screen starts



    Scaffold(
        topBar = { MMTopAppBar(titleText = "Sign In")}
    ) { paddingValues ->
        MMColumnScaffoldContentColumn(modifier = modifier, scaffoldPaddingValues = paddingValues ) {
            Column {
                Button(onClick = {
                    val signInIntent = googleViewModel.googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }) {
                    Text("Sign In with Google")
                }
                Button(onClick = {
                    googleViewModel.signOut(
                        onSuccess = {
                            Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { exception ->
                            Toast.makeText(context, "Sign-out failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }) {
                    Text("Sign Out")
                }
                Button(onClick = {
                    googleViewModel.uploadFileToDrive(
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
            }
        }
    }
}


fun accessGoogleDrive(context: Context, account: GoogleSignInAccount): Drive {
    val credential = GoogleAccountCredential.usingOAuth2(
        context, listOf(DriveScopes.DRIVE_FILE)
    )
    credential.selectedAccount = account.account

    return Drive.Builder(
        NetHttpTransport(),
        JacksonFactory(),
        credential
    ).setApplicationName("Money Matters").build()
}

fun handleSignInResult(
    context: Context,
    task: com.google.android.gms.tasks.Task<GoogleSignInAccount>,
    scope: CoroutineScope,
    onSignedIn: (GoogleSignInAccount) -> Unit
) {
    try {
        val account = task.getResult(ApiException::class.java)
        if (account != null) {
            Toast.makeText(context, "Signed in as ${account.displayName}", Toast.LENGTH_SHORT).show()
            onSignedIn(account)
        }
    } catch (e: ApiException) {
        Toast.makeText(context, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

