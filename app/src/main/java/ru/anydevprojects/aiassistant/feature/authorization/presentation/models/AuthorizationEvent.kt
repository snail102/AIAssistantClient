package ru.anydevprojects.aiassistant.feature.authorization.presentation.models

sealed interface AuthorizationEvent {

    data object NavigateToRegistrationScreen : AuthorizationEvent
}
