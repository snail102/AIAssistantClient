package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val role: String,
    val content: String
)
