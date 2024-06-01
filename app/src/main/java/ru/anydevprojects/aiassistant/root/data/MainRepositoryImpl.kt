package ru.anydevprojects.aiassistant.root.data

import ru.anydevprojects.aiassistant.root.domain.MainRepository
import ru.anydevprojects.aiassistant.tokenStorage.TokenStorage

class MainRepositoryImpl(
    private val tokenStorage: TokenStorage
) : MainRepository {

    override suspend fun hasSavedTokens(): Boolean {
        val token = tokenStorage.initToken()
        return token.access.isNotEmpty() && token.refresh.isNotEmpty()
    }
}
