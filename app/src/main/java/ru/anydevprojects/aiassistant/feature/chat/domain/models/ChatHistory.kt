package ru.anydevprojects.aiassistant.feature.chat.domain.models

data class ChatHistory(
    val chatId: Int,
    val firstMessagePreview: String,
    val createdDate: Long,
    val lastChangedDate: Long
)
