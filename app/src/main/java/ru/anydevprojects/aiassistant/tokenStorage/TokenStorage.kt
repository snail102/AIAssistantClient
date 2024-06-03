package ru.anydevprojects.aiassistant.tokenStorage

import kotlinx.coroutines.flow.Flow
import ru.anydevprojects.aiassistant.domain.models.Token

interface TokenStorage {

    val accessToken: String

    val refreshToken: String

    fun getTokenFlow(): Flow<Token>

    suspend fun initToken(): Token

    suspend fun saveToken(token: Token)

    suspend fun getToken(): Token
}
