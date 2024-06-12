package ru.anydevprojects.aiassistant.feature.registration.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val login: String,
    val password: String,
    val email: String
)
