package ru.anydevprojects.aiassistant.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.anydevprojects.aiassistant.feature.chat.domain.ChatRepository
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatIntent
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatMessageUi
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatState

class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ChatState>(ChatState())
    val state = _state.asStateFlow()

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.OnChangeMessage -> updateInputtedMessage(intent.message)
            ChatIntent.SendMessage -> sendMessage()
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
                        isLoading = true,
                        messages = it.messages + ChatMessageUi.UserChatMessage(
                            content = inputtedMessage
                        ),
                        errorMessage = ""
                    )
                }
                chatRepository.sendMessage(message = inputtedMessage)
                    .onSuccess { chatMessages ->
                        _state.update { lastState ->
                            lastState.copy(
                                isLoading = false,
                                messages = lastState.messages + chatMessages.map { message ->
                                    ChatMessageUi.AssistantChatMessage(content = message.content)
                                },
                                errorMessage = ""
                            )
                        }
                    }
                    .onFailure { throwable ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = throwable.message ?: "Unknown error"
                            )
                        }
                    }
            }
        }
    }
}
