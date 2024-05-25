package ru.anydevprojects.aiassistant.feature.chat.presentation.models

sealed interface ChatIntent {
    data object SendMessage : ChatIntent
    data class OnChangeMessage(val message: String) : ChatIntent
}
