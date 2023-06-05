package com.efrivahmi.elaborate.data.repository

import androidx.paging.ExperimentalPagingApi
import com.efrivahmi.elaborate.data.api.ApiService
import com.efrivahmi.elaborate.data.model.UserPreference
import com.efrivahmi.elaborate.local.room.LabDatabase

@OptIn(ExperimentalPagingApi::class)
class DataSource private constructor(
    private val pref: UserPreference,
    private val apiService: ApiService,
    private val labDatabase: LabDatabase
) {

    companion object {
        private const val TAG = "DataSource"
        @Volatile
        private var instance: DataSource? = null
        fun getInstance(
            pref: UserPreference,
            apiService: ApiService,
            labDatabase: LabDatabase
        ): DataSource =
            instance ?: synchronized(this) {
                instance ?: DataSource(pref, apiService, labDatabase).also { instance = it }
            }
    }
}