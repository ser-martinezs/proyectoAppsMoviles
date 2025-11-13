package com.example.myapplication.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore("user_prefs")


class CredentialRepository(private val context: Context) {
    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val PASSWORD = stringPreferencesKey("password")
    }

    val dataFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID]
        prefs[PASSWORD]
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { prefs -> prefs[USER_ID] = username }
    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit { prefs -> prefs[PASSWORD] = password }
    }


    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(PASSWORD)
        }

    }


}