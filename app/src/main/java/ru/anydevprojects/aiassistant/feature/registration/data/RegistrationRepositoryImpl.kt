package ru.anydevprojects.aiassistant.feature.registration.data

import io.ktor.client.HttpClient
import ru.anydevprojects.aiassistant.core.network.TokenResponse
import ru.anydevprojects.aiassistant.feature.registration.domain.RegistrationRepository

class RegistrationRepositoryImpl(
    private val httpClient: HttpClient
) : RegistrationRepository {
    override suspend fun register(login: String, password: String, email: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun confirmEmail(login: String, confirmCode: String): Result<TokenResponse> {
        TODO("Not yet implemented")
    }
}
