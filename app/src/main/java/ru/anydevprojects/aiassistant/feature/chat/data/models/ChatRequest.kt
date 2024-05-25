package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val content: String
)
