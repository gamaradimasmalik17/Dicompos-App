package com.example.compost.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.compost.R
import com.example.compost.Screen
import com.example.compost.ui.theme.Green40
import com.example.compost.ui.theme.GreenGrey40
import com.example.compost.viewModel.LoginState
import com.example.compost.viewModel.LoginViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = getViewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val loginState by loginViewModel.loginState.collectAsState()

    IconButton(
        onClick = { navController.popBackStack() }, // Kembali ke halaman sebelumnya
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
    }
    ConstraintLayout(
        modifier = Modifier
    ) {
        val screenSize = LocalConfiguration.current.screenWidthDp.dp
        val (logo, form, forgotPassword, loginButton, registerText) = createRefs()
        Spacer(modifier = Modifier.height(32.dp))
        LogoSection(modifier = Modifier.constrainAs(logo) {
            top.linkTo(parent.top, margin = 70.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, horizontalAlignment = Alignment.CenterHorizontally)

        LoginForm(
            loginViewModel = loginViewModel,
            username = username,
            password = password,
            isPasswordVisible = isPasswordVisible,
            onUsernameChange = { username = it },
            onPasswordChange = { password = it },
            onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
            loginState = loginState,
            modifier = Modifier.constrainAs(form) {
                top.linkTo(logo.bottom, margin = 40.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                if (screenSize >= 600.dp) {
                    width = Dimension.fillToConstraints
                    start.linkTo(parent.start, margin = 64.dp)
                    end.linkTo(parent.end, margin = 64.dp)
                }
            },
            navController
        )

        RegisterSection(navController,modifier = Modifier.constrainAs(registerText) {
            top.linkTo(form.bottom, margin = 30.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        })


    }
}

@Composable
fun LogoSection(modifier: Modifier = Modifier, horizontalAlignment: Alignment.Horizontal) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.logo_sementara),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp)) // Jarak antara logo dan teks
        Text(
            text = "Dicompos",
            style = TextStyle(
                color = Green40,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun LoginForm(
    loginViewModel: LoginViewModel,
    username: String,
    password: String,
    isPasswordVisible: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    loginState: LoginState,
    modifier: Modifier,
    navController: NavController
) {
    val context = LocalContext.current

    // Remember if the login was successful
    var loginSuccess by remember { mutableStateOf(false) }
    Column(modifier = modifier.padding(horizontal = 31.dp)) {
        OutlinedTextField(
            value = username,
            shape = RoundedCornerShape(percent = 20),
            onValueChange = { onUsernameChange(it) },
            label = { Text("Email") },
            placeholder = { Text(text = "Masukkan Email Anda") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Green40,
                backgroundColor = GreenGrey40
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            shape = RoundedCornerShape(percent = 20),
            onValueChange = { onPasswordChange(it) },
            label = { Text("Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                val icon = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(icon, contentDescription = "Toggle password visibility")
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Green40,
                backgroundColor = GreenGrey40
            )

        )
//        Spacer(modifier = Modifier.height(5.dp))
//        Text(
//            text = "Lupa Password?",
//            style = TextStyle(color = Green40),
//            modifier = modifier
//                .fillMaxWidth()
//                .wrapContentWidth(Alignment.End)
//                .clickable {     navController.navigate(Screen.ForgotPasswordScreen.route)
//                }
//        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { loginViewModel.login(username, password)
                if (loginState is LoginState.Success) {
                    navController.navigate(Screen.AppScaffold.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }},
            enabled = loginState !is LoginState.Loading && !loginSuccess,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Green40),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            when  {
                loginState is LoginState.Loading -> {

                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                loginSuccess -> {
                    Text(text = "Logging in...")
                }
                else -> {
                    Text(text = "Login", color = Color.White)
                }
            }
        }
    }

    // Observe the login state and handle navigation when login succeeds
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            val data = (loginState as LoginState.Success).data
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

            navController.popBackStack()
            navController.navigate(Screen.AppScaffold.route+"/${data!!.username}"+"/${data!!.email}") {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
        if (loginState is LoginState.Error) {
            val message = loginState.message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        }
    }
}

@Composable
fun RegisterSection(navController: NavHostController, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "Belum punya akun? ",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            )
        )
        Spacer(modifier = Modifier.width(4.dp)) // Memberikan jarak horizontal antara teks "Belum punya akun? " dan "Daftar"
        ClickableText(
            text = AnnotatedString("Daftar"),
            onClick = {
                navController.navigate(Screen.RegisterScreen.route)
            },
            style = TextStyle(
                color = Green40,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )
        )
    }
}

