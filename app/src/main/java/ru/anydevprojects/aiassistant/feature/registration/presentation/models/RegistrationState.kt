package ru.anydevprojects.aiassistant.feature.registration.presentation.models

data class RegistrationState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val enabledRegisterBtn: Boolean = false
)
