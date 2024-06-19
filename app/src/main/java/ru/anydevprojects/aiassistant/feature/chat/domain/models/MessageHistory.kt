package ru.anydevprojects.aiassistant.feature.chat.domain.models

data class MessageHistory(
    val id: Int,
    val isUserRole: Boolean,
    val content: String
)
