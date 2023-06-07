package com.efrivahmi.elaborate.data.model

import com.google.gson.annotations.SerializedName

class UserRegister (
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String
)