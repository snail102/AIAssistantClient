package ru.anydevprojects.aiassistant.core.network

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)
