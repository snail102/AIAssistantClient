package ru.anydevprojects.aiassistant.feature.registration.presentation.models

sealed interface RegistrationEvent {
    data class NavigateToConfirmationEmail(val login: String) : RegistrationEvent
}
