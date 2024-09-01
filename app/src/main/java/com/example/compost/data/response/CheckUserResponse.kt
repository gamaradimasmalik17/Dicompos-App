package com.example.compost.data.response

import com.google.gson.annotations.SerializedName

data class CheckUserResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataItem(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("Username")
	val username: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("inserted_at")
	val insertedAt: String? = null,

	@field:SerializedName("Password")
	val password: String? = null
)
