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
        val chanelName = "message_channel"
        val channelId = "message_id"
        val type = intent?.getStringExtra("type")
        val repetition = intent?.getStringExtra("repetition")

        Log.d("Notifications", "type= $type, repetition= $repetition")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, chanelName, NotificationManager.IMPORTANCE_DEFAULT)
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
                TimePeriod.DAILY.name -> this.timeInMillis =
                    timeInMillis + TimeUnit.HOURS.toMillis(24)
                TimePeriod.WEEKLY.name -> this.timeInMillis =
                    timeInMillis + TimeUnit.DAYS.toMillis(7)
                TimePeriod.EVERY_TWO_WEEKS.name -> this.timeInMillis =
                    timeInMillis + TimeUnit.DAYS.toMillis(14)
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
        var daysInMillis = 0L
        when (maximumDay) {
            28 -> daysInMillis = TimeUnit.DAYS.toMillis(28)
            29 -> daysInMillis = TimeUnit.DAYS.toMillis(29)
            30 -> daysInMillis = TimeUnit.DAYS.toMillis(30)
            31 -> daysInMillis = TimeUnit.DAYS.toMillis(31)
        }
        return daysInMillis
    }
}
