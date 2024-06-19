package ru.anydevprojects.aiassistant.feature.chat.domain

import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatHistory
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessage
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessageHistory

interface ChatRepository {

    suspend fun getChatMessageHistory(chatId: Int): Result<ChatMessageHistory>

    suspend fun getChatAll(): Result<List<ChatHistory>>

    suspend fun sendMessage(chatId: Int, message: String): Result<List<ChatMessage>>
}
