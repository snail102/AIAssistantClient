package ru.anydevprojects.aiassistant.feature.profile.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import ru.anydevprojects.aiassistant.feature.profile.data.mappers.toDomain
import ru.anydevprojects.aiassistant.feature.profile.data.models.UserGptTokenStatisticsDto
import ru.anydevprojects.aiassistant.feature.profile.domain.ProfileRepository
import ru.anydevprojects.aiassistant.feature.profile.domain.models.UserGptTokenStatistics

private const val GPT_TOKENS_PATH = "user_gpt_tokens"

class ProfileRepositoryImpl(
    private val httpClient: HttpClient
) : ProfileRepository {
    override suspend fun getUserGptTokenStatistics(): Result<UserGptTokenStatistics> {
        return runCatching {
            val response: HttpResponse = httpClient.get(GPT_TOKENS_PATH)

            val userGptTokenStatisticsDto = response.body<UserGptTokenStatisticsDto>()

            userGptTokenStatisticsDto.toDomain()
        }
    }
}
