package com.efrivahmi.elaborate.ui.main.diagnose

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efrivahmi.elaborate.data.model.Diagnose
import com.efrivahmi.elaborate.data.model.UserModel
import com.efrivahmi.elaborate.data.repository.DataSource
import com.efrivahmi.elaborate.data.response.DiagnoseResponse
import com.efrivahmi.elaborate.utils.HelperToast
import kotlinx.coroutines.launch

class DiagnoseViewModel(private val dataSource: DataSource) : ViewModel() {
    val isLoading: LiveData<Boolean> = dataSource.isLoading
    val diagnoseResult: LiveData<DiagnoseResponse> = dataSource.diagnose
    val toastText: LiveData<HelperToast<String>> = dataSource.toastText

    fun diagnoseClient(userId: String, diagnose: Diagnose) {
        viewModelScope.launch {
            dataSource.diagnoseClient(userId, diagnose)
        }
    }

    fun getPatient(): LiveData<UserModel>{
        return dataSource.getUser()
    }
}