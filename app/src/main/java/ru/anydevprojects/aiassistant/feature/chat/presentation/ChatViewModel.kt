package ru.anydevprojects.aiassistant.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.anydevprojects.aiassistant.feature.chat.domain.ChatRepository
import ru.anydevprojects.aiassistant.feature.chat.presentation.mappers.toUi
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatIntent
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatState
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.MessageUi

class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ChatState>(ChatState())
    val state = _state.asStateFlow()

    private var chatId: Int = 0

    init {
        loadChatAll()
    }

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.OnChangeMessage -> updateInputtedMessage(intent.message)
            ChatIntent.SendMessage -> sendMessage()
            is ChatIntent.OnChatHistoryClick -> selectChatFromHistory(intent.chatId)
        }
    }

    private fun selectChatFromHistory(chatId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingCurrentChatMessages = true
                )
            }

            chatRepository.getChatMessageHistory(chatId = chatId).onSuccess { chatMessageHistory ->
                this@ChatViewModel.chatId = chatMessageHistory.chatId
                _state.update {
                    it.copy(
                        isLoadingCurrentChatMessages = false,
                        messages = chatMessageHistory.messages.map { messageHistory ->
                            messageHistory.toUi()
                        }
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoadingCurrentChatMessages = false,
                        messages = emptyList()
                    )
                }
            }
        }
    }

    private fun loadChatAll() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingChatAll = true
                )
            }
            chatRepository.getChatAll().onSuccess { chatHistoryList ->
                _state.update {
                    it.copy(
                        isLoadingChatAll = false,
                        chatHistory = chatHistoryList
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoadingChatAll = false
                    )
                }
            }
        }
    }

    private fun updateInputtedMessage(message: String) {
        _state.update {
            it.copy(
                inputtedMessage = message,
                enabledSendBtn = message.isNotBlank()
            )
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            val inputtedMessage = _state.value.inputtedMessage
            if (inputtedMessage.isNotBlank()) {
                _state.update {
                    it.copy(
                        isLoadingCurrentChatMessages = true,
                        messages = it.messages + MessageUi.UserMessage(
                            id = (it.messages.lastOrNull()?.id ?: 0) + 1,
                            content = inputtedMessage
                        ),
                        inputtedMessage = "",
                        errorMessage = ""
                    )
                }
                chatRepository.sendMessage(chatId = chatId, message = inputtedMessage)
                    .onSuccess { chatMessages ->
                        chatId = chatMessages.last().chatId
                        _state.update { lastState ->
                            lastState.copy(
                                isLoadingCurrentChatMessages = false,
                                messages = lastState.messages + chatMessages.map { message ->
                                    MessageUi.AssistantMessage(
                                        id = (lastState.messages.lastOrNull()?.id ?: 0) + 1,
                                        content = message.content
                                    )
                                },
                                errorMessage = ""
                            )
                        }
                    }
                    .onFailure { throwable ->
                        _state.update {
                            it.copy(
                                isLoadingCurrentChatMessages = false,
                                errorMessage = throwable.message ?: "Unknown error"
                            )
                        }
                    }
            }
        }
    }
}
