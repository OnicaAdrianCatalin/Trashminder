package com.example.trashminder.presentation.navigation

sealed class NavigationRoute(val route: String){
    object Login : NavigationRoute("login_screen")
    object SignUp : NavigationRoute("signup_screen")
    object NewReminder : NavigationRoute("new_reminder_screen")
    object MainScreen : NavigationRoute("main_screen")
    object HomeScreen : NavigationRoute("home_screen")
    object Settings : NavigationRoute("settings_screen")
}
