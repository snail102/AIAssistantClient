package ru.anydevprojects.aiassistant.feature.chat.data

import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.aiassistant.feature.chat.data.dao.ChatDao
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatEntity

class ChatLocalDataSource(private val chatDao: ChatDao) {

    suspend fun saveChats(chats: List<ChatEntity>) {
        chatDao.insertAllChats(chats)
    }

    fun getChats(): Flow<List<ChatEntity>> = chatDao.getAllChats()
}
