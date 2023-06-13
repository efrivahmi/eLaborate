package com.efrivahmi.elaborate.data.response

data class SignIn(
    val code: Int,
    val error: Boolean,
    val message: String,
    val userData: UserData
)
data class UserData (
    val userId: String,
    val username: String,
    val email: String,
    val token: String
)