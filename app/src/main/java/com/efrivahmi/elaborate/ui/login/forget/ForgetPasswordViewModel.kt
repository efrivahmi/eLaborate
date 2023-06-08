package com.efrivahmi.elaborate.ui.login.forget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efrivahmi.elaborate.data.repository.DataSource
import com.efrivahmi.elaborate.data.response.FpResponse
import com.efrivahmi.elaborate.utils.HelperToast
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(private val dataSource: DataSource) : ViewModel() {
    val forgot: LiveData<FpResponse> = dataSource.forgot
    private val _toastText = MutableLiveData<HelperToast<String>>()
    val toastText: LiveData<HelperToast<String>> = _toastText

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            dataSource.forgotPassword(email)
        }
    }
}
