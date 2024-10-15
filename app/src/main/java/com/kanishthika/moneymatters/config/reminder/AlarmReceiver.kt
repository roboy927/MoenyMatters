package com.kanishthika.moneymatters.config.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TAG", "onReceive: Alarm Notify")
        val notificationService = NotificationService(context)

        notificationService.showNotification(
            channelID = NotificationService.REMINDER_NOTIFICATION_CHANNEL_ID,
            notificationId = intent.getIntExtra("notificationId", 0),
            notificationTitle = intent.getStringExtra("reminderTitle") ?: "Reminder",
            notificationText = "Just Try"
        )
    }
}


