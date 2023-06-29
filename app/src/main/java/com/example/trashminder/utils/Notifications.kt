package com.example.trashminder.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.trashminder.model.Reminder
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Notifications {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    fun setRepetitiveAlarm(
        time: Long,
        context: Context,
        id: Int,
        reminder: Reminder,
    ) {

        val jsonReminder = Json.encodeToString(reminder)

        alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(REMINDER, jsonReminder)
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
        private const val REMINDER="reminder"
    }
}