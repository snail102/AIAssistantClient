package ru.anydevprojects.aiassistant.feature.authorization.presentation.models

sealed interface AuthorizationIntent {

    data object OnAuthClick : AuthorizationIntent

    data class OnChangeLogin(val newLoginValue: String) : AuthorizationIntent
    data class OnChangePassword(val newPasswordValue: String) : AuthorizationIntent
}
