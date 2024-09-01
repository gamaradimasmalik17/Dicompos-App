package com.example.compost.data.repository

import com.example.compost.data.model.IndicatorInfo
import com.example.compost.data.model.Realtime
import com.example.compost.data.remote.RemoteDataSource
import com.example.compost.data.response.MoistureResponse
import com.example.compost.data.response.TemperatureResponse
import com.himanshoe.charty.line.model.LineData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ControlRepository(private val remoteDataSource: RemoteDataSource) {



    suspend fun getIndicatorInfo(): IndicatorInfo {
        // Contoh implementasi sederhana untuk mendapatkan informasi indikator
        return withContext(Dispatchers.IO) {
            // Simulasi jeda jaringan
            delay(1000)

            // Mengembalikan data indikator berdasarkan nama indikator
            IndicatorInfo("25°C", "Normal","60%", "Optimal","7.0", "Neutral")
        }
    }

    suspend fun fetchIndicatorInfo(): Realtime? {
        return try {
            val response = remoteDataSource.getData()
            if (response.status == 200) {
                // Assuming data is present at index 0
                response.data.getOrNull(0)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    fun getDataChartLine(nameIndicator: String): List<LineData> {
     // Ambil data LineData dari repository berdasarkan nama indikator
        // Implementasi ini akan bergantung pada sumber data Anda
        // Contoh sederhana:
        return when (nameIndicator) {
            "Temperature" -> generateMockTemperatureData()
            "Humidity" -> generateMockHumidityData()
            "Ph" -> generateMockPhData()
            else -> generateMockTemperatureData()
        }
    }

    suspend fun fetchRecordsData(nameIndicator: String): List<LineData> {
        return try {
            val response = remoteDataSource.getRecords()
            if (response.status == 200) {
                response.data.map { record ->
                    val yValue = when (nameIndicator) {
                        "Temperature" -> record.temperature.toFloat()
                        "Humidity" -> record.humidity.toFloat()
                        "Ph" -> record.ph.toFloat()
                        else -> 0f
                    }
                    LineData(yValue, record.formattedDayInsertedAt)
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    private fun generateMockTemperatureData(): List<LineData> {
        return listOf(
            LineData(45F, "Sen"),
            LineData(10F, "Sel"),
            LineData(34F, "Rab"),
            LineData(50F, "Kam"),
            LineData(20F, "Sab")
        )
    }

    private fun generateMockHumidityData(): List<LineData> {
        return listOf(
            LineData(0F, "Sen"),
            LineData(10F, "Sel"),
            LineData(5F, "Rab"),
            LineData(50F, "Kam"),
            LineData(55F, "Jum"),
            LineData(3F, "Sab"),
            LineData(9F, "Min")
        )
    }

    private fun generateMockPhData(): List<LineData> {
        return listOf(
            LineData(6F, "Sen"),
            LineData(7F, "Sel"),
            LineData(7F, "Rab"),
            LineData(7F, "Kam"),
            LineData(4F, "Jum"),
            LineData(5F, "Sab"),
            LineData(6F, "Min")
        )
    }

    suspend fun activate() {
        withContext(Dispatchers.IO) {
            remoteDataSource.activate()
        }
    }

    suspend fun deactivate() {
        withContext(Dispatchers.IO) {
            remoteDataSource.deactivate()
        }
    }

    suspend fun setTemperature(mesophilicTemp: Int, thermophilicTemp: Int) : TemperatureResponse {

        return remoteDataSource.setTemperature(mesophilicTemp, thermophilicTemp)
    }

    suspend fun setMoisture(minMoist: Int, maxMoist: Int) : MoistureResponse {

        return remoteDataSource.setMoisture(minMoist, maxMoist)
    }
}