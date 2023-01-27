package com.example.trashminder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trashminder.presentation.createdReminder.MainScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = NavigationGraphs.ROOT,
        startDestination = NavigationGraphs.AUTHENTICATION
    ) {
        newReminderGraphBuilder(navController)
        authNavGraph(navController)
        composable(route = NavigationGraphs.MAINSCREEN) {
            MainScreen()
        }
    }
}
