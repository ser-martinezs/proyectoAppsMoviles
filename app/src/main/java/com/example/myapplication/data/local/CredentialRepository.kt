package com.example.myapplication.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.data.local.CredentialRepository.Companion.PASSWORD
import com.example.myapplication.data.local.CredentialRepository.Companion.USER_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore("user_prefs")

abstract class IdkCredentialRepository{
    abstract suspend fun saveUserID(id: Long)
    abstract suspend fun savePassword(password: String)
    abstract suspend fun getID(): Long
    abstract suspend fun getPassword(): String
    abstract suspend fun clearSession()
}

class CredentialRepository(private val context: Context) : IdkCredentialRepository(){
    companion object {
        private val USER_ID = longPreferencesKey("user_id")
        private val PASSWORD = stringPreferencesKey("password")
    }

    private val passwordFlow: Flow<String?> = context.dataStore.data.map { prefs -> prefs[PASSWORD] }
    private val userFlow: Flow<Long?> = context.dataStore.data.map { prefs -> prefs[USER_ID] }

    override suspend fun saveUserID(id: Long) {
        context.dataStore.edit { prefs -> prefs[USER_ID] = id }
    }
    override suspend fun savePassword(password: String) {
        context.dataStore.edit { prefs -> prefs[PASSWORD] = password }
    }
    override suspend fun getID(): Long{
        return userFlow.first()?:-1L
    }
    override suspend fun getPassword(): String{
        return passwordFlow.first()?:""
    }

    override suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(PASSWORD)
        }
    }
}

class TestableCredentialRepository() : IdkCredentialRepository(){
    private var password :String? = null
    private var user : Long? = null

    override suspend fun saveUserID(id: Long) {user=id}
    override suspend fun savePassword(password: String) {this.password=password }
    override suspend fun getID(): Long{
        return user?:-1L
    }
    override suspend fun getPassword(): String{
        return password?:""
    }

    override suspend fun clearSession() {
        user=null
        password=null
    }
}