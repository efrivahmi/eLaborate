package com.efrivahmi.elaborate.data.response

data class EditProfileResponse (
    val code: Int,
    val error: Boolean,
    val message: String,
    val userData: UserProfileData
)
data class UserProfileData (
    val userId: String,
    val username: String,
    val email: String,
    val phone: String,
    val address: String
)