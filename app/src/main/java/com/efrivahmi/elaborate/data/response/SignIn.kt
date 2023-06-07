package com.efrivahmi.elaborate.data.response

import com.google.gson.annotations.SerializedName

data class SignIn(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val token: String
)