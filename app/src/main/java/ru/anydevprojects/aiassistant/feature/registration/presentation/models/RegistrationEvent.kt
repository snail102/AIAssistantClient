package ru.anydevprojects.aiassistant.feature.registration.presentation.models

sealed interface RegistrationEvent {
    data object NavigateToConfirmationEmail : RegistrationEvent
}
