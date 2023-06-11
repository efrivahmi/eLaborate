package com.efrivahmi.elaborate.data.preference

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.efrivahmi.elaborate.data.model.UserLogin
import com.efrivahmi.elaborate.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){
    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERNAME_KEY = stringPreferencesKey("username")
        private val TOKEN_KEY = stringPreferencesKey("confirmPassword")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val RESET_TOKEN_KEY = stringPreferencesKey("resetToken")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this){
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USERNAME_KEY] ?:"",
                preferences[TOKEN_KEY] ?:"",
                preferences[EMAIL_KEY] ?:"",
                preferences[PASSWORD_KEY] ?:"",
                preferences[STATE_KEY] ?: false,

            )
        }
    }

    suspend fun saveUser(user: UserLogin) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
        }
    }

    suspend fun saveResetToken(resetToken: String) {
        dataStore.edit { preferences ->
            preferences[RESET_TOKEN_KEY] = resetToken
            Log.d(TAG, "Saved reset token: $resetToken")
        }
    }

    suspend fun getResetToken(): String? {
        return dataStore.data.map { preferences ->
            preferences[RESET_TOKEN_KEY]
        }.firstOrNull()
    }
}