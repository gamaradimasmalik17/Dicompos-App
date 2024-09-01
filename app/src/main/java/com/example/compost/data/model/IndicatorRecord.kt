package com.example.compost.data.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class IndicatorRecord(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("temp") val temperature: Float,
    @field:SerializedName("moist") val humidity: Float,
    @field:SerializedName("ph") val ph: Float,
    @field:SerializedName("temp_ambiance") val ambientTemperature: Float,
    @field:SerializedName("humid_ambiance") val ambientHumidity: Float,
    @field:SerializedName("inserted_at") val insertedAt: Date,
) {
    val formattedInsertedAt: String
        get() = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale("id", "ID")).format(insertedAt)

    val formattedDayInsertedAt: String
        get() = SimpleDateFormat("dd MMM", Locale("id", "ID")).format(insertedAt)
}
