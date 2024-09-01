package com.example.compost.ui.screens.control

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Water
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compost.ui.screens.home.userInfo
import com.example.compost.ui.theme.Green40

// Model untuk mewakili kontrol
data class Control(val name: String, var value: Float, val level: String)

@Composable
fun ControlScreen(navController: NavController, username: String) {

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
                .padding(16.dp) // Padding untuk menambahkan jarak di sekitar konten
        ) {
            userInfo(username)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(topStart = 38.dp, topEnd = 38.dp)) // Warna hijau muda dengan sudut melengkung
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Mengurangi jarak antar elemen menjadi 8.dp
            ) {
                Text(
                    text = "Silahkan atur nilai ambang batas yang diinginkan di bawah ini:",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                ControlMenu()
            }
        }
    }
}

@Composable
fun ControlMenu() {
    // Menyimpan state apakah program sedang berjalan atau tidak
    val isRunning = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ControlMenuItem(name = "Temperatur", icon = Icons.Filled.Thermostat)
            Spacer(modifier = Modifier.height(35.dp)) // Ubah ukuran jarak sesuai kebutuhan
            ControlMenuItem(name = "Moisture", icon = Icons.Filled.Water)
        }
    }
}

@Composable
fun ControlMenuItem(name: String, icon: ImageVector) {
    // Menyimpan state apakah pop-up sedang ditampilkan atau tidak
    val showDialog = remember { mutableStateOf(false) }

    // Menyimpan nilai min dan max untuk kontrol saat ini
    val minThreshold = remember { mutableStateOf(0f) }
    val maxThreshold = remember { mutableStateOf(100f) }

    Card(
        modifier = Modifier
            .clickable {
                // Menampilkan pop-up saat card diklik
                showDialog.value = true
            }
            .size(width = 180.dp, height = 160.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp), // Mengubah sudut card menjadi lebih melengkung
        elevation = 12.dp, // Menambahkan bayangan untuk membuat card lebih menonjol
        backgroundColor = Color(0xFFE0F7FA) // Menggunakan warna yang lebih menarik
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = Color(0xFF00796B) // Menggunakan warna ikon yang kontras
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.h6, // Menggunakan typography yang lebih besar
                color = Color(0xFF00796B),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp) // Memberikan jarak vertikal
            )
            // Menampilkan nilai ambang batas yang sudah diatur
            Text(
                text = "Mesofilik ${minThreshold.value}, Termofilik ${maxThreshold.value}",
                style = MaterialTheme.typography.body2,
                color = Color(0xFF00796B),
                textAlign = TextAlign.Center
            )
        }
    }

    // Menampilkan pop-up jika showDialog bernilai true
    if (showDialog.value) {
        ThresholdPopup(
            controlName = name,
            minValue = minThreshold.value.toInt(), // Ubah menjadi bilangan bulat
            maxValue = maxThreshold.value.toInt(), // Ubah menjadi bilangan bulat
            range = 0f..100f,
            onMinValueChange = { value -> minThreshold.value = value.toFloat() },
            onMaxValueChange = { value -> maxThreshold.value = value.toFloat() },
            onDismiss = {
                showDialog.value = false
            } // Menutup pop-up saat tombol Close ditekan
        )
    }
}


@Composable
fun ThresholdPopup(
    controlName: String,
    minValue: Int,
    maxValue: Int,
    range: ClosedFloatingPointRange<Float>,
    onMinValueChange: (Int) -> Unit,
    onMaxValueChange: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = controlName) },
        text = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Mesofilik Value: $minValue",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Slider(
                    value = minValue.toFloat(),
                    onValueChange = { newValue -> onMinValueChange(newValue.toInt()) },
                    valueRange = range,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(Green40.value),
                        activeTrackColor = Color(Green40.value)
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )
                Text(
                    text = "Termofilik Value: $maxValue",
                    modifier = Modifier.padding(top = 8.dp)
                )
                Slider(
                    value = maxValue.toFloat(),
                    onValueChange = { newValue -> onMaxValueChange(newValue.toInt()) },
                    valueRange = range,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(Green40.value),
                        activeTrackColor = Color(Green40.value)
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(Green40.value))
            ) {
                Text("Close", color = Color.White)
            }
        }
    )
}
