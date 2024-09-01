package com.example.compost.data.response

import com.example.compost.data.model.TemperatureData
import com.google.gson.annotations.SerializedName

data class TemperatureResponse(

	@field:SerializedName("data")
	val data: List<TemperatureData>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

