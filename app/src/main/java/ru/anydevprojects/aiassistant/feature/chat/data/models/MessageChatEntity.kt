package ru.anydevprojects.aiassistant.feature.chat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_chat")
data class MessageChatEntity(
    @PrimaryKey
    val id: Int,
    val chatId: Int,
    val isUserRole: Boolean,
    val content: String
)
