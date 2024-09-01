package com.example.komposapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationScreen(val route: String, val title: String,  val selectedIcon: ImageVector,val unSelectedIcon: ImageVector) {
    data object HomeScreen : NavigationScreen(
        route = "home_screen",
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home
    )


    data object ControlScreen : NavigationScreen(
        route = "control_screen",
        title = "Control",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings
    )
    data object ProfileScreen :
        NavigationScreen(route = "profile_screen", title = "Profile", selectedIcon = Icons.Filled.Person, unSelectedIcon = Icons.Outlined.Person)

}