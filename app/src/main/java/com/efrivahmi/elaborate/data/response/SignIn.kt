package com.efrivahmi.elaborate.data.response

data class SignIn(
    val error : String,
    val message : String,
    val loginResult: LoginResult
)
data class LoginResult (
    val userId : String,
    val name : String,
    val token : String
)