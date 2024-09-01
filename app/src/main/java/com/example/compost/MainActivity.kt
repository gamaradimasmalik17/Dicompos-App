package com.example.compost

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compost.ui.screens.login.ForgotPasswordScreen
import com.example.compost.ui.screens.login.LoginScreen
import com.example.compost.ui.screens.login.RegisterScreen
import com.example.compost.ui.screens.welcome.WelcomeScreen
import com.example.compost.ui.theme.CompostTheme
import com.example.compost.viewModel.BottomBarViewModel
import com.example.compost.viewModel.LoginViewModel
import com.example.komposapp.bottombar.AppScaffold
import org.koin.androidx.compose.getViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.Blue.toArgb()
        setContent {

            CompostTheme {
                // A surface container using the 'background' color from the theme
                LoginApplication()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CompostTheme {
        Greeting("Android")
    }
}
@Composable
fun SetStatusBarColor(color: Color) {
    // Get the context
    val context = LocalContext.current
    val window = (context as? ComponentActivity)?.window
    window?.statusBarColor = color.toArgb()
    window?.decorView?.systemUiVisibility = 0
}
@Composable
fun LoginApplication() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = getViewModel()
    val bottomBarViewModel: BottomBarViewModel = getViewModel()

    NavHost(navController = navController, startDestination = Screen.WelcomeScreen.route) {
        composable(route = Screen.WelcomeScreen.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable(route = Screen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(
            route = Screen.AppScaffold.route + "/{username}/{email}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            AppScaffold(
                username = username,
                email = email,
                context = LocalContext.current,
                viewModel = bottomBarViewModel
            ) {
                // When logging out, navigate back to the login screen and clear the back stack
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}