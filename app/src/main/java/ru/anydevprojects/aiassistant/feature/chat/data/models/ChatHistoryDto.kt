package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatHistoryDto(
    val chatId: Int,
    val firstMessagePreview: String,
    val createdDate: Long,
    val lastChangedDate: Long
)
