package com.example.komposapp.bottombar

import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.compost.viewModel.BottomBarViewModel
import com.example.komposapp.navigation.NavigationHost

@Composable
fun AppScaffold(
    username: String,
    email: String,
    context: Context,
    viewModel: BottomBarViewModel,
    logout: () -> Unit
) {
    // Properly initialize the NavController using rememberNavController
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, context = context, viewModel = viewModel)
        },
        scaffoldState = scaffoldState,
    ) {
        NavigationHost(
            navController = navController,
            username = username,
            email = email,
            logout = logout
        )
    }
}
