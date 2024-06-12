package ru.anydevprojects.aiassistant.feature.registration.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.anydevprojects.aiassistant.feature.registration.data.RegistrationRepositoryImpl
import ru.anydevprojects.aiassistant.feature.registration.domain.RegistrationRepository
import ru.anydevprojects.aiassistant.feature.registration.presentation.ConfirmEmailViewModel
import ru.anydevprojects.aiassistant.feature.registration.presentation.RegistrationViewModel

val registrationModule = module {
    factory<RegistrationRepository> { RegistrationRepositoryImpl(get(), get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { ConfirmEmailViewModel(get(), get()) }
}
