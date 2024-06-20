package ru.anydevprojects.aiassistant.feature.profile.presentation.models

import ru.anydevprojects.aiassistant.feature.profile.domain.models.UserGptTokenStatistics

data class ProfileState(
    val isLoadingUserGptTokens: Boolean = false,
    val gptTokenStatistics: UserGptTokenStatistics? = null
)
