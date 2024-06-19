package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageHistoryDto(
    val id: Int,
    val isUserRole: Boolean,
    val content: String
)
