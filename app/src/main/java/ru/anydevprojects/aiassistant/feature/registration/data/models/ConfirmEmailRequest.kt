package ru.anydevprojects.aiassistant.feature.registration.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmEmailRequest(
    val login: String,
    val confirmCode: String
)
