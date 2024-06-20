package ru.anydevprojects.aiassistant.feature.profile.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserGptTokenStatisticsDto(
    val availableGptTokens: Int,
    val usedGptTokens: Int
)
