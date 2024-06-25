package ru.anydevprojects.aiassistant.feature.chat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey
    val id: Int,
    val firstMessagePreview: String,
    val createdDate: Long,
    val lastChangedDate: Long
)
