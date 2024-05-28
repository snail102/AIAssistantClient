package ru.anydevprojects.aiassistant.tokenStorage

import ru.anydevprojects.aiassistant.domain.models.Token

interface TokenStorage {

    suspend fun saveToken(token: Token)

    suspend fun getToken(): Token
}
