package com.efrivahmi.elaborate.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efrivahmi.elaborate.model.UserModel
import com.efrivahmi.elaborate.model.UserPreference
import kotlinx.coroutines.launch

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    fun saveUser(user: UserModel){
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}