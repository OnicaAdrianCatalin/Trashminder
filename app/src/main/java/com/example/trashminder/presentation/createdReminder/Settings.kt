package com.example.trashminder.presentation.createdReminder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.trashminder.presentation.navigation.NavigationGraphs
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SettingsScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            auth.signOut()
            navController.navigate(NavigationGraphs.AUTHENTICATION){
                popUpTo(NavigationGraphs.MAINSCREEN){
                    inclusive = true
                }
            }
        }) {
            Text(text = "LogOut")
        }
    }
}
