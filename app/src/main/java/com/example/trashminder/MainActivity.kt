package com.example.trashminder

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import androidx.work.ExistingWorkPolicy.REPLACE

import com.example.trashminder.presentation.navigation.RootNavigationGraph
import com.example.trashminder.presentation.theme.TrashminderTheme
import com.example.trashminder.utils.*
import java.util.*
import java.util.concurrent.TimeUnit.MILLISECONDS


class MainActivity : ComponentActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            TrashminderTheme {
                RootNavigationGraph(navController = rememberNavController())

            }
        }

        fun setAlarm(time: Long, context: Context, id: Int) {

            alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)

            pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)
            alarmManager.setInexactRepeating(

                AlarmManager.RTC_WAKEUP, time, 60000,
                pendingIntent
            )

            Log.d("Main", "Alarm set successfuly")

        }

        fun createNotificationsChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name: CharSequence = "Name"
                val description = "description"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel("channel1_id", name, importance)
                channel.description = description
                val notificationManager = getSystemService(
                    NotificationManager::class.java
                )

                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
