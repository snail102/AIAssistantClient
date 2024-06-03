package ru.anydevprojects.aiassistant.feature.authorization.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.anydevprojects.aiassistant.feature.authorization.data.AuthorizationRepositoryImpl
import ru.anydevprojects.aiassistant.feature.authorization.domain.AuthorizationRepository
import ru.anydevprojects.aiassistant.feature.authorization.presentation.AuthorizationViewModel

val authorizationModule = module {
    factory<AuthorizationRepository> { AuthorizationRepositoryImpl(get(), get()) }
    viewModel { AuthorizationViewModel(get()) }
}
