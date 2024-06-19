package ru.anydevprojects.aiassistant.feature.chat.presentation

import ru.anydevprojects.aiassistant.feature.chat.domain.models.MessageHistory
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.MessageUi

fun MessageHistory.toUi(): MessageUi {
    return if (this.isUserRole) {
        MessageUi.UserMessage(
            id = this.id,
            content = this.content
        )
    } else {
        MessageUi.AssistantMessage(
            id = this.id,
            content = this.content
        )
    }
}
