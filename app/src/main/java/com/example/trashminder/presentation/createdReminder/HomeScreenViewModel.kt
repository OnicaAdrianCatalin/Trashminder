package com.example.trashminder.presentation.createdReminder

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.trashminder.model.ListOfReminders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeScreenViewModel : ViewModel() {
    val reminderResponse = mutableStateOf<ListOfReminders?>(null)
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        getUserData()
    }

    private fun getUserData() {
        firestore.collection(collectionPath).document("${auth.currentUser?.uid}")
            .addSnapshotListener { snapshot, e ->
                if ((snapshot != null) && snapshot.exists()) {
                    val data = snapshot.toObject(ListOfReminders::class.java)
                    reminderResponse.value = data
                }
            }
    }

    companion object {
        private const val collectionPath = "users"
    }
}
