package ru.anydevprojects.aiassistant.feature.chat.presentation.models

sealed interface MessageUi {
    val id: Int
    val content: String

    data class AssistantMessage(
        override val id: Int,
        override val content: String
    ) : MessageUi

    data class UserMessage(
        override val id: Int,
        override val content: String
    ) : MessageUi
}
