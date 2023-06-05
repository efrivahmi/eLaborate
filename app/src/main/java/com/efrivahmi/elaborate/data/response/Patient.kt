package com.efrivahmi.elaborate.data.response

data class Patient(
    val error: Boolean,
    val message: String,
    val userData: UserData
) {
    data class UserData(
        val address: String,
        val email: String,
        val phone: String,
        val token: String,
        val userId: String,
        val username: String
    )
}