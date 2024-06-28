package ru.anydevprojects.aiassistant.feature.chat.data

import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.aiassistant.feature.chat.data.dao.ChatDao
import ru.anydevprojects.aiassistant.feature.chat.data.dao.MessageChatDao
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatEntity
import ru.anydevprojects.aiassistant.feature.chat.data.models.MessageChatEntity

class ChatLocalDataSource(
    private val chatDao: ChatDao,
    private val messageChatDao: MessageChatDao
) {

    suspend fun saveChats(chats: List<ChatEntity>) {
        chatDao.insertAllChats(chats)
    }

    suspend fun saveMessages(messages: List<MessageChatEntity>) {
        messageChatDao.insertMessages(messages)
    }

    fun getChats(): Flow<List<ChatEntity>> = chatDao.getAllChats()

    fun getMessagesByChatId(chatId: Int): Flow<List<MessageChatEntity>> =
        messageChatDao.getMessagesByChatId(chatId = chatId)
}
