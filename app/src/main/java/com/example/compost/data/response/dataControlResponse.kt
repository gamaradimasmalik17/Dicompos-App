package com.example.compost.data.response

import com.example.compost.data.model.dataControl
import com.google.gson.annotations.SerializedName

data class dataControlResponse(
    @field:SerializedName( "status") val status: Int,
    @field:SerializedName( "data") val data: List<dataControl>,
    @field:SerializedName("message") val message: String
)