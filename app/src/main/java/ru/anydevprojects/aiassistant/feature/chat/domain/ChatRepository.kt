package ru.anydevprojects.aiassistant.feature.chat.domain

import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessage

interface ChatRepository {

    suspend fun sendMessage(message: String): Result<List<ChatMessage>>
}
