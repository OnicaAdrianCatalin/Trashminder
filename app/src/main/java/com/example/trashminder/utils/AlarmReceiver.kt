package com.example.trashminder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.trashminder.R
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val chanelName = "message_channel"
        val channelId = "message_id"
        val type= intent?.getStringExtra("type")
        val repetition= intent?.getStringExtra("repetition")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, chanelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        setRepetitiveAlarm(TrashminderNotifications(), context, type, repetition )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(type)
            .setContentText("Este timpul sa scoti gunoiul.")
        manager.notify(1, builder.build())
    }

    private fun setRepetitiveAlarm(
        trashminderNotifications: TrashminderNotifications,
        context: Context,
        type: String?,
        repetition: String?
    ) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.HOURS.toMillis(24)
        }
        trashminderNotifications.setRepetitiveAlarm(cal.timeInMillis, context, cal.timeInMillis.toInt(), type, repetition)
    }

}