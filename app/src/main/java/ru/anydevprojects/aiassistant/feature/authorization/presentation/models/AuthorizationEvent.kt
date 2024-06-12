package ru.anydevprojects.aiassistant.feature.authorization.presentation.models

sealed interface AuthorizationEvent {

    data class ShowErrorMessage(val message: String) : AuthorizationEvent
}
