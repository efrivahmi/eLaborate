package com.efrivahmi.elaborate.data.model

data class ResetPassword(
    val resetToken: String,
    val newPassword: String
)
