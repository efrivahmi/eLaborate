package com.efrivahmi.elaborate.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.efrivahmi.elaborate.data.model.UserPreference
import com.efrivahmi.elaborate.ui.login.LoginViewModel
import com.efrivahmi.elaborate.ui.main.MainActivity
import com.efrivahmi.elaborate.ui.main.MainViewModel
import com.efrivahmi.elaborate.ui.register.RegisterViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainActivity::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}