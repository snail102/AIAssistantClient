package ru.anydevprojects.aiassistant.feature.settings.data

import ru.anydevprojects.aiassistant.feature.settings.domain.SettingsRepository
import ru.anydevprojects.aiassistant.tokenStorage.TokenStorage

class SettingsRepositoryImpl(
    private val tokenStorage: TokenStorage
) : SettingsRepository {
    override suspend fun logOut() {
        tokenStorage.removeTokens()
    }
}
