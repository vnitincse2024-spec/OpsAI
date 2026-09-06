package com.example.opsai.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val team: String?
)

enum class UserRole {
    ADMIN, MANAGER, ENGINEER, VIEWER
}

data class AuthToken(
    val token: String,
    val refreshToken: String?,
    val expiresAt: Long
)
