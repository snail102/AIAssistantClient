package ru.anydevprojects.aiassistant.feature.chat.presentation.models

data class ChatState(
    val isLoading: Boolean = false,
    val inputtedMessage: String = "",
    val enabledSendBtn: Boolean = false,
    val messages: List<ChatMessageUi> = emptyList(),
    val errorMessage: String = ""
)
