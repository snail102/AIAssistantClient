package ru.anydevprojects.aiassistant.feature.chat.presentation.models

sealed interface ChatMessageUi {
    val content: String

    data class AssistantChatMessage(
        override val content: String
    ) : ChatMessageUi

    data class UserChatMessage(
        override val content: String
    ) : ChatMessageUi
}
