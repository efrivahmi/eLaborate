package com.efrivahmi.elaborate.ui.login.forget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efrivahmi.elaborate.data.model.ForgotPassword
import com.efrivahmi.elaborate.data.model.Verify
import com.efrivahmi.elaborate.data.repository.DataSource
import com.efrivahmi.elaborate.data.response.FpResponse
import com.efrivahmi.elaborate.data.response.VerifyCode
import com.efrivahmi.elaborate.utils.HelperToast

class ForgetPasswordViewModel(private val dataSource: DataSource) : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = dataSource.isLoading
    val forgot: LiveData<FpResponse> = dataSource.forgot
    val verifyCode: LiveData<VerifyCode> = dataSource.verify
    private val _toastText = MutableLiveData<HelperToast<String>>()
    val toastText: LiveData<HelperToast<String>> = _toastText

    fun forgotPassword(email: String) {
        val user = ForgotPassword(email)
        dataSource.forgotPassword(user)
    }

    fun sendVerificationCode(email: String, verificationCode: String) {
        val user = Verify(email, verificationCode)
        dataSource.verifyCode(user)
    }
}