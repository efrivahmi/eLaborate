package com.efrivahmi.elaborate.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efrivahmi.elaborate.data.model.UserModel
import com.efrivahmi.elaborate.data.repository.DataSource
import kotlinx.coroutines.launch

class MainViewModel(private val dataSource: DataSource): ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return dataSource.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            dataSource.logout()
        }
    }
}