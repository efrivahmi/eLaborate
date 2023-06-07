package com.efrivahmi.elaborate.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.efrivahmi.elaborate.data.api.ApiConfig
import com.efrivahmi.elaborate.data.model.UserPreference
import com.efrivahmi.elaborate.data.repository.DataSource

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun labRepository(context: Context): DataSource {
        val preferences = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getConfigApi()
        return DataSource.getInstance(preferences, apiService)
    }
}