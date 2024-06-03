package ru.anydevprojects.aiassistant.feature.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.anydevprojects.aiassistant.feature.registration.domain.RegistrationRepository
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.ConfirmEmailEvent
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.ConfirmEmailIntent
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.ConfirmEmailState

class ConfirmEmailViewModel(
    private val registrationRepository: RegistrationRepository,
    private val login: String
) : ViewModel() {

    private val _state: MutableStateFlow<ConfirmEmailState> = MutableStateFlow(ConfirmEmailState())
    val state = _state.asStateFlow()

    private val _event: Channel<ConfirmEmailEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: ConfirmEmailIntent) {
        when (intent) {
            is ConfirmEmailIntent.OnChangeCode -> changeCode(intent.newCode)
            ConfirmEmailIntent.OnRetrySendMailBtnClick -> sendRetryMail()
        }
    }

    private fun changeCode(newCode: String) {
        _state.update {
            it.copy(
                code = newCode
            )
        }
    }

    private fun sendRetryMail() {
        viewModelScope.launch {
            // TODO send request for send new code on email
        }
    }

    private fun sendCode(code: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            registrationRepository.confirmEmail(
                login = login,
                confirmCode = code
            ).onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }

                _event.send(ConfirmEmailEvent.IncorrectCode)
            }
        }
    }
}
