package com.efrivahmi.elaborate.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.efrivahmi.elaborate.data.api.ApiConfig
import com.efrivahmi.elaborate.data.api.ml.ApiConfigMl
import com.efrivahmi.elaborate.data.api.ml.DataSourDiagnose
import com.efrivahmi.elaborate.data.api.ml.UserPreferenceMl
import com.efrivahmi.elaborate.data.preference.UserPreference
import com.efrivahmi.elaborate.data.repository.DataSource

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun labRepository(context: Context): DataSource {
        val preferences = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getConfigApi()
        return DataSource.getInstance(preferences, apiService)
    }

    fun labDiagnose(context: Context): DataSourDiagnose {
        val preferenceMl = UserPreferenceMl.getInstance(context.dataStore)
        val apiServiceMl = ApiConfigMl.getDiagnose()
        return DataSourDiagnose.getInstance(preferenceMl, apiServiceMl)
    }
}