package com.example.compost.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compost.ui.theme.Green40
import com.example.compost.viewModel.UserViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(navController: NavController, username: String, email: String, logout: () -> Unit) {
    val userViewModel: UserViewModel = getViewModel()
    userViewModel.fetchUserFullName(username)
    userViewModel.fetchUserFullName(email)

    val showAboutDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Green40.value))
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(Color(Green40.value))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navController.popBackStack() }, // Kembali ke halaman sebelumnya
                modifier = Modifier
                    .padding(start = 0.dp, top = 0.dp)
                    .align(Alignment.TopStart)
            ) {
                androidx.compose.material.Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White // Ubah warna ikon menjadi putih
                )
            }

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person Icon",
                modifier = Modifier.size(130.dp),
                tint = Color.White // Ubah warna ikon menjadi putih
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(topStart = 38.dp, topEnd = 38.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileInfoItem(label = "Username", value = username)
                ProfileInfoItem(label = "Email", value = email)
                Spacer(modifier = Modifier.height(2.dp))
                OutlinedButton(
                    onClick = { showAboutDialog.value = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(Green40.value)
                    ),
                    shape = RoundedCornerShape(26.dp),
                    border = BorderStroke(3.dp, Color(Green40.value)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp) // Perbesar tinggi button
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info, // Menambahkan ikon info
                            contentDescription = "About This App Icon",
                            tint = Color(Green40.value),
                            modifier = Modifier.size(24.dp) // Perbesar ukuran ikon
                        )
                        Spacer(modifier = Modifier.width(16.dp)) // Perbesar jarak antara ikon dan teks
                        Text(
                            text = "About This App",
                            color = Color(Green40.value), // Ubah warna teks menjadi hijau
                            fontSize = 18.sp, // Perbesar ukuran teks

                        )
                    }
                }
                if (showAboutDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showAboutDialog.value = false },
                        title = {
                            Text(text = "About This App")
                        },
                        text = {
                            Text(
                                text = "Aplikasi Dicompos adalah sebuah platform monitoring dan controlling pada proses pembuatan kompos. Aplikasi ini terintegrasi langsung dengan komposter digital berbasis Internet of Things."
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = { showAboutDialog.value = false }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
                OutlinedButton(
                    onClick = { logout() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(Green40.value)
                    ),
                    shape = RoundedCornerShape(26.dp),
                    border = BorderStroke(3.dp, Color(Green40.value)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp) // Perbesar tinggi button
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp, // Menggunakan ikon ExitToApp
                            contentDescription = "Log Out Icon",
                            tint = Color(Green40.value),
                            modifier = Modifier.size(24.dp) // Perbesar ukuran ikon
                        )
                        Spacer(modifier = Modifier.width(16.dp)) // Perbesar jarak antara ikon dan teks
                        Text(
                            text = "Log Out",
                            color = Color(Green40.value), // Ubah warna teks menjadi hijau
                            fontSize = 18.sp, // Perbesar ukuran teks
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp),
            color = Color(Green40.value)
        )
        Card(
            modifier = Modifier
                .height(55.dp) // Perbesar tinggi card
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
            backgroundColor = Color(Green40.value)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.body1.copy(fontSize = 20.sp),
                color = Color.White,
                modifier = Modifier.padding(8.dp) // Perbesar padding text
            )
        }
    }
}
