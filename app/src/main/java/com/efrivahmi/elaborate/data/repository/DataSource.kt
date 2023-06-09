package com.efrivahmi.elaborate.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.efrivahmi.elaborate.data.api.ApiService
import com.efrivahmi.elaborate.data.model.ForgotPassword
import com.efrivahmi.elaborate.data.model.ResetPassword
import com.efrivahmi.elaborate.data.model.UserLogin
import com.efrivahmi.elaborate.data.model.UserModel
import com.efrivahmi.elaborate.data.preference.UserPreference
import com.efrivahmi.elaborate.data.model.UserRegister
import com.efrivahmi.elaborate.data.model.Verify
import com.efrivahmi.elaborate.data.response.FpResponse
import com.efrivahmi.elaborate.data.response.RpResponse
import com.efrivahmi.elaborate.data.response.SignIn
import com.efrivahmi.elaborate.data.response.SignUp
import com.efrivahmi.elaborate.data.response.VerifyCode
import com.efrivahmi.elaborate.utils.HelperToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSource private constructor(
    private val pref: UserPreference,
    private val apiService: ApiService
) {
    companion object {
        private const val TAG = "DataSource"
        @Volatile
        private var instance: DataSource? = null
        fun getInstance(
            pref: UserPreference,
            apiService: ApiService
        ): DataSource =
            instance ?: synchronized(this) {
                instance ?: DataSource(pref, apiService).also { instance = it }
            }
    }

    private val _login = MutableLiveData<SignIn>()
    val login: LiveData<SignIn> = _login

    private val _registerResult = MutableLiveData<SignUp>()
    val registerResult: LiveData<SignUp> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<HelperToast<String>>()
    val toastText: LiveData<HelperToast<String>> = _toastText

    private val _forgot = MutableLiveData<FpResponse>()
    val forgot: LiveData<FpResponse> = _forgot

    private val _verify = MutableLiveData<VerifyCode>()
    val verify: LiveData<VerifyCode> = _verify

    private val _resetPasswordResult = MutableLiveData<RpResponse>()
    val resetPasswordResult: LiveData<RpResponse> = _resetPasswordResult

    fun registerClient(user: UserRegister) {
        _isLoading.value = true
        val client = apiService.signUp(user)
        client.enqueue(object : Callback<SignUp> {
            override fun onResponse(call: Call<SignUp>, response: Response<SignUp>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _registerResult.value = response.body()
                    _toastText.value = HelperToast(response.body()?.message.toString())
                } else {
                    _toastText.value = HelperToast(response.message().toString())
                    Log.e(TAG, "on Failure!: ${response.message()}, ${response.body()?.message.toString()}")
                }
            }

            override fun onFailure(call: Call<SignUp>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = HelperToast(t.message.toString())
                Log.e(TAG, "Failed SignUp: ${t.message.toString()}")
            }
        })
    }

    fun uploadLogin(user: UserLogin) {
        _isLoading.value = true
        val client = apiService.signIn(user)
        client.enqueue(object : Callback<SignIn> {
            override fun onResponse(call: Call<SignIn>, response: Response<SignIn>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _login.value = response.body()
                    _toastText.value = HelperToast(response.body()?.message.toString())
                } else {
                    _toastText.value = HelperToast(response.message().toString())
                    Log.e(TAG, "on Failure!: ${response.message()}, ${response.body()?.message.toString()}")
                }
            }

            override fun onFailure(call: Call<SignIn>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = HelperToast(t.message.toString())
                Log.e(TAG, "Failed Login: ${t.message.toString()}")
            }
        })
    }

    fun forgotPassword(user: ForgotPassword) {
        _isLoading.value = true
        val callForgotPassword = apiService.forgotPassword(user)
        callForgotPassword.enqueue(object : Callback<FpResponse> {
            override fun onResponse(call: Call<FpResponse>, response: Response<FpResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _forgot.value = response.body()
                    _toastText.value = HelperToast(response.body()?.message.toString())
                } else {
                    _toastText.value = HelperToast(response.message().toString())
                    Log.e(TAG, "Failed to send forgot password request: ${response.message()}, ${response.body()?.message.toString()}")
                }
            }

            override fun onFailure(call: Call<FpResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = HelperToast(t.message.toString())
                Log.e(TAG, "Failed to send forgot password request: ${t.message.toString()}")
            }
        })
    }

    fun verifyCode(user: Verify) {
        _isLoading.value = true
        val callVerifyCode = apiService.verifyCode(user)
        callVerifyCode.enqueue(object : Callback<VerifyCode> {
            override fun onResponse(call: Call<VerifyCode>, response: Response<VerifyCode>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _verify.value = response.body()
                    _toastText.value = HelperToast(response.body()?.message.toString())
                } else {
                    _toastText.value = HelperToast(response.message().toString())
                    Log.e(TAG, "Failed to verify code: ${response.message()}, ${response.body()?.message.toString()}")
                }
            }

            override fun onFailure(call: Call<VerifyCode>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = HelperToast(t.message.toString())
                Log.e(TAG, "Failed to verify code: ${t.message.toString()}")
            }
        })
    }

    fun getPatient(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun saveUser(session: UserLogin) {
        pref.saveUser(session)
    }

    suspend fun login() {
        pref.login()
    }

    suspend fun logout() {
        pref.logout()
    }
}
