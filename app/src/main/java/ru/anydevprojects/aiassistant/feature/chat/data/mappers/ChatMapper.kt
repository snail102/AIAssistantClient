package ru.anydevprojects.aiassistant.feature.chat.data.mappers

import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatEntity
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatHistory

fun ChatHistory.toEntity(): ChatEntity = ChatEntity(
    id = this.chatId,
    firstMessagePreview = this.firstMessagePreview,
    createdDate = this.createdDate,
    lastChangedDate = this.lastChangedDate
)

fun ChatEntity.toDomain(): ChatHistory = ChatHistory(
    chatId = this.id,
    firstMessagePreview = this.firstMessagePreview,
    createdDate = this.createdDate,
    lastChangedDate = this.lastChangedDate
)
