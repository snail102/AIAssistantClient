package ru.anydevprojects.aiassistant.feature.registration.presentation.models

data class ConfirmEmailState(
    val isLoading: Boolean = false,
    val code: String = ""
)
