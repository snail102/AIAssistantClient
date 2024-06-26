package ru.anydevprojects.aiassistant.feature.chat.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.anydevprojects.aiassistant.feature.chat.data.mappers.toDomain
import ru.anydevprojects.aiassistant.feature.chat.data.mappers.toEntity
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatHistoryDto
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatMessageHistoryDto
import ru.anydevprojects.aiassistant.feature.chat.data.models.ChatRequest
import ru.anydevprojects.aiassistant.feature.chat.data.models.MessageResponse
import ru.anydevprojects.aiassistant.feature.chat.domain.ChatRepository
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatHistory
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessage
import ru.anydevprojects.aiassistant.feature.chat.domain.models.ChatMessageHistory
import ru.anydevprojects.aiassistant.feature.chat.domain.models.MessageHistory

private const val CHAT_PATH = "chat/completions"
private const val CHAT_ALL_PATH = "chat_all"
private const val CHAT_MESSAGE_HISTORY_PATH = "chat_message_history"
private const val CHAT_ID_PARAMETER_CHAT_MESSAGE_HISTORY = "chat_id"

class ChatRepositoryImpl(
    private val httpClient: HttpClient,
    private val chatLocalDataSource: ChatLocalDataSource
) : ChatRepository {

    override val chatHistory: Flow<List<ChatHistory>>
        get() = chatLocalDataSource.getChats()
            .map { chatHistoryList ->
                chatHistoryList.map { chatHistory -> chatHistory.toDomain() }
            }

    override fun getMessages(chatId: Int): Flow<List<MessageHistory>> =
        chatLocalDataSource.getMessagesByChatId(chatId = chatId).map { messages ->
            messages.map {
                it.toDomain()
            }
        }

    override suspend fun getChatMessageHistory(chatId: Int): Result<ChatMessageHistory> =
        kotlin.runCatching {
            val response: HttpResponse = httpClient.get(CHAT_MESSAGE_HISTORY_PATH) {
                parameter(key = CHAT_ID_PARAMETER_CHAT_MESSAGE_HISTORY, value = chatId)
            }
            val chatMessageHistoryDto = response.body<ChatMessageHistoryDto>()
            ChatMessageHistory(
                chatId = chatMessageHistoryDto.chatId,
                messages = chatMessageHistoryDto.messages.map {
                    MessageHistory(
                        id = it.id,
                        isUserRole = it.isUserRole,
                        content = it.content
                    )
                }
            )
        }

    override suspend fun getChatAll(): Result<List<ChatHistory>> = kotlin.runCatching {
        val response: HttpResponse = httpClient.get(CHAT_ALL_PATH)
        val chatAllResponse = response.body<List<ChatHistoryDto>>()
        val chats = chatAllResponse.map {
            ChatHistory(
                chatId = it.chatId,
                firstMessagePreview = it.firstMessagePreview,
                createdDate = it.createdDate,
                lastChangedDate = it.lastChangedDate
            )
        }
        chatLocalDataSource.saveChats(chats.map { it.toEntity() })

        chats
    }

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
