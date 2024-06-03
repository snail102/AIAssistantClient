package ru.anydevprojects.aiassistant.feature.registration.presentation.models

sealed interface RegistrationIntent {
    data class OnChangeLogin(val newLogin: String) : RegistrationIntent
    data class OnChangePassword(val newPassword: String) : RegistrationIntent
    data class OnChangeConfirmPassword(val newConfirmPassword: String) : RegistrationIntent
    data class OnChangeEmail(val newEmail: String) : RegistrationIntent

    data object OnRegistrationBtnClick : RegistrationIntent
}
