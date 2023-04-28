package com.example.trashminder.presentation.createdReminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import com.example.trashminder.model.ListOfReminders
import com.example.trashminder.presentation.theme.greenTrash
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeScreenViewModel : ViewModel() {
    val reminderResponse = mutableStateOf<ListOfReminders?>(null)
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var showDialog by mutableStateOf(false)


    init {
        showDialog = true
        getUserData()
    }

    private fun getUserData() {
        firestore.collection(collectionPath).document("${auth.currentUser?.uid}")
            .addSnapshotListener { snapshot, e ->
                if ((snapshot != null) && snapshot.exists()) {
                    val data = snapshot.toObject(ListOfReminders::class.java)
                    reminderResponse.value = data
                    showDialog = false
                }
            }
    }

    @Composable
    fun ProgressDialog() {
        if (showDialog) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(color = greenTrash)
            }
        }
    }

    companion object {
        private const val collectionPath = "users"
    }
}
