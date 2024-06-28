package ru.anydevprojects.aiassistant.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.anydevprojects.aiassistant.feature.chat.data.dao.ChatDao
import ru.anydevprojects.aiassistant.feature.chat.data.dao.MessageChatDao
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatEntity

@Database(entities = [(ChatEntity::class)], version = 1)
abstract class AssistantDatabase : RoomDatabase() {
    abstract fun getChatDao(): ChatDao
    abstract fun getMessageChatDao(): MessageChatDao
}

fun createDataBase(applicationContext: Context): AssistantDatabase = Room.databaseBuilder(
    applicationContext,
    AssistantDatabase::class.java,
    "assistant_database"
).fallbackToDestructiveMigration().build()

fun provideChatDao(assistantDatabase: AssistantDatabase): ChatDao = assistantDatabase.getChatDao()

fun provideMessageChatDao(assistantDatabase: AssistantDatabase): MessageChatDao =
    assistantDatabase.getMessageChatDao()
