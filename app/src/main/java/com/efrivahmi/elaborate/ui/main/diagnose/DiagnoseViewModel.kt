package com.efrivahmi.elaborate.ui.main.diagnose

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efrivahmi.elaborate.data.api.ml.DataSourDiagnose
import com.efrivahmi.elaborate.data.model.Diagnose
import com.efrivahmi.elaborate.data.model.UserModel
import com.efrivahmi.elaborate.data.repository.DataSource
import com.efrivahmi.elaborate.data.response.DiagnoseResponse
import com.efrivahmi.elaborate.utils.HelperToast
import kotlinx.coroutines.launch

class DiagnoseViewModel(private val dataSourceMl: DataSourDiagnose) : ViewModel() {
    val isLoading: LiveData<Boolean> = dataSourceMl.isLoading
    val diagnoseResult: LiveData<DiagnoseResponse> = dataSourceMl.diagnose
    val toastText: LiveData<HelperToast<String>> = dataSourceMl.toastText

    fun diagnoseClient(diagnose: Diagnose) {
        viewModelScope.launch {
            dataSourceMl.diagnoseClient(diagnose)
        }
    }
}