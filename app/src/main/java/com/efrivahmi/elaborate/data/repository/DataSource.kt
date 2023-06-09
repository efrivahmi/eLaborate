package com.efrivahmi.elaborate.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import com.efrivahmi.elaborate.data.api.ApiService
import com.efrivahmi.elaborate.data.model.UserPreference
import com.efrivahmi.elaborate.data.response.SignUp
//import com.efrivahmi.elaborate.local.room.LabDatabase
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

    private val _registerResult = MutableLiveData<SignUp>()
    val registerResult: LiveData<SignUp> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<HelperToast<String>>()
    val toastText: LiveData<HelperToast<String>> = _toastText

    fun registerClient(username: String, email: String, password: String, confirmPassword: String) {
        _isLoading.value = true
        val client = apiService.signUp(username, email, password, confirmPassword)
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
}
