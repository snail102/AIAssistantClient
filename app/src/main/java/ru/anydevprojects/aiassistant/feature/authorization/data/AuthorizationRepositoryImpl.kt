package ru.anydevprojects.aiassistant.feature.authorization.data

import io.ktor.client.HttpClient
import ru.anydevprojects.aiassistant.feature.authorization.domain.AuthorizationRepository

class AuthorizationRepositoryImpl(
    private val httpClient: HttpClient
) : AuthorizationRepository {
    override suspend fun auth(login: String, password: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}
