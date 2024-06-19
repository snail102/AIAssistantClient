package ru.anydevprojects.aiassistant.feature.chat.presentation.models

import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatHistory

data class ChatState(
    val isLoadingCurrentChatMessages: Boolean = false,
    val isLoadingChatAll: Boolean = false,
    val inputtedMessage: String = "",
    val enabledSendBtn: Boolean = false,
    val messages: List<MessageUi> = emptyList(),
    val chatHistory: List<ChatHistory> = emptyList(),
    val errorMessage: String = ""
)
