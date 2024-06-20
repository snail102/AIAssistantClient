package ru.anydevprojects.aiassistant.feature.profile.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.anydevprojects.aiassistant.feature.profile.data.ProfileRepositoryImpl
import ru.anydevprojects.aiassistant.feature.profile.domain.ProfileRepository
import ru.anydevprojects.aiassistant.feature.profile.presentation.ProfileViewModel

val profileModule = module {
    factory<ProfileRepository> { ProfileRepositoryImpl(get()) }
    viewModel { ProfileViewModel(get()) }
}
