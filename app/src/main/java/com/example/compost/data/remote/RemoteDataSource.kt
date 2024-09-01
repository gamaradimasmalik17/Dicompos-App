package com.example.compost.data.remote

import com.example.compost.data.model.MoistureRequest
import com.example.compost.data.model.TemperatureRequest

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getData() = apiService.getRealtimeData()

    suspend fun getRecords() = apiService.getRecordsData()

    suspend fun getControl() = apiService.dataControl()

    suspend fun registerUser(username: String, email: String, password: String) =
        apiService.registerUser(username, email, password)

    suspend fun loginUser(email: String, password: String) =
        apiService.loginUser(email, password)

    suspend fun activate() {
        apiService.activate()
    }

    suspend fun deactivate() {
        apiService.deactivate()
    }

    suspend fun setTemperature(mesophilicTemp: Int, thermophilicTemp: Int) =
        apiService.setTemperature(TemperatureRequest(mesophilicTemp, thermophilicTemp))

    suspend fun setMoisture(minMoist: Int, maxMoist: Int) =
        apiService.setMoisture(MoistureRequest(minMoist, maxMoist))
}