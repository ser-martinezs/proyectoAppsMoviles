package com.example.myapplication.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore("user_prefs")


class CredentialRepository(private val context: Context) {
    companion object {
        private val USER_ID = longPreferencesKey("user_id")
        private val PASSWORD = stringPreferencesKey("password")
    }

    val passwordFlow: Flow<String?> = context.dataStore.data.map { prefs -> prefs[PASSWORD] }

    val userFlow: Flow<Long?> = context.dataStore.data.map { prefs -> prefs[USER_ID] }

    suspend fun saveUserID(id: Long) {
        context.dataStore.edit { prefs -> prefs[USER_ID] = id }
    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit { prefs -> prefs[PASSWORD] = password }
    }
    suspend fun getID(): Long{
        var id :Long= -1;
        userFlow.collect{storedID ->
            id = storedID ?: -1
        }
        return id;
    }
    suspend fun getPassword(): String{
        var pass :String= "";
        passwordFlow.collect{storedPass ->
            pass = storedPass ?: ""
        }
        return pass;
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(PASSWORD)
        }

    }


}