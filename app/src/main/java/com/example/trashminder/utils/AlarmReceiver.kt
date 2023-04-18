package com.example.trashminder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.trashminder.R
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = CHANNEL_NAME
        val channelId = CHANNEL_ID
        val type = intent?.getStringExtra(REMINDER_TYPE)
        val repetition = intent?.getStringExtra(REMINDER_REPETITION)

        Log.d("Notifications", "type= $type, repetition= $repetition")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        if (!Snooze.isSnoozed) {
            setRepetitiveAlarm(Notifications(), context, type, repetition)
        } else {
            Snooze.isSnoozed = false
        }

        val snoozeIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, SnoozeNotificationReceiver::class.java).apply {
                putExtra(SNOOZE_TYPE, type)
                putExtra(SNOOZE_REPETITION, repetition)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(type)
            .setContentText(context.getString(R.string.notification_content_text))
            .addAction(
                R.drawable.ic_baseline_snooze_24,
                "Snooze for 10 minutes",
                snoozeIntent
            )
        manager.notify(1, builder.build())
    }

    private fun setRepetitiveAlarm(
        notifications: Notifications,
        context: Context,
        type: String?,
        repetition: String?
    ) {
        val cal = Calendar.getInstance().apply {
            when (repetition) {
                TimePeriod.WEEKLY.name -> this.timeInMillis =
                    timeInMillis + TimeUnit.DAYS.toMillis(7)
                TimePeriod.EVERY_TWO_WEEKS.name -> this.timeInMillis =
                    timeInMillis + TimeUnit.DAYS.toMillis(14)
                TimePeriod.EVERY_THREE_WEEKS.name -> this.timeInMillis =
                    timeInMillis + TimeUnit.DAYS.toMillis(21)
                TimePeriod.MONTHLY.name -> this.timeInMillis =
                    timeInMillis + getMonthDuration()
            }
        }

        notifications.setRepetitiveAlarm(
            cal.timeInMillis,
            context,
            cal.timeInMillis.toInt(),
            type,
            repetition
        )
    }

    private fun getMonthDuration(): Long {
        val cal: Calendar = Calendar.getInstance()
        val maximumDay: Int = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        return TimeUnit.DAYS.toMillis(maximumDay.toLong())
    }

    companion object {
        private const val CHANNEL_ID = "TrashMinderChannel"
        private const val CHANNEL_NAME = "TrashMinderNotifications"
        private const val REMINDER_TYPE = "type"
        private const val REMINDER_REPETITION = "repetition"
        private const val SNOOZE_TYPE = "snooze_type"
        private const val SNOOZE_REPETITION = "snooze_repetition"
    }
}