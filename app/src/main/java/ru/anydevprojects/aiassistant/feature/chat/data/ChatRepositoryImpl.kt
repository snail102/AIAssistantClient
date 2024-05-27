package ru.anydevprojects.aiassistant.feature.chat.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.anydevprojects.aiassistant.core.network.BASE_URL
import ru.anydevprojects.aiassistant.core.network.client
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatMessageResponse
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatRequest
import ru.anydevprojects.aiassistant.feature.chat.domain.ChatRepository
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessage

private const val CHAT_PATH = "chat/completions"

class ChatRepositoryImpl(
    private val httpClient: HttpClient
) : ChatRepository {

    override suspend fun sendMessage(message: String): Result<List<ChatMessage>> {
        // return Result.success(listOf(ChatMessage(Random.nextLong().toString())))
        return kotlin.runCatching {
            val chatRequest: ChatRequest = ChatRequest(
                content = message
            )
            val response: HttpResponse = client.post(BASE_URL + CHAT_PATH) {
                contentType(ContentType.Application.Json)
                header("User", "1234")
                setBody(chatRequest)
            }

            val chatResponse = response.body<ChatMessageResponse>()
            listOf(
                ChatMessage(
                    content = chatResponse.choices.first().message.content
                )
            )
        }
    }
}
