package com.example.trashminder.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.example.trashminder.MainActivity
import com.example.trashminder.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val chanelName = "message_channel"
        val channelId = "message_id"
        val type= intent?.getStringExtra("type")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, chanelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(type)
            .setContentText("Este timpul sa scoti gunoiul.")
        manager.notify(1, builder.build())
    }

}