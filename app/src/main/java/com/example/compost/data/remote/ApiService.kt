package com.example.compost.data.remote

import com.example.compost.data.model.MoistureRequest
import com.example.compost.data.model.TemperatureRequest
import com.example.compost.data.response.IndicatorRecordsResponse
import com.example.compost.data.response.IndicatorResponse
import com.example.compost.data.response.LoginResponse
import com.example.compost.data.response.MoistureResponse
import com.example.compost.data.response.RegisterResponse
import com.example.compost.data.response.TemperatureResponse
import com.example.compost.data.response.dataControlResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("/user")
    @FormUrlEncoded
    suspend fun registerUser(
        @Field("Username") username: String,
        @Field("Email") email: String,
        @Field("Password") password: String,
    ): RegisterResponse

    @POST("user/login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("Email") email: String,
        @Field("Password") password: String,
    ): LoginResponse

    @GET("data/realtime")
    suspend fun getRealtimeData(): IndicatorResponse

    @GET("data/records")
    suspend fun getRecordsData(): IndicatorRecordsResponse

    @PUT("state/activate")
    suspend fun activate()

    @PUT("state/deactivate")
    suspend fun deactivate()

    @PUT("control/temperature")
    suspend fun setTemperature(
        @Body temperatureRequest: TemperatureRequest
    ): TemperatureResponse

    @PUT("control/moisture")
    suspend fun setMoisture(
        @Body temperatureRequest: MoistureRequest
    ): MoistureResponse

    @GET("control")
    suspend fun dataControl(): dataControlResponse
}





