package ru.anydevprojects.aiassistant.feature.authorization.presentation.models

data class AuthorizationState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val enabledAuthBtn: Boolean = false
)
