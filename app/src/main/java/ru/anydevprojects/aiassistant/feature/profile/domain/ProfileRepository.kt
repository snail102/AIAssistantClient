package ru.anydevprojects.aiassistant.feature.profile.domain

import ru.anydevprojects.aiassistant.feature.profile.domain.models.UserGptTokenStatistics

interface ProfileRepository {

    suspend fun getUserGptTokenStatistics(): Result<UserGptTokenStatistics>
}
