package com.example.trashminder.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.trashminder.R
import java.util.*

class TrashminderNotifications() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    fun setRepetitiveAlarm(time: Long, context: Context, id: Int, type: String?, repetition: String?) {

        alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("type", type)
            putExtra("repetition", repetition)
        }

        pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

       /* when(repetition){
            "Zilnic" -> alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time,AlarmManager.INTERVAL_DAY, pendingIntent)
            "Saptamanal" -> alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY*7, pendingIntent)
            "La 2 saptamani" -> alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, 2*6604800000, pendingIntent)
            "Lunar" -> alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, getDuration(), pendingIntent)
        }*/

        setAlarm(time, pendingIntent)

        Log.d("Main", "Alarm set successfuly")

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

    private fun getDuration(): Long {
        // get todays date
        val cal: Calendar = Calendar.getInstance()
        // get current month
        var currentMonth: Int = cal.get(Calendar.MONTH)

        // move month ahead
        currentMonth++
        // check if has not exceeded threshold of december
        if (currentMonth > Calendar.DECEMBER) {
            // alright, reset month to jan and forward year by 1 e.g fro 2013 to 2014
            currentMonth = Calendar.JANUARY
            // Move year ahead as well
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1)
        }

        // reset calendar to next month
        cal.set(Calendar.MONTH, currentMonth)
        // get the maximum possible days in this month
        val maximumDay: Int = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        // set the calendar to maximum day (e.g in case of fEB 28th, or leap 29th)
        cal.set(Calendar.DAY_OF_MONTH, maximumDay)
        return cal.timeInMillis // this is what you set as trigger point time i.e one month after
    }

    fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context){
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }

    }


    companion object{
        const val CHANNEL_ID="channel_id"
    }

}