package ru.anydevprojects.aiassistant.feature.chat.domain

import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatHistory
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessage
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessageHistory
import ru.anydevprojects.aiassistant.feature.chat.domain.models.MessageHistory

interface ChatRepository {

    val chatHistory: Flow<List<ChatHistory>>

    fun getMessages(chatId: Int): Flow<List<MessageHistory>>

    suspend fun getChatMessageHistory(chatId: Int): Result<ChatMessageHistory>

    suspend fun getChatAll(): Result<List<ChatHistory>>

    suspend fun sendMessage(chatId: Int, message: String): Result<List<ChatMessage>>
}
