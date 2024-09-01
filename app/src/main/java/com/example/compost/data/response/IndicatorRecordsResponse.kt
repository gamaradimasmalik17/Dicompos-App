package com.example.compost.data.response

import com.example.compost.data.model.IndicatorRecord
import com.google.gson.annotations.SerializedName

data class IndicatorRecordsResponse(
    @field:SerializedName( "status") val status: Int,
    @field:SerializedName( "data") val data: List<IndicatorRecord>,
    @field:SerializedName("message") val message: String
)