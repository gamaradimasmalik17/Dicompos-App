package com.example.compost.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Realtime(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("temp") val temperature: Int,
    @field:SerializedName("moist") val moisture: Int,
    @field:SerializedName("ph") val ph: Int,
    @field:SerializedName("temp_ambiance") val ambientTemperature: Int,
    @field:SerializedName("humid_ambiance") val ambientHumidity: Int,
    @field:SerializedName("phase") val phase: String,
    @field:SerializedName("inserted_at") val insertedAt: Date
)