package com.example.compost.data.response

import com.example.compost.data.model.Realtime
import com.google.gson.annotations.SerializedName

data class IndicatorResponse(
    @field:SerializedName( "status") val status: Int,
    @field:SerializedName( "data") val data: List<Realtime>,
    @field:SerializedName("message") val message: String
)