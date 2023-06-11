package com.efrivahmi.elaborate.ui.login.editpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efrivahmi.elaborate.data.model.ResetPassword
import com.efrivahmi.elaborate.data.repository.DataSource
import com.efrivahmi.elaborate.data.response.RpResponse
import com.efrivahmi.elaborate.data.response.VerifyCode
import com.efrivahmi.elaborate.utils.HelperToast
import kotlinx.coroutines.launch

class EditPasswordViewModel(private val dataSource: DataSource) : ViewModel() {
    val isLoading: LiveData<Boolean> = dataSource.isLoading
    val resetPasswordResult: LiveData<RpResponse> = dataSource.resetPasswordResult
    val toastText: LiveData<HelperToast<String>> = dataSource.toastText
    private val _resetToken = MutableLiveData<String>()
    var resetToken: LiveData<String> = _resetToken

    fun resetPassword(resetToken: String, newPassword: String) {
        val resetPasswordModel = ResetPassword(resetToken, newPassword)
        viewModelScope.launch {
            dataSource.resetPassword(resetPasswordModel.toString())
        }
    }

    fun saveSession(resetToken: String) {
        viewModelScope.launch {
            dataSource.saveResetToken(resetToken)
            _resetToken.value = resetToken
        }
    }
}
