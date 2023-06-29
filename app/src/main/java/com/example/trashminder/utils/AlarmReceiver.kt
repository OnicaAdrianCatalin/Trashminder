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
import com.example.trashminder.model.Reminder
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {

    private val firestore = FirebaseFirestore.getInstance()

    override fun onReceive(context: Context, intent: Intent?) {

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = CHANNEL_NAME
        val channelId = CHANNEL_ID
        val reminder = intent?.getStringExtra(REMINDER)

        val objReminder = reminder?.let { Json.decodeFromString<Reminder>(it) }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        if (!Snooze.isSnoozed) {
            setRepetitiveAlarm(Notifications(), context, objReminder)
        } else {
            Snooze.isSnoozed = false
        }

        val snoozeIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, SnoozeNotificationReceiver::class.java).apply {
                putExtra(SNOOZE_REMINDER, reminder)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        createBuilder(context, channelId, objReminder, snoozeIntent, manager)
    }

    private fun createBuilder(
        context: Context,
        channelId: String,
        objReminder: Reminder?,
        snoozeIntent: PendingIntent,
        manager: NotificationManager
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(objReminder?.type.toString())
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
        objReminder: Reminder?,
    ) {
        val cal = Calendar.getInstance().apply {
            when (objReminder?.repetition) {
                TimePeriod.WEEKLY -> this.timeInMillis =
                    timeInMillis + TimeUnit.DAYS.toMillis(7)
                TimePeriod.EVERY_TWO_WEEKS -> this.timeInMillis =
                    timeInMillis + TimeUnit.DAYS.toMillis(14)
                TimePeriod.EVERY_THREE_WEEKS -> this.timeInMillis =
                    timeInMillis + TimeUnit.DAYS.toMillis(21)
                TimePeriod.MONTHLY -> this.timeInMillis =
                    timeInMillis + getMonthDuration()
                else -> {}
            }
        }

        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE)
        val dateString = formatter.format(Date(cal.timeInMillis))

        val newReminder = objReminder?.id?.let { objReminder.copy(date = dateString, id = it + 1) }

        updateReminder(objReminder, newReminder)

        if (newReminder != null) {
            notifications.setRepetitiveAlarm(
                cal.timeInMillis,
                context,
                cal.timeInMillis.toInt(),
                newReminder
            )
        }
    }

    private fun updateReminder(
        objReminder: Reminder?,
        newReminder: Reminder?,
    ) {

        if (objReminder != null) {
            firestore.collection(collectionPath).whereArrayContains(arrayPath, objReminder).get()
                .addOnCompleteListener { task ->
                    task.apply {
                        if (task.isSuccessful) {
                            for (document in result) {
                                val docIdRef =
                                    firestore.collection(collectionPath).document(document.id)
                                updateArrayElement(docIdRef, objReminder, newReminder)
                            }
                        } else {
                            task.exception?.message?.let {
                                Log.e("Update", it)
                            }
                        }
                    }
                }
        }
    }

    private fun updateArrayElement(
        docIdRef: DocumentReference,
        objReminder: Reminder,
        newReminder: Reminder?
    ) {
        docIdRef.update(arrayPath, FieldValue.arrayRemove(objReminder))
            .addOnCompleteListener { removeTask ->
                if (removeTask.isSuccessful) {
                    docIdRef.update(
                        arrayPath,
                        FieldValue.arrayUnion(newReminder)
                    ).addOnCompleteListener { additionTask ->
                        if (!additionTask.isSuccessful) {
                            additionTask.exception?.message?.let {
                                Log.e("Update", it)
                            }
                        }
                    }
                } else {
                    removeTask.exception?.message?.let {
                        Log.e("Update", it)
                    }
                }
            }
    }

    private fun getMonthDuration(): Long {
        val cal: Calendar = Calendar.getInstance()
        val maximumDay: Int = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        return TimeUnit.DAYS.toMillis(maximumDay.toLong())
    }

    companion object {
        private const val CHANNEL_ID = "TrashMinderChannel"
        private const val CHANNEL_NAME = "TrashMinderNotifications"
        private const val REMINDER = "reminder"
        private const val SNOOZE_REMINDER = "snooze_reminder"
        private const val collectionPath = "users"
        private const val arrayPath = "reminders"

    }
}