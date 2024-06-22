package ru.anydevprojects.aiassistant.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.anydevprojects.aiassistant.feature.profile.domain.ProfileRepository
import ru.anydevprojects.aiassistant.feature.profile.presentation.models.ProfileState

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileState> =
        MutableStateFlow(ProfileState(isLoadingUserGptTokens = true))
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadUserGptTokenStatistics()
    }

    private fun loadUserGptTokenStatistics() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingUserGptTokens = true
                )
            }
            profileRepository.getUserGptTokenStatistics().onSuccess { userGptTokenStatistics ->
                _state.update {
                    it.copy(
                        isLoadingUserGptTokens = false,
                        gptTokenStatistics = userGptTokenStatistics
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoadingUserGptTokens = false,
                        gptTokenStatistics = null
                    )
                }
            }
        }
    }
}
