package ru.anydevprojects.aiassistant.tokenStorage

import ru.anydevprojects.aiassistant.domain.models.Token

interface TokenStorage {

    val accessToken: String

    val refreshToken: String

    suspend fun initToken(): Token

    suspend fun saveToken(token: Token)

    suspend fun getToken(): Token
}
