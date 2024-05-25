package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerMessage(
    val index: Int,
    val message: MessageDto,
    @SerialName("finish_reason")
    val finishReason: String
)
