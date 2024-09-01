package com.example.compost.data.model

import com.google.gson.annotations.SerializedName

data class RegisterData (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("Email")
    val email: String? = null,

    @field:SerializedName("Password")
    val password: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("Username")
    val username: String? = null,

    @field:SerializedName("inserted_at")
    val insertedAt: String? = null
)