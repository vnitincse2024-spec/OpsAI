package com.example.opsai.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.opsai.data.remote.OpsAIApi
import com.example.opsai.domain.model.*
import com.example.opsai.domain.repository.AuthRepository
import com.example.opsai.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: OpsAIApi,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val USER_JSON = stringPreferencesKey("user_json")
    }

    override suspend fun login(email: String, password: String): Resource<AuthToken> {
        return try {
            // Demo mode: allow login with any credentials if API fails or for testing
            if (email == "admin@opsai.com" && password == "admin123") {
                val token = AuthToken("demo_token", null, System.currentTimeMillis() + 3600000)
                saveAuthToken(token.token)
                return Resource.Success(token)
            }
            val response = api.login(mapOf("email" to email, "password" to password))
            val token = response.toAuthToken()
            saveAuthToken(token.token)
            Resource.Success(token)
        } catch (e: Exception) {
            // Fallback for demo
            if (email.isNotBlank() && password.isNotBlank()) {
                val token = AuthToken("demo_token", null, System.currentTimeMillis() + 3600000)
                saveAuthToken(token.token)
                Resource.Success(token)
            } else {
                Resource.Error(e.message ?: "Login failed")
            }
        }
    }

    override suspend fun logout() {
        dataStore.edit { it.remove(PreferencesKeys.AUTH_TOKEN) }
        dataStore.edit { it.remove(PreferencesKeys.USER_JSON) }
    }

    override fun getAuthenticatedUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            // In a real app, parse USER_JSON. Mocking for now.
            User("1", "Admin User", "admin@opsai.com", UserRole.ADMIN, "Operations")
        }
    }

    override fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN]
        }
    }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] = token
        }
    }
}
