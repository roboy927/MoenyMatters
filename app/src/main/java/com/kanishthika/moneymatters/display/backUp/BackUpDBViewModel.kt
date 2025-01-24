package com.kanishthika.moneymatters.display.backUp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.client.http.InputStreamContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackUpDBViewModel @Inject constructor(
    private val driveService: Drive
): ViewModel() {

    var uploadProgress = mutableStateOf(0) // Progress percentage
        private set
    var uploadMessage = mutableStateOf<String?>(null) // Success or failure message
        private set

    var downloadProgress = mutableStateOf(0) // Progress percentage
        private set
    var downloadMessage = mutableStateOf<String?>(null) // Success or failure message
        private set


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

    fun downloadDatabaseFilesFromDrive(
        localDatabaseDir: String, // Directory where the database files are stored
        folderName: String = "MMBackup",
        fileNames: List<String> = listOf("AccountDB", "AccountDB-shm", "AccountDB-wal")
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Step 1: Locate the folder on Google Drive
                val folder = driveService.files().list()
                    .setQ("mimeType = 'application/vnd.google-apps.folder' and name = '$folderName'")
                    .setSpaces("drive")
                    .setFields("files(id, name)")
                    .execute()
                    .files
                    .firstOrNull() ?: throw Exception("Folder '$folderName' not found on Google Drive.")

                fileNames.forEachIndexed { index, fileName ->
                    try {
                        // Step 2: Locate each file in the folder
                        val file = driveService.files().list()
                            .setQ("name = '$fileName' and '${folder.id}' in parents")
                            .setSpaces("drive")
                            .setFields("files(id, name)")
                            .execute()
                            .files
                            .firstOrNull() ?: throw Exception("File '$fileName' not found in folder '$folderName'.")

                        // Step 3: Download the file content
                        val localFilePath = "$localDatabaseDir/$fileName"
                        val outputStream = java.io.File(localFilePath).outputStream()
                        driveService.files().get(file.id).executeMediaAndDownloadTo(outputStream)

                        // Update progress
                        val progress = ((index + 1) * 100) / fileNames.size
                        downloadProgress.value = progress
                    } catch (fileException: Exception) {
                        fileException.printStackTrace()
                        downloadMessage.value = "Failed to download $fileName: ${fileException.message}"
                    }
                }

                // Step 4: Notify success
                downloadMessage.value = "All database files downloaded and replaced successfully!"
            } catch (e: Exception) {
                e.printStackTrace()
                downloadMessage.value = "Database files download failed: ${e.message}"
            }
        }
    }
}