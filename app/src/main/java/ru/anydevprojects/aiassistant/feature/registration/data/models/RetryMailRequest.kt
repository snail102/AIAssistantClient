package ru.anydevprojects.aiassistant.feature.registration.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RetryMailRequest(
    val login: String
)
