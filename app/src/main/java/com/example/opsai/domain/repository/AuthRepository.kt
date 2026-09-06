package com.example.opsai.domain.repository

import com.example.opsai.domain.model.*
import com.example.opsai.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<AuthToken>
    suspend fun logout()
    fun getAuthenticatedUser(): Flow<User?>
    fun getAuthToken(): Flow<String?>
    suspend fun saveAuthToken(token: String)
}
