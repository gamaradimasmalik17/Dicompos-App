package com.example.compost

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object WelcomeScreen : Screen("welcome_screen")
    object RegisterScreen : Screen("register_screen")
    object ForgotPasswordScreen : Screen("forgot_password_screen")

    object AppScaffold : Screen("app_scaffold")




}