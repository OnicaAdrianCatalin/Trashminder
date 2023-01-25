package com.example.trashminder.presentation.newReminder

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trashminder.model.ListOfReminders
import com.example.trashminder.model.Reminder
import com.example.trashminder.repository.ReminderRepository
import com.example.trashminder.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class NewReminderViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private var listOfData = emptyList<Reminder>().toMutableList()
    private val repository = ReminderRepository()
    private var reminderResponse = mutableStateOf<Response<ListOfReminders>>(Response.Loading)

    init {
        getUserData()
    }

    fun createProfileOrAddData(type: String, date: String, repetition: String) {
        val userData = Reminder(
            id = listOfData.size + 1,
            type = type,
            date = date,
            repetition = repetition
        )
        when (val reminderResponse = reminderResponse.value) {
            is Response.Loading -> Log.d("TAG", "Books: print")
            is Response.Success -> listOfData = reminderResponse.data.reminders
            is Response.Failure -> print(reminderResponse.e)
        }

        listOfData.add(userData)
        val reminders = mapOf(
            "reminders" to listOfData
        )

        auth.currentUser?.uid?.let { uid ->
            firestore.collection("users").document(uid).set(reminders).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createProfile: successful")
                } else {
                    Log.d("TAG", "createProfile: not successful")
                }
            }
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            repository.getUserData().collect { response ->
                reminderResponse.value = response
            }
        }
    }
}
