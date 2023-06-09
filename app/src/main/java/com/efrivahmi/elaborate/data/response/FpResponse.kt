package com.efrivahmi.elaborate.data.response

data class FpResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val userId: String,
    val username: String,
    val email: String,
    val verificationCode: String
)
