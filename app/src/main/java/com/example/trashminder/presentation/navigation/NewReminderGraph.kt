package com.example.trashminder.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.trashminder.presentation.newReminder.NewReminderScreen

fun NavGraphBuilder.newReminderGraphBuilder(navController: NavHostController) {
    navigation(
        route = NavigationGraphs.NEWREMINDER,
        startDestination = NavigationRoute.NewReminder.route
    ) {
        composable(route = NavigationRoute.NewReminder.route) {
            NewReminderScreen(navController = navController)
        }
    }
}
