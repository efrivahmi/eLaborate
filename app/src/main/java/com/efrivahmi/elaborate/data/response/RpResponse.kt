package com.efrivahmi.elaborate.data.response

data class RpResponse(
    val error: Boolean,
    val message: String,
    val userId: String,
    val username: String,
    val email: String,
    val tokenRevoked: Boolean
)
