package ru.anydevprojects.aiassistant.feature.authorization.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val login: String,
    val password: String
)
