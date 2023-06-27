package com.example.trashminder.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity

class Notifications {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    fun setRepetitiveAlarm(
        time: Long,
        context: Context,
        id: Int,
        type: String?,
        repetition: String?,
        date: String,
        idReminder: Int
    ) {

        alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(REMINDER_TYPE, type)
            putExtra(REMINDER_REPETITION, repetition)
            putExtra(REMINDER_DATE, date)
            putExtra(REMINDER_ID, idReminder)
        }

        pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        setAlarm(time, pendingIntent)

        Log.d("Notifications", "Alarm set successfuly")
    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }
    }

    companion object {
        private const val REMINDER_TYPE = "type"
        private const val REMINDER_REPETITION = "repetition"
        private const val REMINDER_DATE = "date"
        private const val REMINDER_ID = "id"
    }
}