package com.efrivahmi.elaborate.data.api.ml

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.efrivahmi.elaborate.data.api.ApiService
import com.efrivahmi.elaborate.data.model.Diagnose
import com.efrivahmi.elaborate.data.preference.UserPreference
import com.efrivahmi.elaborate.data.repository.DataSource
import com.efrivahmi.elaborate.data.response.DiagnoseResponse
import com.efrivahmi.elaborate.utils.HelperToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSourDiagnose private constructor(
    private val pref: UserPreferenceMl,
    private val apiServiceMl: ApiServiceMl
) {
    companion object {
        private const val TAG = "DataSourceMl"
        @Volatile
        private var instance: DataSourDiagnose? = null
        fun getInstance(
            pref: UserPreferenceMl,
            apiServiceMl: ApiServiceMl
        ): DataSourDiagnose =
            instance ?: synchronized(this) {
                instance ?: DataSourDiagnose(pref, apiServiceMl).also { instance = it }
            }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<HelperToast<String>>()
    val toastText: LiveData<HelperToast<String>> = _toastText

    private val _diagnose = MutableLiveData<DiagnoseResponse>()
    val diagnose: LiveData<DiagnoseResponse> = _diagnose

    fun diagnoseClient(diagnose: Diagnose) {
        _isLoading.value = true
        val client = apiServiceMl.submitDiagnosticForm(diagnose)
        client.enqueue(object : Callback<DiagnoseResponse> {
            override fun onResponse(call: Call<DiagnoseResponse>, response: Response<DiagnoseResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _diagnose.value = response.body()
                    _toastText.value = HelperToast(response.body()?.message.toString())
                } else {
                    _toastText.value = HelperToast(response.message().toString())
                    Log.e(DataSource.TAG, "on Failure!: ${response.message()}, ${response.body()?.message.toString()}")
                }
            }

            override fun onFailure(call: Call<DiagnoseResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = t.localizedMessage?.let { HelperToast(it) }
                Log.e(TAG, "Failed Upload Data: ${t.localizedMessage}")
            }
        })
    }
}