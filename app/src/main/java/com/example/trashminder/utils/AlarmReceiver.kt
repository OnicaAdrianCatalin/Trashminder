package com.example.trashminder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.trashminder.R
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
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

        setRepetitiveAlarm(Notifications(), context, type, repetition)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(type)
            .setContentText(context.getString(R.string.notification_content_text))
        manager.notify(1, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.M)
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
    }
}