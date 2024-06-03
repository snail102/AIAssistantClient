package ru.anydevprojects.aiassistant.feature.authorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.anydevprojects.aiassistant.feature.authorization.domain.AuthorizationRepository
import ru.anydevprojects.aiassistant.feature.authorization.presentation.models.AuthorizationEvent
import ru.anydevprojects.aiassistant.feature.authorization.presentation.models.AuthorizationIntent
import ru.anydevprojects.aiassistant.feature.authorization.presentation.models.AuthorizationState

class AuthorizationViewModel(
    private val authorizationRepository: AuthorizationRepository
) : ViewModel() {

    private val _state: MutableStateFlow<AuthorizationState> =
        MutableStateFlow(AuthorizationState())
    val state = _state.map {
        if (it.login.isNotBlank() && it.password.isNotBlank()) {
            it.copy(enabledAuthBtn = true)
        } else {
            it
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        AuthorizationState()
    )

    private val _event: Channel<AuthorizationEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: AuthorizationIntent) {
        when (intent) {
            AuthorizationIntent.OnAuthClick -> auth()
            is AuthorizationIntent.OnChangeLogin -> changeLogin(intent.newLoginValue)
            is AuthorizationIntent.OnChangePassword -> changePassword(intent.newPasswordValue)
        }
    }

    private fun auth() {
        viewModelScope.launch {
            val login = _state.value.login
            val password = _state.value.password

            if (login.isNotBlank() && password.isNotBlank()) {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                authorizationRepository.auth(
                    login = login,
                    password = password
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
                }
            }
        }
    }

    private fun changeLogin(newValue: String) {
        _state.update {
            it.copy(
                login = newValue
            )
        }
    }

    private fun changePassword(newValue: String) {
        _state.update {
            it.copy(
                password = newValue
            )
        }
    }

    private fun sendEvent(event: AuthorizationEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }
}
