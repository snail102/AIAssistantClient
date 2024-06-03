package ru.anydevprojects.aiassistant.feature.registration.presentation.models

sealed interface ConfirmEmailEvent {

    data object IncorrectCode : ConfirmEmailEvent
}
