package com.example.komposapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.compost.ui.screens.control.ControlScreen
import com.example.compost.ui.screens.home.HomeScreen
import com.example.compost.ui.screens.profile.ProfileScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    username: String,
    email: String,
    logout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.HomeScreen.route
    ) {
        composable(NavigationScreen.HomeScreen.route) {
            HomeScreen(navController, username)
        }
        composable(NavigationScreen.ControlScreen.route) {
            ControlScreen(navController, username)
        }
        composable(NavigationScreen.ProfileScreen.route) {
            ProfileScreen(navController, username, email) {
                logout()
            }
        }
    }
}
