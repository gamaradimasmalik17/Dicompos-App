package com.example.compost.ui.screens.home




import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.compost.data.model.IndicatorRecord
import com.example.compost.ui.theme.Green40
import com.example.compost.ui.theme.White12
import com.example.compost.viewModel.IndicatorViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import org.koin.androidx.compose.getViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun HomeScreen(navController: NavController, username: String) {
    val selectedIndicator = remember { mutableStateOf("Temperature") }
    val indicatorViewModel: IndicatorViewModel = getViewModel()
    val showGraph: (String) -> Unit = { indicatorName ->
        selectedIndicator.value = indicatorName
    }

    val temperatureTargetMin = remember { mutableStateOf("") }
    val temperatureTargetMax = remember { mutableStateOf("") }
    val moistureTargetMin = remember { mutableStateOf("") }
    val moistureTargetMax = remember { mutableStateOf("") }

    // Load the saved values from SharedPreferences
    indicatorViewModel.loadSavedValues(
        temperatureTargetMin, temperatureTargetMax,
        moistureTargetMin, moistureTargetMax
    )

    LaunchedEffect(Unit) {
        indicatorViewModel.fetchDataControl()
    }

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
                .padding(16.dp)
        ) {
            userInfo(username)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    Color(White12.value),
                    shape = RoundedCornerShape(topStart = 38.dp, topEnd = 38.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TargetButtonCard("Temperature", isTemperature = true, targetMin = temperatureTargetMin, targetMax = temperatureTargetMax, context = LocalContext.current, indicatorViewModel)

                TargetButtonCardMoisture("Moisture", isMoisture = true, targetMin = moistureTargetMin, targetMax = moistureTargetMax, context = LocalContext.current, indicatorViewModel)

                DataControlCard(indicatorViewModel)


                ChartCard(selectedIndicator.value, indicatorViewModel)

                indicators { selectedIndicator.value = it }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
fun TargetButtonCard(parameterType: String, isTemperature: Boolean = false, targetMin: MutableState<String>, targetMax: MutableState<String>,context: Context,viewModel: IndicatorViewModel) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color.White,
                contentColor = contentColorFor(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Target $parameterType",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "$parameterType",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isTemperature) "Min" else "Min",
                                style = MaterialTheme.typography.body2,
                                color = Color.Black
                            )
                            TextField(
                                value = targetMin.value,
                                onValueChange = { targetMin.value = it }
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isTemperature) "Max" else "Max",
                                style = MaterialTheme.typography.body2,
                                color = Color.Black
                            )
                            TextField(
                                value = targetMax.value,
                                onValueChange = { targetMax.value = it }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        androidx.compose.material.Button(
                            onClick = { showDialog.value = false },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(Green40.value))
                        ) {
                            Text("Batal", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        androidx.compose.material.Button(
                            onClick = {
                                viewModel.setTemperature(targetMin.value, targetMax.value) { success ->
                                    showDialog.value = false
                                    if (success) {
                                        Toast.makeText(context, "Temperature set successfully", Toast.LENGTH_SHORT).show()

                                        showDialog.value = false
                                    } else {
                                        Toast.makeText(context, "Failed to set temperature", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                 },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(Green40.value))
                        ) {
                            Text("Simpan", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog.value = true }
            .shadow(elevation = 14.dp, shape = RoundedCornerShape(16.dp), clip = true), // Add 3D shadow effect
        backgroundColor = Color.White, // Set card background color to white
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                horizontalAlignment = Alignment.Start // Align content to the start (left)
            ) {
                Text(
                    text = "Target $parameterType",
                    style = MaterialTheme.typography.h6,
                    color = Color(Green40.value) // Change text color to white
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isTemperature) "Min: ${targetMin.value}°C  Max: ${targetMax.value}°C" else "Min: ${targetMin.value}%  Max: ${targetMax.value}%",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black // Change text color to white
                )
            }
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .border(1.dp, Color(Green40.value), shape = CircleShape) // Add border to the icon
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow Icon",
                    tint = Color.Black, // Change icon color to white
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp) // Adjust icon size as needed
                )
            }
        }
    }
}


@Composable
fun TargetButtonCardMoisture(parameterType: String, isMoisture: Boolean = false, targetMin: MutableState<String>, targetMax: MutableState<String>,context: Context,viewModel: IndicatorViewModel) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color.White,
                contentColor = contentColorFor(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Target $parameterType",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "$parameterType",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isMoisture) "Min" else "Minimal",
                                style = MaterialTheme.typography.body2,
                                color = Color.Black
                            )
                            TextField(
                                value = targetMin.value,
                                onValueChange = { targetMin.value = it }
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isMoisture) "Max" else "Maximal",
                                style = MaterialTheme.typography.body2,
                                color = Color.Black
                            )
                            TextField(
                                value = targetMax.value,
                                onValueChange = { targetMax.value = it }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        androidx.compose.material.Button(
                            onClick = { showDialog.value = false },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(Green40.value))
                        ) {
                            Text("Batal", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        androidx.compose.material.Button(
                            onClick = {
                                viewModel.setMoisture(targetMin.value, targetMax.value) { success ->
                                    showDialog.value = false
                                    if (success) {
                                        Toast.makeText(context, "Moisture set successfully", Toast.LENGTH_SHORT).show()

                                        showDialog.value = false
                                    } else {
                                        Toast.makeText(context, "Failed to set Moisture", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(Green40.value))
                        ) {
                            Text("Simpan", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog.value = true }
            .shadow(elevation = 14.dp, shape = RoundedCornerShape(16.dp), clip = true), // Add 3D shadow effect
        backgroundColor = Color.White, // Set card background color to white
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                horizontalAlignment = Alignment.Start // Align content to the start (left)
            ) {
                Text(
                    text = "Target $parameterType",
                    style = MaterialTheme.typography.h6,
                    color = Color(Green40.value) // Change text color to white
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isMoisture) "Min: ${targetMin.value}% Max: ${targetMax.value}%" else "Minimal: ${targetMin.value}  Maximal: ${targetMax.value}",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black // Change text color to white
                )
            }
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .border(1.dp, Color(Green40.value), shape = CircleShape) // Add border to the icon
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow Icon",
                    tint = Color.Black, // Change icon color to white
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp) // Adjust icon size as needed
                )
            }
        }
    }
}

@Composable
fun DataControlCard(viewModel: IndicatorViewModel) {
    // Mengambil data dari ViewModel
    val dataControl by viewModel.dataControlState.collectAsState()

    Log.d("DataControlCard", "Data received: $dataControl")

    // Tampilkan Card hanya jika dataControl tidak null
    if (dataControl != null) {
        dataControl?.let { control ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 14.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = true
                    ), // Add 3D shadow effect
                backgroundColor = Color.White, // Set card background color to white
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Mesofilik",
                            style = MaterialTheme.typography.body2, // Ukuran teks lebih kecil
                            color = Color(Green40.value)
                        )
                        Text(
                            text = control.lw2.toString(),
                            style = MaterialTheme.typography.body2,
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Thermofilik",
                            style = MaterialTheme.typography.body2, // Ukuran teks lebih kecil
                            color = Color(Green40.value)
                        )
                        Text(
                            text = control.h2.toString(),
                            style = MaterialTheme.typography.body2,
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Maturasi",
                            style = MaterialTheme.typography.body2, // Ukuran teks lebih kecil
                            color = Color(Green40.value)
                        )
                        Text(
                            text = control.vc2.toString(),
                            style = MaterialTheme.typography.body2,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    } else {
        // Placeholder jika data belum ada
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Memuat data...",
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun userInfo(username: String) {
    val dateFormat = SimpleDateFormat("E, dd MMMM yyyy", Locale("id", "ID"))
    val currentDate = dateFormat.format(Date())

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    text = "Halo, $username",
                    style = MaterialTheme.typography.h6.copy(fontSize = 24.sp),
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
                Text(
                    text = "Selamat datang di Dicompos",
                    style = MaterialTheme.typography.body2.copy(fontSize = 20.sp),
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(48.dp) // Ukuran kotak lebih kecil
                    .border(3.dp, Color.White, CircleShape) // Border lebih tipis
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Person, contentDescription = "Person", modifier = Modifier.size(40.dp), tint = Color.Black) // Ukuran ikon lebih kecil
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currentDate,
                style = MaterialTheme.typography.body2.copy(fontSize = 20.sp),
                textAlign = TextAlign.Start,
                color = Color.Black
            )
        }
    }
}

@Composable
fun indicators(showGraph: (String) -> Unit) {
    val indicatorViewModel: IndicatorViewModel = getViewModel()
    indicatorViewModel.fetchIndicatorInfo()
    val indicatorInfo by indicatorViewModel.indicatorInfoState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Baris pertama
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IndicatorCard(
                modifier = Modifier.weight(1f),
                icon = { Icon(Icons.Filled.Thermostat, contentDescription = "Temperature") },
                indicatorValue = indicatorInfo?.temperature?.toString() ?: "0.0",
                indicatorName = "Temp",
                onClick = { showGraph("Temp") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            IndicatorCard(
                modifier = Modifier.weight(1f),
                icon = { Icon(Icons.Filled.Water, contentDescription = "Moisture") },
                indicatorValue = indicatorInfo?.moisture?.toString() ?: "0.0",
                indicatorName = "Moisture",
                onClick = { showGraph("Moisture") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            IndicatorCard(
                modifier = Modifier.weight(1f),
                icon = { Icon(Icons.Filled.Waves, contentDescription = "pH Level") },
                indicatorValue = indicatorInfo?.ph?.toString() ?: "0.0",
                indicatorName = "pH Level",
                onClick = { showGraph("Ph") }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Baris kedua dengan "Phase" di tengah
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center // Posisikan di tengah
        ) {
            IndicatorCard(
                modifier = Modifier.size(width = 111.dp, height = 106.dp), // Atur ukuran panjang dan lebar
                indicatorValue = indicatorInfo?.phase?.toString() ?: "0.0", // Tanpa ikon
                indicatorName = "Phase",
                valueFontSize = 12.sp, // Ukuran font lebih kecil untuk "Phase"
                onClick = { showGraph("Phase") }
            )
        }
    }
}


@Composable
fun IndicatorCard(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null, // Ikon sekarang opsional
    indicatorValue: String,
    indicatorName: String,
    valueFontSize: TextUnit = 20.sp, // Parameter ukuran font untuk value, default 20.sp
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(106.dp) // Menetapkan tinggi yang sama untuk semua kartu
            .clickable(onClick = onClick)
            .shadow(
                elevation = 18.dp, // Menambahkan ketebalan bayangan
                shape = RoundedCornerShape(24.dp),
                clip = true // Untuk memastikan konten juga terklip dengan bentuk
            ),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    CompositionLocalProvider(LocalContentColor provides Color(Green40.value)) {
                        icon()
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = indicatorValue,
                    style = MaterialTheme.typography.h5.copy(fontSize = valueFontSize),
                    textAlign = TextAlign.End,
                    color = Color(Green40.value)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = indicatorName,
                style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                textAlign = TextAlign.End,
                color = Color(Green40.value)
            )

        }
    }
}

@Composable
fun ChartCard(nameIndicator: String, indicatorViewModel: IndicatorViewModel) {
    indicatorViewModel.fetchDataChart()
    //val dataChart by indicatorViewModel.lineDataState.collectAsState()
    val dataChart by indicatorViewModel.lineDataRecord.collectAsState()

    val chartTitle = "Grafik Temperature, pH"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(24.dp), clip = true), // Add 3D shadow effect
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Adjust padding for better layout
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth() // Ensure the Row fills the width of the Card
            ) {
                Text(
                    text = chartTitle,
                    style = MaterialTheme.typography.body1,
                    color = Color(Green40.value),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LineChartScreen(dataChart)
        }
    }
}

@Composable
fun LineChartScreen(lineChartData: List<IndicatorRecord>) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {

        val context = LocalContext.current
        val chart = com.github.mikephil.charting.charts.LineChart(context)



        // Create data entries for each dataset
        val tempEntries = lineChartData.mapIndexed { index, it -> Entry(index.toFloat(), it.temperature) }
       // val moistEntries = lineChartData.mapIndexed { index, it -> Entry(index.toFloat(), it.humidity) }
        val phEntries = lineChartData.mapIndexed { index, it -> Entry(index.toFloat(), it.ph) }

        val tempDataSet = LineDataSet(tempEntries, "Temperature").apply {
            color = android.graphics.Color.RED
            circleRadius = 4f
            setCircleColor(android.graphics.Color.RED)
        }

//        val moistDataSet = LineDataSet(moistEntries, "Moisture").apply {
//            color = android.graphics.Color.BLUE
//            circleRadius = 4f
//            setCircleColor(android.graphics.Color.BLUE)
//        }

        val phDataSet = LineDataSet(phEntries, "pH Level").apply {
            color = android.graphics.Color.GREEN
            circleRadius = 4f
            setCircleColor(android.graphics.Color.GREEN)
        }

        val lineData = com.github.mikephil.charting.data.LineData(
            listOf(
                tempDataSet,
                //moistDataSet,
                phDataSet
            ) as List<ILineDataSet>
        )

        // Configure chart appearance
        chart.data = lineData
        chart.description.isEnabled = false
        chart.axisRight.isEnabled=false

        // Customize x-axis
        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(true)
            granularity = 1f
            axisMinimum = 0f
            isGranularityEnabled = true
            labelRotationAngle = 90f
            textSize = 6f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.toInt()
                    return if (lineChartData.isNotEmpty() && lineChartData.size == 1 && (index==-1 || index==1)) "" else if (lineChartData.isNotEmpty()) lineChartData[index].formattedInsertedAt else ""
//                    val index = value.toInt()
//                    return if (index < data.size) lineChartData[index].formattedInsertedAt else value.toString()
                }
            }
            setDrawGridLines(false)

        }
        // Configure the chart
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White),
            factory = {
                chart
            },
            update = { chart ->
                // Customize x-axis
                chart.xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawLabels(true)
                    granularity = 1f
                    axisMinimum = 0f
                    isGranularityEnabled = true
                    labelRotationAngle = 90f
                    textSize = 6f // Set X-axis label font size
                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            val index = value.toInt()
                            return if (lineChartData.isNotEmpty() && lineChartData.size == 1 && (index == -1 || index == 1)) "" else if (lineChartData.isNotEmpty()) lineChartData[index].formattedInsertedAt else ""
                        }
                    }}
                chart.data = lineData // Update the chart's data
                chart.invalidate()    // Refresh the chart to reflect the new data
            }
        )

    }
}

