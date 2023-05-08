package com.example.trashminder.presentation.newReminder

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.trashminder.model.Reminder
import com.example.trashminder.utils.TimePeriod
import com.example.trashminder.utils.TrashType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*

class NewReminderViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minutes = calendar.get(Calendar.MINUTE)
    var date = ""
    var time = ""

    fun createProfileOrAddData(type: TrashType, date: String, repetition: TimePeriod) {
        val userData = Reminder(
            type = type,
            date = date,
            repetition = repetition
        )

        val reminders = mapOf(
            "reminders" to FieldValue.arrayUnion(userData)
        )
        auth.currentUser?.uid?.let { uid ->
            firestore.collection(collectionPath).document(uid).set(reminders, SetOptions.merge())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createProfileOrAddData: the data was set or updated")
                    } else {
                        Log.d(TAG, "createProfileOrAddData: the set or update process failed")
                    }
                }
        }
    }

    fun setDateCorrectFormat(
        selectedMonth: Int,
        selectedDayOfMonth: Int,
        selectedYear: Int
    ): String {
        var newDay = "$selectedDayOfMonth"
        var newMonth = "${selectedMonth + 1}"
        if (selectedDayOfMonth < 10) {
            newDay = "0$selectedDayOfMonth"
        }
        if (selectedMonth < 10) {
            newMonth = "0${selectedMonth + 1}"
        }

        return "$newDay/$newMonth/$selectedYear"
    }

    fun setTimeCorrectFormat(selectedHour: Int, selectedMinute: Int): String {
        var newHour = "$selectedHour"
        var newMinutes = "$selectedMinute"

        if (selectedHour < 10) {
            newHour = "0$selectedHour"
        }
        if (selectedMinute < 10) {
            newMinutes = "0$selectedMinute"
        }

        return " $newHour:$newMinutes"
    }

    companion object {
        private const val TAG = "NewReminderViewModel"
        private const val collectionPath = "users"
    }
}
