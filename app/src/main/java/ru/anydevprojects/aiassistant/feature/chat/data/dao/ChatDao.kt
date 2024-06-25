package ru.anydevprojects.aiassistant.feature.chat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatEntity

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllChats(chats: List<ChatEntity>)

    @Query("SELECT * FROM chat")
    fun getAllChats(): Flow<List<ChatEntity>>
}
