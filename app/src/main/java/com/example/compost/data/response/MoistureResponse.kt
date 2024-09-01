package com.example.compost.data.response

import com.example.compost.data.model.MoistureData
import com.google.gson.annotations.SerializedName

data class MoistureResponse(

    @field:SerializedName("data")
    val data: List<MoistureData>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

