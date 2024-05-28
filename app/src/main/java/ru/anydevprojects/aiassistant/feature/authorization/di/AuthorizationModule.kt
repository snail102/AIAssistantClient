package ru.anydevprojects.aiassistant.feature.authorization.di

import org.koin.dsl.module
import ru.anydevprojects.aiassistant.feature.authorization.data.AuthorizationRepositoryImpl
import ru.anydevprojects.aiassistant.feature.authorization.domain.AuthorizationRepository

val authorizationModule = module {
    factory<AuthorizationRepository> { AuthorizationRepositoryImpl(get()) }
}
