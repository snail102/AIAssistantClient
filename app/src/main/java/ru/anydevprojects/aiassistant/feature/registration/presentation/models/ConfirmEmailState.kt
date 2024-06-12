package ru.anydevprojects.aiassistant.feature.registration.presentation.models

data class ConfirmEmailState(
    val isCheckCorrectCode: Boolean = false,
    val isProcessingSendRetryCode: Boolean = false,
    val code: String = ""
)
