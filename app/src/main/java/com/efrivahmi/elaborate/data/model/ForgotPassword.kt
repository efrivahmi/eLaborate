package com.efrivahmi.elaborate.data.model

import com.google.gson.annotations.SerializedName

data class ForgotPassword (
    @SerializedName("email")
    val email: String
)
