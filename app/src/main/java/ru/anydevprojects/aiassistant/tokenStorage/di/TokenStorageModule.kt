package ru.anydevprojects.aiassistant.tokenStorage.di

import org.koin.dsl.module
import ru.anydevprojects.aiassistant.tokenStorage.TokenStorage
import ru.anydevprojects.aiassistant.tokenStorage.TokenStorageImpl

val tokenStorageModule = module {
    single<TokenStorage> { TokenStorageImpl(get(), get()) }
}
