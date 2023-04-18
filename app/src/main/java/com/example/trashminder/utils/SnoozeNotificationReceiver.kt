package com.example.trashminder.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class SnoozeNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val type = intent?.getStringExtra(SNOOZE_TYPE)
        val repetition = intent?.getStringExtra(SNOOZE_REPETITION)

        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 10)
        val date: Date = calendar.time
        Snooze.isSnoozed = true
        Notifications().setRepetitiveAlarm(date.time, context, date.time.toInt(), type, repetition)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(1)
    }

    companion object {
        private const val SNOOZE_TYPE = "snooze_type"
        private const val SNOOZE_REPETITION = "snooze_repetition"
    }
}