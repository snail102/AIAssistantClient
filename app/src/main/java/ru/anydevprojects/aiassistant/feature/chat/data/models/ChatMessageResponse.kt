package ru.anydevprojects.aiassistant.feature.chat.data.models

import kotlinx.serialization.Serializable

@Serializable
class ChatMessageResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<AnswerMessage>,
    val usage: UsageToken
)
