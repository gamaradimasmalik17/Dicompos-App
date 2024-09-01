package com.example.compost.data.repository

import com.example.compost.data.model.CustomLineData
import com.example.compost.data.model.IndicatorInfo
import com.example.compost.data.model.IndicatorRecord
import com.example.compost.data.model.Realtime
import com.example.compost.data.model.dataControl
import com.example.compost.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class IndicatorRepository(private val remoteDataSource: RemoteDataSource) {

    // Simplified method for getting mock indicator info
    suspend fun getIndicatorInfo(): IndicatorInfo {
        return withContext(Dispatchers.IO) {
            // Simulate network delay
            delay(1000)

            // Return mock data
            IndicatorInfo("25°C", "Normal", "60%", "Optimal", "7.0", "Neutral")
        }
    }

    // Fetch real-time indicator information from remote data source
    suspend fun fetchIndicatorInfo(): Realtime? {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getData()
                if (response.status == 200) {
                    response.data.getOrNull(0) // Return the first item or null if the list is empty
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }


    suspend fun fetchDataControl(): dataControl? {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getControl()
                if (response.status == 200) {
                    response.data.getOrNull(0) // Return the first item or null if the list is empty
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // Fetch historical records data for chart display
    suspend fun fetchRecordsData(nameIndicator: String): List<CustomLineData> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getRecords()
                if (response.status == 200) {
                    response.data.map { record ->
                        val yValue = when (nameIndicator) {
                            "Temperature" -> record.temperature
                            "Humidity" -> record.humidity
                            "Ph" -> record.ph
                            else -> 0f
                        }
                        CustomLineData(yValue, record.formattedDayInsertedAt,nameIndicator)
                    }
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
    //
    suspend fun fetchRecordsData(): List<IndicatorRecord> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getRecords()
                if (response.status == 200) {
                    response.data
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }



}
