package com.example.compost.ui.screens.login
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compost.Screen
import com.example.compost.ui.theme.Green40
import com.example.compost.viewModel.RegisterViewModel
import com.example.compost.viewModel.RegistrationState
import org.koin.androidx.compose.getViewModel


@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val registerViewModel: RegisterViewModel = getViewModel()
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val registerState by registerViewModel.registerState.collectAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() }, // Kembali ke halaman sebelumnya
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        LogoSection(
            modifier = Modifier
                .fillMaxWidth(), // agar LogoSection menempati lebar maksimal
            horizontalAlignment = Alignment.CenterHorizontally, // agar LogoSection berada di tengah horizontal
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = email,
                shape = RoundedCornerShape(percent = 20),
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(
                value = username,
                shape = RoundedCornerShape(percent = 20),
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = password,
                shape = RoundedCornerShape(percent = 20),
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon =
                        if (passwordVisibility) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility }
                    ) {
                        Icon(imageVector = icon, contentDescription = "Toggle password visibility")
                    }
                }
            )

            when (registerState) {
                is RegistrationState.Loading -> {
                    CircularProgressIndicator()
                }

                is RegistrationState.Success -> {
                    val successState = registerState as? RegistrationState.Success
                    successState?.let {
                        LaunchedEffect(successState) {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.LoginScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }


                is RegistrationState.Error -> {
                    val errorState = registerState as? RegistrationState.Error
                    errorState?.let {
                        Text(
                            text = "${it.message}",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                RegistrationState.Initial -> {
                    // Jika state masih di awal atau tidak ada perubahan
                }

                else -> {
                    // Opsi default untuk menangani state yang tidak terduga
                }
            }

            // Row untuk opsi login tetap terlihat
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    registerViewModel.registerUser(email, username, password)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Green40),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Register", color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(
                    text = "Sudah punya akun? ",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default
                    )
                )
                ClickableText(
                    text = AnnotatedString("Masuk Sekarang"),
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route)
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

        }
    }


