package ru.anydevprojects.aiassistant.feature.registration.presentation.models

sealed interface ConfirmEmailEvent {

    data object IncorrectCode : ConfirmEmailEvent
    data object FailedToResendCode : ConfirmEmailEvent
    data object SuccessToResendCode : ConfirmEmailEvent
}
