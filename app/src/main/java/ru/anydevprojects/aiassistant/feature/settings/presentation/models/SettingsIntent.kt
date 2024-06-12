package ru.anydevprojects.aiassistant.feature.settings.presentation.models

sealed interface SettingsIntent {
    data object OnLogOutBtnClick : SettingsIntent
}
