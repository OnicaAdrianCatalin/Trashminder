package com.example.trashminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.trashminder.presentation.navigation.RootNavigationGraph
import com.example.trashminder.presentation.theme.TrashminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrashminderTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}
