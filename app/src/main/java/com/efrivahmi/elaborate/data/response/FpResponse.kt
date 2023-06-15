package com.efrivahmi.elaborate.data.response

data class FpResponse(
    val error: Boolean,
    val message: String,
    val userId: String,
    val username: String,
    val email: String,
    val verificationCode: String
)
