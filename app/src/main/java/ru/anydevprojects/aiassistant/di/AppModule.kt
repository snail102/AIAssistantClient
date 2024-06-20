package ru.anydevprojects.aiassistant.di

import org.koin.dsl.module
import ru.anydevprojects.aiassistant.core.network.networkModule
import ru.anydevprojects.aiassistant.cryptoManager.di.cryptoManagerModule
import ru.anydevprojects.aiassistant.feature.authorization.di.authorizationModule
import ru.anydevprojects.aiassistant.feature.chat.di.chatModule
import ru.anydevprojects.aiassistant.feature.profile.di.profileModule
import ru.anydevprojects.aiassistant.feature.registration.di.registrationModule
import ru.anydevprojects.aiassistant.feature.settings.di.settingsModule
import ru.anydevprojects.aiassistant.root.di.mainModule
import ru.anydevprojects.aiassistant.tokenStorage.di.tokenStorageModule

val appModule = module {
    includes(
        networkModule,
        chatModule,
        tokenStorageModule,
        cryptoManagerModule,
        authorizationModule,
        mainModule,
        registrationModule,
        settingsModule,
        profileModule
    )
}
