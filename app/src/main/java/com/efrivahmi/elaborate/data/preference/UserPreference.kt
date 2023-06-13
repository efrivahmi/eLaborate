package com.efrivahmi.elaborate.data.preference


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.efrivahmi.elaborate.data.model.UserModel
import com.efrivahmi.elaborate.data.response.VerifyCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){
    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERID_KEY = stringPreferencesKey("userId")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("confirmPassword")
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
                preferences[USERID_KEY] ?:"",
                preferences[USERNAME_KEY] ?:"",
                preferences[EMAIL_KEY] ?:"",
                preferences[TOKEN_KEY] ?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USERID_KEY] = user.userId
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[STATE_KEY] = user.isLogin
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

    suspend fun saveResetToken(verifyCode: VerifyCode) {
        dataStore.edit { preferences ->
            preferences[RESET_TOKEN_KEY] = verifyCode.resetToken
            preferences[USERID_KEY] = verifyCode.userId
            preferences[USERNAME_KEY] = verifyCode.username
            preferences[EMAIL_KEY] = verifyCode.email
        }
    }

    fun getResetToken(): Flow<VerifyCode> {
        return dataStore.data.map { preferences ->
            VerifyCode(
                preferences[RESET_TOKEN_KEY] ?: "",
                false,
                "Token generated",
                preferences[USERID_KEY] ?: "",
                preferences[USERNAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: ""
            )
        }
    }

}
