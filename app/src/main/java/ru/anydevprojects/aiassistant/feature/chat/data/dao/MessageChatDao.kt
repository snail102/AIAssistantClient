package ru.anydevprojects.aiassistant.feature.chat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.aiassistant.feature.chat.data.models.MessageChatEntity

@Dao
interface MessageChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageChatEntity>)

    @Query("SELECT * FROM message_chat WHERE chatId = :chatId")
    fun getMessagesByChatId(chatId: Int): Flow<List<MessageChatEntity>>
}
