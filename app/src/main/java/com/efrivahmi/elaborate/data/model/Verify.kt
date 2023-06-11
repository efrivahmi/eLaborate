package com.efrivahmi.elaborate.data.model

import com.google.gson.annotations.SerializedName

data class Verify(
    @SerializedName("email")
    val email: String,
    @SerializedName("verificationCode")
    val verificationCode: String
)
