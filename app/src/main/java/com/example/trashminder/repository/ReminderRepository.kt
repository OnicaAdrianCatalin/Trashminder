package com.example.trashminder.repository

import com.example.trashminder.model.ListOfReminders
import com.example.trashminder.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class ReminderRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    @Suppress("UNCHECKED_CAST")
    suspend fun getUserData() = callbackFlow {
        val snapshotListener = firestore.collection("users").document("${auth.currentUser?.uid}")
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null && snapshot.exists()) {
                    val data = snapshot.toObject(ListOfReminders::class.java)
                    Response.Success(data)
                } else {
                    Response.Failure(e)
                }
                trySend(response as Response<ListOfReminders>)
            }
        awaitClose { snapshotListener.remove() }
    }
}
