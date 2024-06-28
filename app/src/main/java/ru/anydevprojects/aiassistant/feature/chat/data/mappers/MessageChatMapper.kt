package ru.anydevprojects.aiassistant.feature.chat.data.mappers

import ru.anydevprojects.aiassistant.feature.chat.data.models.MessageChatEntity
import ru.anydevprojects.aiassistant.feature.chat.domain.models.MessageHistory

fun MessageChatEntity.toDomain(): MessageHistory = MessageHistory(
    id = this.id,
    isUserRole = this.isUserRole,
    content = this.content
)

fun MessageHistory.toEntity(chatId: Int): MessageChatEntity = MessageChatEntity(
    id = this.id,
    chatId = chatId,
    isUserRole = this.isUserRole,
    content = this.content
)
