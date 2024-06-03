package ru.anydevprojects.aiassistant.feature.registration.presentation.models

sealed interface ConfirmEmailIntent {

    data object OnRetrySendMailBtnClick : ConfirmEmailIntent

    data class OnChangeCode(val newCode: String) : ConfirmEmailIntent
}
