package com.kanishthika.moneymatters.config.reminder


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderAlarmManager @Inject constructor(@ApplicationContext private val context: Context){

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun createPendingIntent(context: Context, reminderTitle: String = "Null", reminderId:Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("reminderTitle", reminderTitle)
            putExtra("notificationId", reminderId)
        }
        return PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm(reminderTitle: String, reminderId:Int, timeMillis: Long){
        val pendingIntent = createPendingIntent(context, reminderTitle, reminderId)
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeMillis,
            pendingIntent,
        )
    }

    fun cancelAlarm(reminderId: Int){
        val pendingIntent = createPendingIntent(context, reminderId = reminderId)
        alarmManager.cancel(pendingIntent)
    }

}