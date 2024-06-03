package ru.anydevprojects.aiassistant.feature.settings.presentation

import androidx.lifecycle.ViewModel
import ru.anydevprojects.aiassistant.feature.settings.domain.SettingsRepository

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel()
