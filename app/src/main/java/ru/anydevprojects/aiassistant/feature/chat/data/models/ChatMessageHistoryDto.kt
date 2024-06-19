package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageHistoryDto(
    val chatId: Int,
    val messages: List<MessageHistoryDto>
)
