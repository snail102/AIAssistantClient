package ru.anydevprojects.aiassistant.feature.authorization.domain

interface AuthorizationRepository {

    suspend fun auth(login: String, password: String): Result<Unit>
}
