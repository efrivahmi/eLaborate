package com.efrivahmi.elaborate.data.model

import com.google.gson.annotations.SerializedName

data class ResetPassword(
    @SerializedName("resetToken")
    var resetToken: String,
    @SerializedName("newPassword")
    val newPassword: String
)
