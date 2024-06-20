package ru.anydevprojects.aiassistant.feature.profile.domain.models

data class UserGptTokenStatistics(
    val availableGptTokens: Int,
    val usedGptTokens: Int
)
