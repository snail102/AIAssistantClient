package ru.anydevprojects.aiassistant.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.anydevprojects.aiassistant.feature.settings.domain.SettingsRepository
import ru.anydevprojects.aiassistant.feature.settings.presentation.models.SettingsIntent
import ru.anydevprojects.aiassistant.feature.settings.presentation.models.SettingsState

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    fun onIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.OnLogOutBtnClick -> logOut()
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLogOutProcessing = true
                )
            }
            settingsRepository.logOut()
        }
    }
}
