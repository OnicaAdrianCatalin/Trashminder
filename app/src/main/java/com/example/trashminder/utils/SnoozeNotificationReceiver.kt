package com.example.trashminder.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.trashminder.model.Reminder
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*

class SnoozeNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val reminder = intent?.getStringExtra(SNOOZE_REMINDER)

        val objReminder = reminder?.let { Json.decodeFromString<Reminder>(it) }

        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 10)
        val date: Date = calendar.time
        Snooze.isSnoozed = true
        if (objReminder != null) {
            Notifications().setRepetitiveAlarm(
                date.time,
                context,
                date.time.toInt(),
                objReminder
            )
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(1)
    }

    companion object {
        private const val SNOOZE_REMINDER = "snooze_reminder"
    }
}