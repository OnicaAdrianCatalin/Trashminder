package com.example.trashminder.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.trashminder.presentation.auth.login.LoginScreen
import com.example.trashminder.presentation.auth.signup.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(route = NavigationGraphs.AUTHENTICATION, startDestination = NavigationRoute.Login.route){
        composable(NavigationRoute.Login.route){
            LoginScreen(navController = navController)
        }
        composable(NavigationRoute.SignUp.route){
            SignUpScreen(navController = navController)
        }
    }
}
