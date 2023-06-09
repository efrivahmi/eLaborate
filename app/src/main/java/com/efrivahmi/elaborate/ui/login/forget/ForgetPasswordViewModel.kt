package com.efrivahmi.elaborate.ui.login.forget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efrivahmi.elaborate.data.model.ForgotPassword
import com.efrivahmi.elaborate.data.model.Verify
import com.efrivahmi.elaborate.data.repository.DataSource
import com.efrivahmi.elaborate.data.response.FpResponse
import com.efrivahmi.elaborate.data.response.VerifyCode
import com.efrivahmi.elaborate.utils.EmailUtil
import com.efrivahmi.elaborate.utils.HelperToast
import kotlinx.coroutines.launch
import java.util.UUID

class ForgetPasswordViewModel(private val dataSource: DataSource) : ViewModel() {
    val forgot: LiveData<FpResponse> = dataSource.forgot
    val verifyCode: LiveData<VerifyCode> = dataSource.verify
    private val _toastText = MutableLiveData<HelperToast<String>>()
    val toastText: LiveData<HelperToast<String>> = _toastText

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            dataSource.forgotPassword(ForgotPassword(email))
            sendVerificationEmail(email)
        }
    }

    fun sendVerificationCode(email: String, verificationCode: String) {
        viewModelScope.launch {
            dataSource.verifyCode(Verify(email, verificationCode))
        }
    }

    private fun sendVerificationEmail(email: String) {
        val verificationCode = generateVerificationCode()
        val username = email.substringBefore('@')
        EmailUtil.sendVerificationCode(email, verificationCode, username)
    }

    private fun generateVerificationCode(): String {
        return UUID.randomUUID().toString()
    }
}
