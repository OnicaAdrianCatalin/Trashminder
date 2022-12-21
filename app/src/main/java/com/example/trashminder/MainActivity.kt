package com.example.trashminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.trashminder.presentation.createdReminder.MainScreen
import com.example.trashminder.presentation.newReminder.NewReminderScreen
import com.example.trashminder.presentation.theme.TrashminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrashminderTheme {
                //NewReminderScreen()
                MainScreen()
            }
        }
    }
}
