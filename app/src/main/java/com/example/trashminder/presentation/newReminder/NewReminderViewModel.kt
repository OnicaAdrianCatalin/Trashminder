package com.example.trashminder.presentation.newReminder

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.trashminder.model.Reminder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class NewReminderViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun createProfileOrAddData(type: String, date: String, repetition: String) {
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

    companion object {
        private const val TAG = "NewReminderViewModel"
        private const val collectionPath = "users"
    }
}
