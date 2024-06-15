package ru.anydevprojects.aiassistant.feature.chat.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatRequest
import ru.anydevprojects.aiassistant.feature.chat.data.models.MessageResponse
import ru.anydevprojects.aiassistant.feature.chat.domain.ChatRepository
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessage

private const val CHAT_PATH = "chat/completions"

class ChatRepositoryImpl(
    private val httpClient: HttpClient
) : ChatRepository {

    override suspend fun sendMessage(chatId: Int, message: String): Result<List<ChatMessage>> {
        // return Result.success(listOf(ChatMessage(Random.nextLong().toString())))
        return kotlin.runCatching {
            val chatRequest: ChatRequest = ChatRequest(
                chatId = chatId,
                content = message
            )
            val response: HttpResponse = httpClient.post(CHAT_PATH) {
                setBody(chatRequest)
            }

            val chatResponse = response.body<MessageResponse>()
            listOf(
                ChatMessage(
                    chatId = chatResponse.chatId,
                    content = chatResponse.content
                )
            )
        }
    }
}
