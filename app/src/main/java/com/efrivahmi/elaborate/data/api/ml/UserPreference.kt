package com.efrivahmi.elaborate.data.api.ml

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

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
}