package com.example.opsai.data.remote.dto

import com.example.opsai.domain.model.*
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("team") val team: String?
) {
    fun toUser(): User {
        return User(
            id = id,
            name = name,
            email = email,
            role = try { UserRole.valueOf(role.uppercase()) } catch(e: Exception) { UserRole.VIEWER },
            team = team
        )
    }
}

data class AuthTokenDto(
    @SerializedName("token") val token: String,
    @SerializedName("refresh_token") val refresh_token: String?,
    @SerializedName("expires_at") val expires_at: Long
) {
    fun toAuthToken(): AuthToken {
        return AuthToken(
            token = token,
            refreshToken = refresh_token,
            expiresAt = expires_at
        )
    }
}
