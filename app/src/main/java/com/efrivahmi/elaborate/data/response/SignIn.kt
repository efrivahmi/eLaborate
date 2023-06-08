package com.efrivahmi.elaborate.data.response

data class SignIn(
    val email: String,
    val error: Boolean,
    val message: String,
    val token: String,
    val userId: String,
    val username: String
)