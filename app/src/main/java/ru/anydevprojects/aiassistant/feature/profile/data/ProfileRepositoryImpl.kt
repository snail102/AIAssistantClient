package ru.anydevprojects.aiassistant.feature.profile.data

import io.ktor.client.HttpClient
import ru.anydevprojects.aiassistant.feature.profile.domain.ProfileRepository
import ru.anydevprojects.aiassistant.feature.profile.domain.models.UserGptTokenStatistics

class ProfileRepositoryImpl(
    private val httpClient: HttpClient
) : ProfileRepository {
    override suspend fun getUserGptTokenStatistics(): Result<UserGptTokenStatistics> {
        TODO("Not yet implemented")
    }
}
