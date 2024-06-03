package ru.anydevprojects.aiassistant.feature.registration.domain

import ru.anydevprojects.aiassistant.core.network.TokenResponse

interface RegistrationRepository {

    suspend fun register(login: String, password: String, email: String): Result<Unit>

    suspend fun confirmEmail(login: String, confirmCode: String): Result<TokenResponse>
}
