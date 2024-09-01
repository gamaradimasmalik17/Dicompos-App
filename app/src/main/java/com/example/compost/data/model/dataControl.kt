package com.example.compost.data.model

import com.google.gson.annotations.SerializedName

data class dataControl(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("vc1")
    val vc1: Number? = null,

    @field:SerializedName("vc2")
    val vc2: Number? = null,

    @field:SerializedName("vc3")
    val vc3: Number? = null,

    @field:SerializedName("c1")
    val c1: Number? = null,
    @field:SerializedName("c2")
    val c2: Number? = null,

    @field:SerializedName("c3")
    val c3: Number? = null,

    @field:SerializedName("lw1")
    val lw1: Number? = null,
    @field:SerializedName("lw2")
    val lw2: Number? = null,
    @field:SerializedName("lw3")
    val lw3: Number? = null,

    @field:SerializedName("w1")
    val w1: Number? = null,

    @field:SerializedName("w2")
    val w2: Number? = null,

    @field:SerializedName("w3")
    val w3: Number? = null,

    @field:SerializedName("h1")
    val h1: Number? = null,

    @field:SerializedName("h2")
    val h2: Number? = null,

    @field:SerializedName("h3")
    val h3: Number? = null,

    @field:SerializedName("vh1")
    val vh1: Number? = null,
    @field:SerializedName("vh2")
    val vh2: Number? = null,

    @field:SerializedName("vh3")
    val vh3: Number? = null,

    @field:SerializedName("moist_min")
    val moistMin: Number? = null,

    @field:SerializedName("moist_max")
    val moistMax: Number? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null
)

