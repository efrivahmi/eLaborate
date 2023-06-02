package com.efrivahmi.elaborate.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val isLogin: Boolean
): Parcelable