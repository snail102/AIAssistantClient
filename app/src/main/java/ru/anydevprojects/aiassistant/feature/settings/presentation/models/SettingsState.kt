package ru.anydevprojects.aiassistant.feature.settings.presentation.models

data class SettingsState(
    val login: String = "",
    val isLogOutProcessing: Boolean = false
)
