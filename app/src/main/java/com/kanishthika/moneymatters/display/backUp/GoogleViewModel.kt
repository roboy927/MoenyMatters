package com.kanishthika.moneymatters.display.backUp

import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.api.client.http.InputStreamContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleViewModel @Inject constructor(
    val googleSignInClient: GoogleSignInClient,
   private val driveService: Drive
) : ViewModel() {

    var uploadProgress = mutableStateOf(0) // Progress percentage
        private set
    var uploadMessage = mutableStateOf<String?>(null) // Success or failure message
        private set

    fun handleSignInResult(data: Intent?, onResult: (GoogleSignInAccount?) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            onResult(account)
        } catch (e: ApiException) {
            onResult(null)
        }
    }

    fun signOut(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        googleSignInClient.signOut()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    task.exception?.let(onFailure)
                }
            }
    }

    fun uploadFileToDrive(fileList: List<java.io.File>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit,
                          onProgress: (Int) -> Unit,
                          onComplete: (Boolean, String?) -> Unit) {
        val folderName = "MMBackup"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Step 1: Check if the folder "MMBackUp" exists
                val folder = driveService.files().list()
                    .setQ("mimeType = 'application/vnd.google-apps.folder' and name = '$folderName'")
                    .setSpaces("drive")
                    .setFields("files(id, name)")
                    .execute()
                    .files
                    .firstOrNull()

                // Step 2: Create the folder if it doesn't exist
                val folderId = folder?.id ?: run {
                    val folderMetadata = com.google.api.services.drive.model.File().apply {
                        name = folderName
                        mimeType = "application/vnd.google-apps.folder"
                    }
                    val createdFolder = driveService.files().create(folderMetadata)
                        .setFields("id")
                        .execute()
                    createdFolder.id
                }

                fileList.forEachIndexed { index, file ->
                    try {
                        // Prepare the file content
                        val fileContent = InputStreamContent(
                            "application/octet-stream",
                            file.inputStream()
                        )

                        // Check if the file already exists in the folder
                        val existingFile = driveService.files().list()
                            .setQ("name = '${file.name}' and '$folderId' in parents")
                            .setSpaces("drive")
                            .setFields("files(id, name)")
                            .execute()
                            .files
                            .firstOrNull()

                        if (existingFile != null) {
                            // Update the existing file
                            driveService.files().update(existingFile.id, null, fileContent).execute()
                        } else {
                            // Create a new file
                            val fileMetadata = File().apply {
                                name = file.name
                                parents = listOf(folderId)
                            }
                            driveService.files().create(fileMetadata, fileContent).setFields("id").execute()
                        }

                        // Update progress
                        val progress = ((index + 1) * 100) / fileList.size
                        uploadProgress.value = progress
                    } catch (fileException: Exception) {
                        fileException.printStackTrace()
                        // Continue to the next file
                    }
                }

                // Notify completion
                uploadMessage.value = "Upload complete!"
            } catch (e: Exception) {
                e.printStackTrace()
                uploadMessage.value = "Upload failed: ${e.message}"
            }
        }.start()
    }
}
