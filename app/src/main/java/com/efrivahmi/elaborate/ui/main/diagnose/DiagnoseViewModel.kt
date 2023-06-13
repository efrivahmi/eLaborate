package com.efrivahmi.elaborate.ui.main.diagnose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efrivahmi.elaborate.data.model.Diagnose
import com.efrivahmi.elaborate.data.repository.DataSource
import com.efrivahmi.elaborate.data.response.DiagnoseResponse
import kotlinx.coroutines.launch

class DiagnoseViewModel(private val dataSource: DataSource) : ViewModel() {
    private val _diagnoseResult = MutableLiveData<DiagnoseResponse>()
    val diagnoseResult: LiveData<DiagnoseResponse> = _diagnoseResult

    fun diagnoseClient(userId: String, diagnose: Diagnose) {
        viewModelScope.launch {
            dataSource.diagnoseClient(userId, diagnose)
        }
    }
}