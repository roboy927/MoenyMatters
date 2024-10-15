package com.kanishthika.moneymatters.config.reminder

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.kanishthika.moneymatters.R

class NotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(
        channelID: String,
        notificationId: Int,
        notificationTitle: String,
        notificationText: String,
    ) {
        val activityIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("mm://reminders/$notificationId")
        )
        activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.bankaccount)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setContentIntent(activityPendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    companion object {
        const val TRANSACTION_NOTIFICATION_CHANNEL_ID = "transaction_channel"
        const val REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_Channel"
    }
}