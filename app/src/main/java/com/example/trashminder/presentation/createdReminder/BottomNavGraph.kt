package com.example.trashminder.presentation.createdReminder

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trashminder.presentation.auth.login.LoginScreen
import com.example.trashminder.presentation.navigation.NavigationGraphs
import com.example.trashminder.presentation.navigation.NavigationRoute
import com.example.trashminder.presentation.navigation.authNavGraph
import com.example.trashminder.presentation.navigation.newReminderGraphBuilder

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = NavigationGraphs.MAINSCREEN,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Calendar.route) {
            CalendarScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(route = NavigationRoute.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = NavigationRoute.MainScreen.route) {
            MainScreen()
        }
        newReminderGraphBuilder(navController)
        authNavGraph(navController)
    }
}
