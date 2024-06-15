package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val chatId: Int,
    val content: String
)
