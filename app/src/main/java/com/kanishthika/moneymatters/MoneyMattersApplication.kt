package com.kanishthika.moneymatters

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.kanishthika.moneymatters.config.reminder.NotificationService
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MoneyMattersApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val transactionChannel = NotificationChannel(
            NotificationService.TRANSACTION_NOTIFICATION_CHANNEL_ID,
            "Repeat Transaction",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        transactionChannel.description = "Remind to add Repeat Transaction"

        val reminderChannel = NotificationChannel(
            NotificationService.REMINDER_NOTIFICATION_CHANNEL_ID,
            "Reminder",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannels(listOf(transactionChannel, reminderChannel))
    }

}