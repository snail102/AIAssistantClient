package ru.anydevprojects.aiassistant.root.presentation.models

sealed interface MainEvent {

    data object NavigateToAuthorization : MainEvent
    data object NavigateToChat : MainEvent
}
