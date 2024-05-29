package ru.anydevprojects.aiassistant.feature.authorization.data

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.anydevprojects.aiassistant.core.network.HTTP_HEADER_AUTHORIZATION
import ru.anydevprojects.aiassistant.feature.authorization.data.models.LoginRequest
import ru.anydevprojects.aiassistant.feature.authorization.domain.AuthorizationRepository

private const val LOGIN_PATH = "login"

class AuthorizationRepositoryImpl(
    private val httpClient: HttpClient
) : AuthorizationRepository {
    override suspend fun auth(login: String, password: String): Result<Unit> {
        return runCatching {
            val loginRequest = LoginRequest(
                login = login,
                password = password
            )

            val response = httpClient.post(LOGIN_PATH) {
                setBody(loginRequest)
                headers {
                    remove(HTTP_HEADER_AUTHORIZATION)
                }
            }

            Unit
        }
    }
}
