package ru.anydevprojects.aiassistant.core.network

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
