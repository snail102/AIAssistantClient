package ru.anydevprojects.aiassistant.feature.registration.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.anydevprojects.aiassistant.core.network.HTTP_HEADER_AUTHORIZATION
import ru.anydevprojects.aiassistant.core.network.TokenResponse
import ru.anydevprojects.aiassistant.domain.models.Token
import ru.anydevprojects.aiassistant.feature.registration.data.models.ConfirmationEmailRequest
import ru.anydevprojects.aiassistant.feature.registration.data.models.RegistrationRequest
import ru.anydevprojects.aiassistant.feature.registration.data.models.RetryMailRequest
import ru.anydevprojects.aiassistant.feature.registration.domain.RegistrationRepository
import ru.anydevprojects.aiassistant.tokenStorage.TokenStorage

private const val REGISTRATION_PATH = "registration"
private const val CONFIRMATION_EMAIL_PATH = "confirmation_email"
private const val RETRY_EMAIL_PATH = "retry_confirmation_email"

class RegistrationRepositoryImpl(
    private val httpClient: HttpClient,
    private val tokenStorage: TokenStorage
) : RegistrationRepository {
    override suspend fun register(login: String, password: String, email: String): Result<Unit> {
        return kotlin.runCatching {
            val registrationRequest = RegistrationRequest(
                login = login,
                password = password,
                email = email
            )
            val response = httpClient.post(REGISTRATION_PATH) {
                setBody(registrationRequest)
                headers {
                    remove(HTTP_HEADER_AUTHORIZATION)
                }
            }
            Unit
        }
    }

    override suspend fun confirmEmail(login: String, confirmCode: String): Result<Unit> {
        return kotlin.runCatching {
            val confirmationEmailRequest = ConfirmationEmailRequest(
                login = login,
                confirmCode = confirmCode
            )

            val response = httpClient.post(CONFIRMATION_EMAIL_PATH) {
                setBody(confirmationEmailRequest)
                headers {
                    remove(HTTP_HEADER_AUTHORIZATION)
                }
            }

            val tokenResponse = response.body<TokenResponse>()

            tokenStorage.saveToken(
                Token(
                    access = tokenResponse.accessToken,
                    refresh = tokenResponse.refreshToken
                )
            )
        }
    }

    override suspend fun retryMail(login: String): Result<Unit> {
        return kotlin.runCatching {
            val retryMailRequest = RetryMailRequest(
                login = login
            )

            val response = httpClient.post(RETRY_EMAIL_PATH) {
                setBody(retryMailRequest)
                headers {
                    remove(HTTP_HEADER_AUTHORIZATION)
                }
            }

            Unit
        }
    }
}
