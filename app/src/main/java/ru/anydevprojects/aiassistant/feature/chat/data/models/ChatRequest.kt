package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val chatId: Int,
    val content: String
)
