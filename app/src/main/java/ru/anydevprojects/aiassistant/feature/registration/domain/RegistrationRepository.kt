package ru.anydevprojects.aiassistant.feature.registration.domain

interface RegistrationRepository {

    suspend fun register(login: String, password: String, email: String): Result<Unit>

    suspend fun confirmEmail(login: String, confirmCode: String): Result<Unit>

    suspend fun retryMail(login: String): Result<Unit>
}
