package com.kanishthika.moneymatters.config.database

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun backupDatabase(context: Context, databaseName: String, backupDir: File): Boolean {
    return try {
        val dbFile = context.getDatabasePath(databaseName)
        if (!dbFile.exists()) {
            throw IllegalStateException("Database file not found!")
        }

        if (!backupDir.exists()) {
            backupDir.mkdirs()
        }

        val backupFile = File(backupDir, "$databaseName-backup-${System.currentTimeMillis()}.db")
        FileInputStream(dbFile).use { input ->
            FileOutputStream(backupFile).use { output ->
                input.copyTo(output)
            }
        }
        true // Backup successful
    } catch (e: Exception) {
        e.printStackTrace()
        false // Backup failed
    }
}
