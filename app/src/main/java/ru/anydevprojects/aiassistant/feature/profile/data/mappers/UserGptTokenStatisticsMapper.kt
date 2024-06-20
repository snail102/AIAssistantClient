package ru.anydevprojects.aiassistant.feature.profile.data.mappers

import ru.anydevprojects.aiassistant.feature.profile.data.models.UserGptTokenStatisticsDto
import ru.anydevprojects.aiassistant.feature.profile.domain.models.UserGptTokenStatistics

fun UserGptTokenStatisticsDto.toDomain(): UserGptTokenStatistics {
    return UserGptTokenStatistics(
        availableGptTokens = this.availableGptTokens,
        usedGptTokens = this.usedGptTokens
    )
}
