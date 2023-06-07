package com.efrivahmi.elaborate.data.response

import com.google.gson.annotations.SerializedName

data class SignUp(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)