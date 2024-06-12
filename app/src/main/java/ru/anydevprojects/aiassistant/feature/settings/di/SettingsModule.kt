package ru.anydevprojects.aiassistant.feature.settings.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.anydevprojects.aiassistant.feature.settings.data.SettingsRepositoryImpl
import ru.anydevprojects.aiassistant.feature.settings.domain.SettingsRepository
import ru.anydevprojects.aiassistant.feature.settings.presentation.SettingsViewModel

val settingsModule = module {
    factory<SettingsRepository> { SettingsRepositoryImpl(get()) }
    viewModel { SettingsViewModel(get()) }
}
