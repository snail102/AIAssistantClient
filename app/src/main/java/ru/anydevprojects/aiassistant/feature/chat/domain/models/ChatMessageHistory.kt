package ru.anydevprojects.aiassistant.feature.chat.domain.models

data class ChatMessageHistory(
    val chatId: Int,
    val messages: List<MessageHistory>
)
