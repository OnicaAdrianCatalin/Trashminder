package com.example.trashminder.presentation.createdReminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trashminder.presentation.navigation.NavigationRoute
import com.example.trashminder.presentation.theme.bleu
import com.example.trashminder.presentation.theme.darkerGreen
import com.example.trashminder.presentation.theme.lightGreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.route != NavigationRoute.NewReminder.route) {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    modifier = Modifier.background(
                        brush = Brush.verticalGradient(
                            listOf(lightGreen, darkerGreen)
                        )
                    )
                ) {}}
        },
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            BottomNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Calendar,
        BottomBarScreen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier.background(
                brush = Brush.verticalGradient(
                    listOf(darkerGreen, bleu)
                )
            )
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "Navigation icon")
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
            }
        }
    )
}
