package ru.anydevprojects.aiassistant.feature.registration.presentation

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
import ru.anydevprojects.aiassistant.feature.registration.domain.RegistrationRepository
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.RegistrationEvent
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.RegistrationIntent
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.RegistrationState

class RegistrationViewModel(
    private val registrationRepository: RegistrationRepository
) : ViewModel() {

    private val _state: MutableStateFlow<RegistrationState> = MutableStateFlow(
        RegistrationState()
    )
    val state = _state.map {
        it.copy()
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        RegistrationState()
    )

    private val _event: Channel<RegistrationEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: RegistrationIntent) {
        when (intent) {
            is RegistrationIntent.OnChangeConfirmPassword -> changeConfirmPassword(
                intent.newConfirmPassword
            )
            is RegistrationIntent.OnChangeEmail -> changeEmail(intent.newEmail)
            is RegistrationIntent.OnChangeLogin -> changeLogin(intent.newLogin)
            is RegistrationIntent.OnChangePassword -> changePassword(intent.newPassword)
            RegistrationIntent.OnRegistrationBtnClick -> registration()
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

    private fun changeEmail(newValue: String) {
        _state.update {
            it.copy(
                email = newValue
            )
        }
    }

    private fun changeConfirmPassword(newValue: String) {
        _state.update {
            it.copy(
                confirmPassword = newValue
            )
        }
    }

    private fun registration() {
        viewModelScope.launch {
            val login = _state.value.login
            val password = _state.value.password
            val confirmPassword = _state.value.confirmPassword
            val email = _state.value.email

            if (login.isNotBlank() && password.isNotBlank() && password == confirmPassword && email.isNotBlank()) {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                registrationRepository.register(
                    login = login,
                    password = password,
                    email = email
                ).onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }

                    _event.send(RegistrationEvent.NavigateToConfirmationEmail)
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
}
